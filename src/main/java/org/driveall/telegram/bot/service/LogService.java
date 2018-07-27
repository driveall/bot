package org.driveall.telegram.bot.service;

import org.driveall.telegram.bot.Bot;

public class LogService {
    private static final String INCOMING = "INCOMING: '";
    private static final String OUTGOING = "OUTGOING: ";

    public static String incomingMsg(String text, String chat) {
        return INCOMING + text + "' from '" + chat + "' chat;";
    }

    public static String outgoingMsg(String text) {
        return OUTGOING + text;
    }

    public static String startMsg() {
        return Bot.BOT_USERNAME + " registered, starting listening...";
    }
}
