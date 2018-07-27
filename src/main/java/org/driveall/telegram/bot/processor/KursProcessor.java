package org.driveall.telegram.bot.processor;

public class KursProcessor {
    public static boolean process(String text) {
        return (text.contains("курс") || text.contains("kurs"));
    }
}
