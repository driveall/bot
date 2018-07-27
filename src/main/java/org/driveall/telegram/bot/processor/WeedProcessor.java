package org.driveall.telegram.bot.processor;

public class WeedProcessor {
    public static boolean process(String text) {
        return (text.contains("есть") && (text.contains("че") || text.contains("чё")));
    }
}
