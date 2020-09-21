package io.github.jasonkayzk.util.nio.channel.fileChannel;

import io.github.jasonkayzk.util.nio.config.DemoConfig;
import io.github.jasonkayzk.util.nio.util.IOUtil;
import io.github.jasonkayzk.util.nio.util.Logger;

import java.io.*;

public class FileReadDemo {

    public static void main(String[] args) {
        readSourceFile();
    }

    /**
     * 读取
     */
    public static void readSourceFile() {
        String sourcePath = DemoConfig.FILE_RESOURCE_SRC_PATH;
        String decodePath = IOUtil.getResourcePath(sourcePath);
        Logger.debug("decodePath=" + decodePath);
        readFile(decodePath);
    }

    /**
     * 读取文件内容并输出
     *
     * @param fileName 文件名
     */
    public static void readFile(String fileName) {
        File file = new File(fileName);
        try {
            Reader reader = new FileReader(file);
            BufferedReader buffered = new BufferedReader(reader);
            String data;
            while ((data = buffered.readLine()) != null) {
                Logger.debug(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
