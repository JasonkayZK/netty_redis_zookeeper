package com.crazymakercircle.iodemo.OIO;

import com.crazymakercircle.NioDemoConfig;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

;

class ConnectionPerThread implements Runnable {
    public void run() {
        try {
            ServerSocket serverSocket =
                    new ServerSocket(NioDemoConfig.SOCKET_SERVER_PORT);
            while (!Thread.interrupted()) {
                Socket socket = serverSocket.accept();
                Handler handler = new Handler(socket);
                //创建新线程来handle
                //或者，使用线程池来处理
                new Thread(handler).start();
            }

        } catch (IOException ex) { /* 处理异常 */ }
    }

    static class Handler implements Runnable {
        final Socket socket;

        Handler(Socket s) {
            socket = s;
        }

        public void run() {
            while (true) {
                try {
                    byte[] input = new byte[NioDemoConfig.SERVER_BUFFER_SIZE];
                    /* 读取数据 */
                    socket.getInputStream().read(input);
                    /* 处理业务逻辑，获取处理结果 */
                    byte[] output =null;
                    /* 写入结果 */
                    socket.getOutputStream().write(output);
                } catch (IOException ex) { /*处理异常*/ }
            }
        }

    }
}