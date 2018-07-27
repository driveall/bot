package org.driveall.telegram.bot.service;

public class BotService {

    public static String transformQueryIfValid(String text) {
        //detecting is text is our query
        if (text != null && text.startsWith("/")) {
            text = text.substring(1);
            if (text.startsWith("dd ") || text.startsWith("дд ")) {
                text = text.substring(3);
                return text;
            } else if (text.startsWith("d ") || text.startsWith("д ")) {
                text = text.substring(2);
                return text;
            }
        }
        return null;
    }
}
