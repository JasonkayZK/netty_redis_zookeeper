/**
 * Created by nien woo
 * Date: 17-12-30
 * Time: 下午4:50
 */
package com.crazymakercircle.util;


import java.io.*;

public class FileLogger
{

    private static File logFile;

    static
    {
        try
        {
            String logDir = IOUtil.builderResourcePath("/AOP/");
            File dir = new File(logDir);
            if (!dir.exists())
            {
                dir.mkdir();
            }
            String filePath = logDir + Dateutil.getToday() + ".txt";

            logFile = new File(filePath);
            if (!logFile.exists())
            {

                logFile.createNewFile();
            }

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    /**
     * 带着方法名输出，方法名称放在前面
     *
     * @param s 待输出的字符串形参
     */
    public static void info(Object s)
    {
        Writer fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try
        {
            fileWriter = new FileWriter(logFile, true);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.append(Dateutil.getNow());
            bufferedWriter.append(" ");
            bufferedWriter.append(s.toString());
            bufferedWriter.append("\r");
            bufferedWriter.flush();

        } catch (IOException e)
        {
            e.printStackTrace();

        } finally
        {
            IOUtil.closeQuietly(bufferedWriter);

        }
    }

}
