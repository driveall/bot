package org.driveall.telegram.bot.service;

import org.junit.Test;

public class CurrencyServiceTest {

    @Test
    public void sendNbuRequestTest() {
        String result = CurrencyService.sendNbuRequest();
        System.out.println(result);
    }

    @Test
    public void sendBlockchainRequestTest() {
        String result = CurrencyService.sendBlockchainRequest();
        System.out.println(result);
    }

    @Test
    public void getBitcoinCurrencyTest() {
        Double result = CurrencyService.getBitcoinCurrency();
        System.out.println(result);
    }
}
