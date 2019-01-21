package org.driveall.telegram.bot;

import org.driveall.telegram.bot.processor.Processor;
import org.driveall.telegram.bot.service.BotService;
import org.driveall.telegram.bot.service.LogService;
import org.driveall.telegram.bot.service.MessageService;
import org.driveall.telegram.bot.service.PropertyService;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.sql.Timestamp;
import java.util.Properties;

public class Bot extends TelegramLongPollingBot {
    private static final String BOT_TOKEN;
    public static final String BOT_USERNAME;

    static {
        Properties prop = PropertyService.getProps();
        BOT_TOKEN = prop.getProperty("botToken");
        BOT_USERNAME = prop.getProperty("botUsername");
    }

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
        String text = msg.getText().toLowerCase();

        //info
        if (Processor.init(text)) {
            sendMsg(update.getMessage(), MessageService.info());
            return;
        }

        text = BotService.transformQueryIfValid(text);

        if (text != null) {
            System.out.println(new Timestamp(System.currentTimeMillis()).toString());
            System.out.println(LogService.incomingMsg(text, msg.getChat().getTitle()));

            //for kurs query
            if (Processor.processKurs(text)) {
                sendMsg(update.getMessage(), MessageService.kurs());
                return;
            }

            //for weed query
            if (Processor.processWeed(text)) {
                sendMsg(update.getMessage(), MessageService.weed());
                return;
            }

            //for wake query
            if (!Processor.processWake(text).equals("error")) {
                try {
                    sendMsg(update.getMessage(), MessageService.wake(Processor.processWake(text), update.getMessage().getFrom().getFirstName()));
                } catch (Exception e) {
                    System.out.println("error sending message to telegram, stacktrace:");
                    e.printStackTrace();
                }
                return;
            }

            //for travel query
            if (!Processor.processTravel(text).equals("error")) {
                try {
                    sendMsg(update.getMessage(), MessageService.travel(Processor.processTravel(text), update.getMessage().getFrom().getFirstName()));
                } catch (Exception e) {
                    System.out.println("error sending message to telegram, stacktrace:");
                    e.printStackTrace();
                }
                return;
            }

            //info
            sendMsg(update.getMessage(), MessageService.info());
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