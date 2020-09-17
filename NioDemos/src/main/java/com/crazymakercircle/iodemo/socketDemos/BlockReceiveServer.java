package com.crazymakercircle.iodemo.socketDemos;

import com.crazymakercircle.NioDemoConfig;
import com.crazymakercircle.util.FormatUtil;
import com.crazymakercircle.util.IOUtil;
import com.crazymakercircle.util.Logger;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DecimalFormat;


/**
 * 文件传输Server端<br>
 */
public class BlockReceiveServer extends ServerSocket
{

    // 服务端端口
    private static final int SERVER_PORT =
            NioDemoConfig.SOCKET_SERVER_PORT;

    //接受文件路径
    private static final String RECIEVE_PATH =
            NioDemoConfig.SOCKET_RECEIVE_PATH;

    private static DecimalFormat df = FormatUtil.decimalFormat(1);


    public BlockReceiveServer() throws Exception
    {
        super(SERVER_PORT);
    }

    /**
     * 启动服务端
     * 使用线程处理每个客户端传输的文件
     *
     * @throws Exception
     */
    public void startServer() throws Exception
    {
        while (true)
        {
            // server尝试接收其他Socket的连接请求，server的accept方法是阻塞式的

            Logger.debug("server listen at:" + SERVER_PORT);
            Socket socket = this.accept();
            /**
             * 我们的服务端处理客户端的连接请求是同步进行的， 每次接收到来自客户端的连接请求后，
             * 都要先跟当前的客户端通信完之后才能再处理下一个连接请求。 这在并发比较多的情况下会严重影响程序的性能，
             * 为此，我们可以把它改为如下这种异步处理与客户端通信的方式
             */
            // 每接收到一个Socket就建立一个新的线程来处理它
            new Thread(new Task(socket)).start();


        }
    }

    /**
     * 处理客户端传输过来的文件线程类
     */
    class Task implements Runnable
    {

        private Socket socket;

        private DataInputStream dis;

        private FileOutputStream fos;

        public Task(Socket socket)
        {
            this.socket = socket;
        }

        @Override
        public void run()
        {
            try
            {
                dis = new DataInputStream(socket.getInputStream());

                // 文件名和长度
                String fileName = dis.readUTF();
                long fileLength = dis.readLong();
                File directory = new File(RECIEVE_PATH);
                if (!directory.exists())
                {
                    directory.mkdir();
                }
                File file = new File(directory.getAbsolutePath() + File.separatorChar + fileName);
                fos = new FileOutputStream(file);
                long startTime = System.currentTimeMillis();
                Logger.debug("block IO 传输开始：");

                // 开始接收文件
                byte[] bytes = new byte[1024];
                int length = 0;
                while ((length = dis.read(bytes, 0, bytes.length)) != -1)
                {
                    fos.write(bytes, 0, length);
                    fos.flush();
                }

                Logger.debug("文件接收成功,File Name：" + fileName);
                Logger.debug(" Size：" + IOUtil.getFormatFileSize(fileLength));
                long endTime = System.currentTimeMillis();
                Logger.debug("block IO 传输毫秒数：" + (endTime - startTime));
            } catch (Exception e)
            {
                e.printStackTrace();
            } finally
            {

                IOUtil.closeQuietly(fos);
                IOUtil.closeQuietly(dis);
                IOUtil.closeQuietly(socket);

            }
        }
    }


    /**
     * 入口
     *
     * @param args
     */
    public static void main(String[] args)
    {
        try
        {
            // 启动服务端
            BlockReceiveServer server = new BlockReceiveServer();
            server.startServer();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}