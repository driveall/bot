package org.driveall.telegram.bot.processor;

public class Processor {
    private static final String ERROR = "error";

    public static boolean processKurs(String text) {
        return (text.contains("курс") || text.contains("kurs"));
    }

    public static boolean processWeed(String text) {
        return (text.contains("есть") && (text.contains("че") || text.contains("чё")));
    }

    public static String processWake(String text) {
        if (text.startsWith("wake") || text.startsWith("вейк")) {
            text = text.substring(4);
            if (text.isEmpty()) {
                return "info";
            }
            text = text.substring(1);
            if (text.startsWith("create") && text.length() > 6) {
                text = text.substring(7);
                if (text.length() == 0) return ERROR;
                return "create " + text;
            }
            if (text.startsWith("+")) {
                if (text.length() == 1) {
                    return "subscribe";
                }
                text = text.substring(2);
                try {
                    int evtNum = Integer.parseInt(text);
                    return "numSubscribe " + evtNum;
                } catch (Exception e) {
                    return ERROR;
                }
            }
            if (text.startsWith("-") && text.length() == 1) {
                return "unsubscribe";
            }
            return ERROR;
        }
        return ERROR;
    }
}
