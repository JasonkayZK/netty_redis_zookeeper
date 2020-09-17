package com.crazymakercircle.ReactorModel;


import com.crazymakercircle.config.SystemConfig;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class MthreadHandler implements Runnable
{
    final SocketChannel channel;
    final SelectionKey selectionKey;
    ByteBuffer input = ByteBuffer.allocate(SystemConfig.INPUT_SIZE);
    ByteBuffer output = ByteBuffer.allocate(SystemConfig.SEND_SIZE);
    static final int READING = 0, SENDING = 1;
    int state = READING;


    ExecutorService pool = Executors.newFixedThreadPool(2);
    static final int PROCESSING = 3;

    MthreadHandler(Selector selector, SocketChannel c) throws IOException
    {
        channel = c;
        c.configureBlocking(false);
        // Optionally try first read now
        selectionKey = channel.register(selector, 0);

        //将Handler作为callback对象
        selectionKey.attach(this);

        //第二步,注册Read就绪事件
        selectionKey.interestOps(SelectionKey.OP_READ);
        selector.wakeup();
    }

    boolean inputIsComplete()
    {
       /* ... */
        return true;
    }

    boolean outputIsComplete()
    {

       /* ... */
        return true;
    }

    void process()
    {
       /* ... */
        return;
    }

    public void run()
    {
        try
        {
            if (state == READING)
            {
                read();
            }
            else if (state == SENDING)
            {
                send();
            }
        } catch (IOException ex)
        { /* ... */ }
    }


    synchronized void read() throws IOException
    {
        // ...
        channel.read(input);
        if (inputIsComplete())
        {
            state = PROCESSING;
            //使用线程pool异步执行
            pool.execute(new Processer());
        }
    }

    void send() throws IOException
    {
        channel.write(output);

        //write完就结束了, 关闭select key
        if (outputIsComplete())
        {
            selectionKey.cancel();
        }
    }

    synchronized void processAndHandOff()
    {
        process();
        state = SENDING;
        // or rebind attachment
        //process完,开始等待write就绪
        selectionKey.interestOps(SelectionKey.OP_WRITE);
    }

    class Processer implements Runnable
    {
        public void run()
        {
            processAndHandOff();
        }
    }

}

