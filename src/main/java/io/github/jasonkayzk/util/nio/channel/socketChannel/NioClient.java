package io.github.jasonkayzk.util.nio.channel.socketChannel;

import io.github.jasonkayzk.util.nio.config.DemoConfig;
import io.github.jasonkayzk.util.nio.util.IOUtil;
import io.github.jasonkayzk.util.nio.util.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class NioClient {

    public static void main(String[] args) {
        // 传输文件
        new NioClient().sendFile();
    }

    public NioClient() {
    }

    @SuppressWarnings("StatementWithEmptyBody")
    public void sendFile() {
        try {
            String srcPath = IOUtil.getResourcePath(DemoConfig.SOCKET_SEND_FILE);
            Logger.debug("srcPath=" + srcPath);
            String destFile = DemoConfig.SOCKET_RECEIVE_FILE;
            Logger.debug("destFile=" + destFile);

            File file = new File(srcPath);
            if (!file.exists()) {
                Logger.debug("文件不存在");
                return;
            }
            FileChannel fileChannel = new FileInputStream(file).getChannel();

            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.socket().connect(new InetSocketAddress(
                    DemoConfig.SOCKET_SERVER_IP,
                    DemoConfig.SOCKET_SERVER_PORT)
            );
            socketChannel.configureBlocking(false);
            Logger.debug("Client 成功连接服务端");

            while (!socketChannel.finishConnect()) {
                //不断的自旋、等待，或者做一些其他的事情
            }

            //发送文件名称
            ByteBuffer fileNameByteBuffer = StandardCharsets.UTF_8.encode(destFile);
            socketChannel.write(fileNameByteBuffer);

            //发送文件长度
            ByteBuffer buffer = ByteBuffer.allocate(DemoConfig.SEND_BUFFER_SIZE);
            buffer.putLong(file.length());

            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();

            //发送文件内容
            Logger.debug("开始传输文件");
            int length;
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
}
