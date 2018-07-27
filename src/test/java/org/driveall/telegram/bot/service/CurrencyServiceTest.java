package org.driveall.telegram.bot.service;

import org.driveall.telegram.bot.jsonEntity.Blockchain;
import org.driveall.telegram.bot.jsonEntity.Nbu;
import org.junit.Test;

import java.util.List;

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
        Blockchain result = CurrencyService.getBitcoinCurrency();
        System.out.println(result);
    }

    @Test
    public void getNbuCurrenciesTest() {
        List<Nbu> result = CurrencyService.getNbuCurrencies();
        System.out.println(result);
    }
}
