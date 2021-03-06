package org.driveall.telegram.bot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import org.driveall.telegram.bot.jsonEntity.Blockchain;
import org.driveall.telegram.bot.jsonEntity.Nbu;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

class CurrencyService {
    private static final String NBU_CURRENCIES_JSON_URL;
    private static final String COINDESK_BITCOIN_JSON_URL;

    private static final RestTemplate rest = new RestTemplate();
    private static final Gson gson = new Gson();

    static {
        Properties prop = PropertyService.getProps();
        NBU_CURRENCIES_JSON_URL = prop.getProperty("nbuJsonUrl");
        COINDESK_BITCOIN_JSON_URL = prop.getProperty("bitcoinJsonUrl");
    }

    //NBU request sender
    private static String sendNbuRequest() {
        return rest.getForObject(URI.create(NBU_CURRENCIES_JSON_URL), String.class);
    }

    //BLOCKCHAIN request sender
    private static String sendBlockchainRequest() {
        return rest.getForObject(URI.create(COINDESK_BITCOIN_JSON_URL), String.class);
    }

    //NBU currencies receiver
    static List<Nbu> getNbuCurrencies() {
        String resp = sendNbuRequest();
        Nbu[] allCurrencies = gson.fromJson(resp, Nbu[].class);
        List<Nbu> out = new LinkedList<>();
        for (Nbu cur : allCurrencies) {
            if (cur.getCc().equals("USD") || cur.getCc().equals("EUR") || cur.getCc().equals("JPY")) {
                out.add(cur);
            }
        }
        return out;
    }

    //BLOCKCHAIN bitcoin currency receiver
    static Blockchain getBitcoinCurrency() {
        String resp = sendBlockchainRequest();
        Map<String, LinkedTreeMap> allCurrencies = gson.fromJson(resp, Map.class);
        return new ObjectMapper().findAndRegisterModules().convertValue(allCurrencies.get("USD"), Blockchain.class);
    }
}
