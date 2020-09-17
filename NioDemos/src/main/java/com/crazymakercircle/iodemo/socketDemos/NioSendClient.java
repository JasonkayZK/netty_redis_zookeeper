package com.crazymakercircle.iodemo.socketDemos;

import com.crazymakercircle.NioDemoConfig;
import com.crazymakercircle.util.IOUtil;
import com.crazymakercircle.util.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;


/**
 * 文件传输Client端
 * Created by 尼恩@ 疯创客圈
 */

public class NioSendClient {


    /**
     * 构造函数
     * 与服务器建立连接
     *
     * @throws Exception
     */
    public NioSendClient() {

    }

    private Charset charset = Charset.forName("UTF-8");

    /**
     * 向服务端传输文件
     *
     * @throws Exception
     */
    public void sendFile() {
        try {


            String sourcePath = NioDemoConfig.SOCKET_SEND_FILE;
            String srcPath = IOUtil.getResourcePath(sourcePath);
            Logger.debug("srcPath=" + srcPath);

            String destFile = NioDemoConfig.SOCKET_RECEIVE_FILE;
            Logger.debug("destFile=" + destFile);

            File file = new File(srcPath);
            if (!file.exists()) {
                Logger.debug("文件不存在");
                return;
            }
            FileChannel fileChannel = new FileInputStream(file).getChannel();

            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.socket().connect(
                    new InetSocketAddress(NioDemoConfig.SOCKET_SERVER_IP
                            , NioDemoConfig.SOCKET_SERVER_PORT));
            socketChannel.configureBlocking(false);
            Logger.debug("Cliect 成功连接服务端");

            while (!socketChannel.finishConnect()) {
                //不断的自旋、等待，或者做一些其他的事情
            }


            //发送文件名称
            ByteBuffer fileNameByteBuffer = charset.encode(destFile);
            socketChannel.write(fileNameByteBuffer);

            //发送文件长度
            ByteBuffer buffer = ByteBuffer.allocate(NioDemoConfig.SEND_BUFFER_SIZE);
            buffer.putLong(file.length());

            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();


            //发送文件内容
            Logger.debug("开始传输文件");
            int length = 0;
            long progress = 0;
            while ((length = fileChannel.read(buffer)) > 0) {
                buffer.flip();
                socketChannel.write(buffer);
                buffer.clear();
                progress += length;
                Logger.debug("| " + (100 * progress / file.length()) + "% |");
            }

            if (length == -1) {
                IOUtil.closeQuietly(fileChannel);
                socketChannel.shutdownOutput();
                IOUtil.closeQuietly(socketChannel);
            }
            Logger.debug("======== 文件传输成功 ========");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 入口
     *
     * @param args
     */
    public static void main(String[] args) {

        NioSendClient client = new NioSendClient(); // 启动客户端连接
        client.sendFile(); // 传输文件

    }

}
