package io.github.jasonkayzk.util.nio.channel.socketChannel;

import io.github.jasonkayzk.util.nio.config.DemoConfig;
import io.github.jasonkayzk.util.nio.util.IOUtil;
import io.github.jasonkayzk.util.nio.util.Logger;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BlockServer extends ServerSocket {

    // 服务端端口
    private static final int SERVER_PORT = DemoConfig.SOCKET_SERVER_PORT;

    // 接受文件路径
    private static final String RECEIVE_PATH = DemoConfig.SOCKET_RECEIVE_PATH;

    public static void main(String[] args) throws Exception {
        // 启动服务端
        BlockServer server = new BlockServer();
        server.startServer();
    }

    public BlockServer() throws Exception {
        super(SERVER_PORT);
    }

    /**
     * 启动服务端
     * 使用线程处理每个客户端传输的文件
     */
    @SuppressWarnings("InfiniteLoopStatement")
    public void startServer() throws IOException {
        Logger.debug("server listen at:" + SERVER_PORT);

        while (true) {
            // server尝试接收其他Socket的连接请求，server的accept方法是阻塞式的
            Socket socket = this.accept();
            Logger.debug("server receive new connection: " + socket.getRemoteSocketAddress());

            /*
             * 我们的服务端处理客户端的连接请求是同步进行的， 每次接收到来自客户端的连接请求后，
             * 都要先跟当前的客户端通信完之后才能再处理下一个连接请求，这在并发比较多的情况下会严重影响程序的性能。
             * 为此，我们可以把它改为如下这种异步处理与客户端通信的方式
             */
            // 每接收到一个Socket就建立一个新的线程来处理它
            new Thread(() -> {
                DataInputStream dis = null;
                FileOutputStream fos = null;

                try {
                    dis = new DataInputStream(socket.getInputStream());

                    // 文件名和长度
                    String fileName = dis.readUTF();
                    long fileLength = dis.readLong();
                    File directory = new File(RECEIVE_PATH);
                    if (!directory.exists()) {
                        boolean mkdirOk = directory.mkdir();
                        if (!mkdirOk) {
                            throw new RuntimeException("mkdir err");
                        }
                    }

                    File file = new File(directory.getAbsolutePath() + File.separatorChar + fileName);
                    fos = new FileOutputStream(file);
                    long startTime = System.currentTimeMillis();
                    Logger.debug("block IO 传输开始：");

                    // 开始接收文件
                    byte[] bytes = new byte[1024];
                    int length;
                    while ((length = dis.read(bytes, 0, bytes.length)) != -1) {
                        fos.write(bytes, 0, length);
                        fos.flush();
                    }

                    Logger.debug("文件接收成功,File Name：" + fileName);
                    Logger.debug(" Size：" + IOUtil.getFormatFileSize(fileLength));
                    long endTime = System.currentTimeMillis();
                    Logger.debug("block IO 传输毫秒数：" + (endTime - startTime));
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    IOUtil.closeQuietly(fos);
                    IOUtil.closeQuietly(dis);
                    IOUtil.closeQuietly(socket);
                }
            }).start();
        }
    }

}
