package com.okrojp.cryptoApp.service;

import com.okrojp.cryptoApp.model.Crypto;
import com.okrojp.cryptoApp.model.Currency;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CryptoServiceImpl implements CryptoService {

    private static final String URL = "http://api.coinlayer.com/api/live?access_key=6abfdee59ef6a0f2f848d6312573b080&symbols=";

    @Getter
    private final RestTemplate restTemplate;

    @Override
    public String getCurrentPrice(String from, List<String> to, Double amount) {
        Crypto sourceCurrencyInfo = getCryptoInfo(from);
        Currency sourceCurrency = new Currency(sourceCurrencyInfo.getRates().getDetails().get(from), amount);
        List<Currency> currencies = new ArrayList<>();
        for (String value : to) {
            Crypto currencyToInfo = getCryptoInfo(value);
            Currency currencyTo = new Currency(currencyToInfo.getRates().getDetails().get(value), amount);

            currencyTo.setAmount(((sourceCurrency.getRate() * amount) / currencyToInfo.getRates().getDetails().get(value)));
            currencyTo.setFee(sourceCurrency.getRate() * 0.01);
            currencyTo.setResult((currencyToInfo.getRates().getDetails().get(value) * currencyTo.getAmount()) - currencyTo.getFee());

            currencies.add(currencyTo);
        }
        return currencies.toString();
    }

    @Override
    public Crypto getCryptoInfo(String currency) {
        // połączenie z api udostepniajacym informacje o cenach kryptowalut
        return restTemplate.getForObject(URL + currency, Crypto.class);
    }
}
