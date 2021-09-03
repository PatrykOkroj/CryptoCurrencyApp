package com.okrojp.cryptoApp.service;

import com.okrojp.cryptoApp.model.Crypto;
import java.util.List;

public interface CryptoService {
    String getCurrentPrice(String from, List<String> to, Double amount);

    Crypto getCryptoInfo(String currency);
}
