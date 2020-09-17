package com.crazymakercircle.iodemo.udpDemos;

import com.crazymakercircle.NioDemoConfig;
import com.crazymakercircle.util.Dateutil;
import com.crazymakercircle.util.Print;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/
public class UDPClient {

    public void send() throws IOException {
        //操作一：获取DatagramChannel数据报通道
        DatagramChannel dChannel = DatagramChannel.open();
        dChannel.configureBlocking(false);
        ByteBuffer buffer = ByteBuffer.allocate(NioDemoConfig.SEND_BUFFER_SIZE);
        Scanner scanner = new Scanner(System.in);
        Print.tcfo("UDP 客户端启动成功！");
        Print.tcfo("请输入发送内容:");
        while (scanner.hasNext()) {
            String next = scanner.next();
            buffer.put((Dateutil.getNow() + " >>" + next).getBytes());
            buffer.flip();
            // 操作三：通过DatagramChannel数据报通道发送数据
            dChannel.send(buffer,
                    new InetSocketAddress(NioDemoConfig.SOCKET_SERVER_IP
                            , NioDemoConfig.SOCKET_SERVER_PORT));
            buffer.clear();
        }
        //操作四：关闭DatagramChannel数据报通道
        dChannel.close();
    }

    public static void main(String[] args) throws IOException {
        new UDPClient().send();
    }
}
