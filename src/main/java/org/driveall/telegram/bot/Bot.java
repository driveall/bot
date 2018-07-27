package org.driveall.telegram.bot;

import org.driveall.telegram.bot.processor.Processor;
import org.driveall.telegram.bot.service.BotService;
import org.driveall.telegram.bot.service.LogService;
import org.driveall.telegram.bot.service.MessageService;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.sql.Timestamp;

public class Bot extends TelegramLongPollingBot {
    private static final String BOT_TOKEN = "669371245:AAFVuryUiTeEiX7P95pKxBjePrwe4_Iyj4w";
    public static final String BOT_USERNAME = "DriveallBot";

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi botapi = new TelegramBotsApi();
        try {
            botapi.registerBot(new Bot());
            System.out.println(LogService.startMsg());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method for receiving messages
     *
     * @param update includs chat message
     */
    public void onUpdateReceived(Update update) {
        Message msg = update.getMessage();

        String text = msg.getText();
        text = text != null ? BotService.transformQueryIfValid(text.toLowerCase()) : null;

        if (text != null) {
            System.out.println(new Timestamp(System.currentTimeMillis()).toString());
            System.out.println(LogService.incomingMsg(text, msg.getChat().getTitle()));

            if (Processor.processKurs(text)) {
                sendMsg(update.getMessage(), MessageService.kurs());
            } else if (Processor.processWeed(text)) {
                sendMsg(update.getMessage(), MessageService.weed());
            } else {
                sendMsg(update.getMessage(), MessageService.info());
            }
        }
    }

    /**
     * Method for setting message and sening it
     *
     * @param msg  message of chat
     * @param text message text
     */
    private synchronized void sendMsg(Message msg, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(msg.getChatId().toString());
        sendMessage.setText(text);
        try {
            this.sendMessage(sendMessage);
            System.out.println(LogService.outgoingMsg(text));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method returns the name of the bot which typed on registration
     *
     * @return the name of the bot
     */
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    /**
     * Method returns0 token of the bot for Telegram connection
     *
     * @return token for bot
     */
    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }
}