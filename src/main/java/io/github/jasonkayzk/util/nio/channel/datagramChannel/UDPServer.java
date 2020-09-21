package io.github.jasonkayzk.util.nio.channel.datagramChannel;

import io.github.jasonkayzk.util.nio.config.DemoConfig;
import io.github.jasonkayzk.util.nio.util.Print;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

public class UDPServer {

    public static void main(String[] args) throws IOException {
        new UDPServer().receive();
    }

    public void receive() throws IOException {
        //操作一：获取DatagramChannel数据报通道
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking(false);
        datagramChannel.bind(new InetSocketAddress(
                DemoConfig.SOCKET_SERVER_IP,
                DemoConfig.SOCKET_SERVER_PORT)
        );
        Print.tcfo("UDP 服务器启动成功！");

        //操作二：创建并注册Selector
        Selector selector = Selector.open();
        datagramChannel.register(selector, SelectionKey.OP_READ);

        //操作三：阻塞等待，直到有消息
        while (selector.select() > 0) {
            var iterator = selector.selectedKeys().iterator();
            var buffer = ByteBuffer.allocate(DemoConfig.SEND_BUFFER_SIZE);
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isReadable()) {
                    //操作四：读取DatagramChannel数据报通道数据
                    datagramChannel.receive(buffer);
                    buffer.flip();
                    Print.tcfo(new String(buffer.array(), 0, buffer.limit()));
                    buffer.clear();
                }
            }
            //操作五：删除处理完成的Key
            iterator.remove();
        }
        selector.close();
        datagramChannel.close();
    }

}
