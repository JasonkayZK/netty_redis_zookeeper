package io.github.jasonkayzk.util.nio.channel.socketChannel;

import io.github.jasonkayzk.util.nio.config.DemoConfig;
import io.github.jasonkayzk.util.nio.util.IOUtil;
import io.github.jasonkayzk.util.nio.util.Logger;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

public class BlockClient extends Socket {

    private Socket client;

    private FileInputStream fis;

    private DataOutputStream dos;

    public static void main(String[] args) throws Exception {
        // 启动客户端连接，传输文件
        new BlockClient().sendFile();
    }

    /**
     * 构造函数<br/>
     * 与服务器建立连接
     */
    public BlockClient() throws Exception {
        super(DemoConfig.SOCKET_SERVER_IP, DemoConfig.SOCKET_SERVER_PORT);
        this.client = this;
        Logger.debug("Client[port:" + client.getLocalPort() + "] 成功连接服务端");
    }

    /**
     * 向服务端传输文件
     */
    public void sendFile() {
        try {
            File file = new File(IOUtil.getResourcePath(DemoConfig.SOCKET_SEND_FILE));
            if (file.exists()) {
                fis = new FileInputStream(file);
                dos = new DataOutputStream(client.getOutputStream());
                Logger.debug("======== 加载文件 ========");

                // 文件名和长度
                dos.writeUTF("copy_" + file.getName());
                dos.flush();
                dos.writeLong(file.length());
                dos.flush();

                // 开始传输文件
                Logger.debug("======== 开始传输文件 ========");
                byte[] bytes = new byte[1024];
                int length;
                long progress = 0;
                while ((length = fis.read(bytes, 0, bytes.length)) != -1) {
                    dos.write(bytes, 0, length);
                    dos.flush();
                    progress += length;
                    Logger.debug("| " + (100 * progress / file.length()) + "% |");
                }
                Logger.debug("======== 文件传输成功 ========");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtil.closeQuietly(fis);
            IOUtil.closeQuietly(dos);
            IOUtil.closeQuietly(client);
        }
    }
}
