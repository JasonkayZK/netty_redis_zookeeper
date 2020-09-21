package io.github.jasonkayzk.util.nio.channel.fileChannel;

import io.github.jasonkayzk.util.nio.config.DemoConfig;
import io.github.jasonkayzk.util.nio.util.IOUtil;
import io.github.jasonkayzk.util.nio.util.Logger;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileNioReadDemo {

    public static final int CAPACITY = 1024;

    public static void main(String[] args) {
        readSourceFile();
    }

    /**
     * 读取
     */
    public static void readSourceFile() {
        String readPath = IOUtil.getResourcePath(DemoConfig.FILE_RESOURCE_SRC_PATH);
        Logger.debug("readPath=" + readPath);
        nioReadFile(readPath);
    }

    /**
     * 读取文件内容并输出
     *
     * @param fileName 文件名
     */
    public static void nioReadFile(String fileName) {
        try {
            RandomAccessFile aFile = new RandomAccessFile(fileName, "rw");
            FileChannel inChannel = aFile.getChannel();

            ByteBuffer buf = ByteBuffer.allocate(CAPACITY);

            int length;
            while ((length = inChannel.read(buf)) != -1) {
                buf.flip();
                byte[] bytes = buf.array();
                String str = new String(bytes, 0, length);
                System.out.println(str);
            }

            IOUtil.closeQuietly(inChannel);
            IOUtil.closeQuietly(aFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
