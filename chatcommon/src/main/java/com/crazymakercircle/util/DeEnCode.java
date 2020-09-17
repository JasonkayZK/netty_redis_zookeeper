package com.crazymakercircle.util;

import java.nio.charset.Charset;

public class DeEnCode {

    private static final String key0 = "FECOI()*&<MNCXZPKL";
    private static final Charset charset = Charset.forName("UTF-8");
    private static byte[] keyBytes = key0.getBytes(charset);

    public static void encode(byte[] b, int length) {
//        int minlen=Math.min(b.length,length);

        for (int i = 0, size = length; i < size; i++) {
            for (byte kb : keyBytes) {
                b[i] = (byte) (b[i] ^ kb);
            }
        }
    }

    public static void decode(byte[] dee, int length) {
//        int minlen=Math.min(dee.length,length);

        for (int i = 0, size = length; i < size; i++) {
            for (byte kb : keyBytes) {
                dee[i] = (byte) (dee[i] ^ kb);
            }
        }
    }

    public static void main(String[] args) {
        String s = "you are right ok 测试";
        byte[] sb = s.getBytes();
        encode(sb, sb.length);
        decode(sb, sb.length);
        Logger.info(new String(sb));
    }
} 


