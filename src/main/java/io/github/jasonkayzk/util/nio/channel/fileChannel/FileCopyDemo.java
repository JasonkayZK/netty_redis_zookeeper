package io.github.jasonkayzk.util.nio.channel.fileChannel;

import io.github.jasonkayzk.util.nio.config.DemoConfig;
import io.github.jasonkayzk.util.nio.util.IOUtil;
import io.github.jasonkayzk.util.nio.util.Logger;

import java.io.*;

public class FileCopyDemo {

    public static void main(String[] args) {
        String srcDecodePath = IOUtil.getResourcePath(DemoConfig.FILE_RESOURCE_SRC_PATH);
        String destDecodePath = IOUtil.builderResourcePath(DemoConfig.FILE_RESOURCE_DEST_PATH);

        copyResourceFile(srcDecodePath, destDecodePath);
    }

    /**
     * 复制两个资源目录下的文件
     */
    public static void copyResourceFile(String srcPath, String destPath) {
        Logger.debug("srcDecodePath=" + srcPath);
        Logger.debug("destDecodePath=" + destPath);

        blockCopyFile(srcPath, destPath);
    }

    /**
     * 复制文件
     */
    public static void blockCopyFile(String srcPath, String destPath) {
        InputStream input = null;
        OutputStream output = null;
        File srcFile = new File(srcPath);
        File destFile = new File(destPath);

        try {
            //如果目标文件不存在，则新建
            if (!destFile.exists()) {
                boolean fileNewFile = destFile.createNewFile();
                if (!fileNewFile) {
                    throw new RuntimeException("create new file: " + destFile + " err!");
                }
            }

            long startTime = System.currentTimeMillis();

            input = new FileInputStream(srcFile);
            output = new FileOutputStream(destFile);
            byte[] buf = new byte[1024];
            int bytesRead;

            while ((bytesRead = input.read(buf)) != -1) {
                output.write(buf, 0, bytesRead);
            }
            output.flush();

            long endTime = System.currentTimeMillis();
            Logger.debug("IO流复制毫秒数：" + (endTime - startTime));

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtil.closeQuietly(input);
            IOUtil.closeQuietly(output);
        }
    }

}
