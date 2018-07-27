package org.driveall.telegram.bot.processor;

public class Processor {
    public static boolean processKurs(String text) {
        return (text.contains("курс") || text.contains("kurs"));
    }

    public static boolean processWeed(String text) {
        return (text.contains("есть") && (text.contains("че") || text.contains("чё")));
    }
}
