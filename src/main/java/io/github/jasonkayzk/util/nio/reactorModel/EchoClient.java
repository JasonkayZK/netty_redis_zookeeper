package io.github.jasonkayzk.util.nio.reactorModel;

import io.github.jasonkayzk.util.nio.config.DemoConfig;
import io.github.jasonkayzk.util.nio.util.Dateutil;
import io.github.jasonkayzk.util.nio.util.Logger;
import io.github.jasonkayzk.util.nio.util.Print;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import java.util.Set;

public class EchoClient {

    public static void main(String[] args) throws IOException, InterruptedException {
        new EchoClient().start();
    }

    public void start() throws IOException, InterruptedException {
        InetSocketAddress address = new InetSocketAddress(
                DemoConfig.SOCKET_SERVER_IP,
                DemoConfig.SOCKET_SERVER_PORT
        );
        // 1、获取通道（channel）
        SocketChannel socketChannel = SocketChannel.open(address);
        // 2、切换成非阻塞模式
        socketChannel.configureBlocking(false);
        //不断的自旋、等待连接完成，或者做一些其他的事情
        while (!socketChannel.finishConnect()) {
            Logger.info("connecting...");
            Thread.sleep(1000);
        }
        Print.tcfo("客户端启动成功！");

        //启动接受线程
        Processor processor = new Processor(socketChannel);
        new Thread(processor).start();
    }

    static class Processor implements Runnable {
        final Selector selector;
        final SocketChannel channel;

        Processor(SocketChannel channel) throws IOException {
            //Reactor初始化
            selector = Selector.open();
            this.channel = channel;
            channel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        }

        public void run() {
            try {
                while (!Thread.interrupted()) {
                    selector.select();
                    Set<SelectionKey> selected = selector.selectedKeys();
                    for (SelectionKey sk : selected) {
                        if (sk.isWritable()) {
                            ByteBuffer buffer = ByteBuffer.allocate(DemoConfig.SEND_BUFFER_SIZE);

                            Scanner scanner = new Scanner(System.in);
                            Print.tcfo("请输入发送内容:");
                            if (scanner.hasNext()) {
                                SocketChannel socketChannel = (SocketChannel) sk.channel();
                                String next = scanner.next();
                                buffer.put((Dateutil.getNow() + " >>" + next).getBytes());
                                buffer.flip();
                                // 操作三：通过DatagramChannel数据报通道发送数据
                                socketChannel.write(buffer);
                                buffer.clear();
                            }

                        }
                        if (sk.isReadable()) {
                            // 若选择键的IO事件是“可读”事件,读取数据
                            SocketChannel socketChannel = (SocketChannel) sk.channel();

                            //读取数据
                            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                            int length;
                            while ((length = socketChannel.read(byteBuffer)) > 0) {
                                byteBuffer.flip();
                                Logger.info("server echo:" + new String(byteBuffer.array(), 0, length));
                                byteBuffer.clear();
                            }
                        }
                        //处理结束了, 这里不能关闭select key，需要重复使用
                        //selectionKey.cancel();
                    }
                    selected.clear();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
