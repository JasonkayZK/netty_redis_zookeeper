package io.github.jasonkayzk.util.nio.channel.fileChannel;

import io.github.jasonkayzk.util.nio.config.DemoConfig;
import io.github.jasonkayzk.util.nio.util.IOUtil;
import io.github.jasonkayzk.util.nio.util.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileNioCopyDemo {

    public static void main(String[] args) {
        //演示复制资源文件
        nioCopyResourceFile();
    }

    /**
     * 复制两个资源目录下的文件
     */
    public static void nioCopyResourceFile() {
        String sourcePath = DemoConfig.FILE_RESOURCE_SRC_PATH;
        String srcPath = IOUtil.getResourcePath(sourcePath);
        Logger.debug("srcPath=" + srcPath);

        String destShortPath = DemoConfig.FILE_RESOURCE_DEST_PATH;
        String destPath = IOUtil.builderResourcePath(destShortPath);
        Logger.debug("destPath=" + destPath);

        nioCopyFile(srcPath, destPath);
    }

    /**
     * 复制文件
     */
    public static void nioCopyFile(String srcPath, String destPath) {
        File srcFile = new File(srcPath);
        File destFile = new File(destPath);

        try {
            //如果目标文件不存在，则新建
            if (!destFile.exists()) {
                boolean newFile = destFile.createNewFile();
                if (!newFile) {
                    throw new RuntimeException("new file: " + destFile + "err");
                }
            }

            long startTime = System.currentTimeMillis();
            FileInputStream fis = null;
            FileOutputStream fos = null;
            FileChannel inChannel = null;
            FileChannel outChannel = null;
            try {
                fis = new FileInputStream(srcFile);
                fos = new FileOutputStream(destFile);
                inChannel = fis.getChannel();
                outChannel = fos.getChannel();

                ByteBuffer buf = ByteBuffer.allocate(1024);
                //从输入通道读取到buf
                while (inChannel.read(buf) != -1) {
                    //翻转buf,变成成读模式
                    buf.flip();

                    int outlength = 0;
                    //将buf写入到输出的通道
                    while ((outlength = outChannel.write(buf)) != 0) {
                        System.out.println("写入字节数：" + outlength);
                    }
                    //清除buf,变成写入模式
                    buf.clear();
                }

                //强制刷新磁盘
                outChannel.force(true);
            } finally {
                IOUtil.closeQuietly(outChannel);
                IOUtil.closeQuietly(fos);
                IOUtil.closeQuietly(inChannel);
                IOUtil.closeQuietly(fis);
            }
            long endTime = System.currentTimeMillis();
            Logger.debug("base 复制毫秒数：" + (endTime - startTime));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
