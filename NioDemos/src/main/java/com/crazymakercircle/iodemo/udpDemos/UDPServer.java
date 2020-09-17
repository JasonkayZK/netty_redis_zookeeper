package com.crazymakercircle.iodemo.udpDemos;

import com.crazymakercircle.NioDemoConfig;
import com.crazymakercircle.util.Print;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/
public class UDPServer {

    public void receive() throws IOException {
        //操作一：获取DatagramChannel数据报通道
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking(false);
        datagramChannel.bind(new InetSocketAddress(
                NioDemoConfig.SOCKET_SERVER_IP
                , NioDemoConfig.SOCKET_SERVER_PORT));
        Print.tcfo("UDP 服务器启动成功！");
        Selector selector = Selector.open();
        datagramChannel.register(selector, SelectionKey.OP_READ);
        while (selector.select() > 0) {
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            ByteBuffer buffer = ByteBuffer.allocate(NioDemoConfig.SEND_BUFFER_SIZE);
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isReadable()) {
                    //操作二：读取DatagramChannel数据报通道数据
                    SocketAddress client = datagramChannel.receive(buffer);
                    buffer.flip();
                    Print.tcfo(new String(buffer.array(), 0, buffer.limit()));
                    buffer.clear();
                }
            }
            iterator.remove();
        }

        selector.close();
        datagramChannel.close();
    }

    public static void main(String[] args) throws IOException {
        new UDPServer().receive();
    }
}
