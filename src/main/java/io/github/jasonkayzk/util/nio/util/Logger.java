package io.github.jasonkayzk.util.nio.util;

public class Logger {

    private static java.util.logging.Logger logger;

    static {
        logger = java.util.logging.Logger.getAnonymousLogger();
    }

    public static void info(String str) {
        logger.info(str);
    }

    public static void debug(String str) {
        logger.info(str);
    }
}
