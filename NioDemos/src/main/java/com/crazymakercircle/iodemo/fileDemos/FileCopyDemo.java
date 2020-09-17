package com.crazymakercircle.iodemo.fileDemos;

import com.crazymakercircle.NioDemoConfig;
import com.crazymakercircle.util.IOUtil;
import com.crazymakercircle.util.Logger;

import java.io.*;

/**
 * Created by 尼恩@ 疯创客圈
 */

public class FileCopyDemo {

    /**
     * 演示程序的入口函数
     *
     * @param args
     */
    public static void main(String[] args) {
        //演示复制资源文件
        copyResouceFile();

    }

    /**
     * 复制两个资源目录下的文件
     */
    public static void copyResouceFile() {
        String sourcePath = NioDemoConfig.FILE_RESOURCE_SRC_PATH;
        String srcDecodePath = IOUtil.getResourcePath(sourcePath);
        Logger.debug("srcDecodePath=" + srcDecodePath);

        String destPath = NioDemoConfig.FILE_RESOURCE_DEST_PATH;
        String destDecodePath = IOUtil.builderResourcePath(destPath);
        Logger.debug("destDecodePath=" + destDecodePath);


        blockCopyFile(srcDecodePath, destDecodePath);

    }


    /**
     * 复制文件
     *
     * @param srcPath
     * @param destPath
     */
    public static void blockCopyFile(String srcPath, String destPath) {
        InputStream input = null;
        OutputStream output = null;
        File srcFile = new File(srcPath);
        File destFile = new File(destPath);

        try {
            //如果目标文件不存在，则新建
            if (!destFile.exists()) {
                destFile.createNewFile();
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
