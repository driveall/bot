package org.driveall.telegram.bot.service;

public class MessageService {
    public static String kurs() {
        return "Курс НБУ:\n" + CurrencyService.getNbuCurrencies() + "\n" + "Курс битка:\n" + CurrencyService.getBitcoinCurrency().floatValue() + "USD;";
    }

    public static String weed() {
        return "Не, все закончилось, спроси позже";
    }

    public static String info() {
        return "INFO:\n" +
                "All commands are case insensitive;\n" +
                "To call bot begin message with: '/d', '/dd', '/д', '/дд';\n" +
                "COMMANDS:\n" +
                "курс or kurs - currencies of (USD, EUR and JPY to UAH) and (BTC to USD);\n" +
                "";
    }
}
