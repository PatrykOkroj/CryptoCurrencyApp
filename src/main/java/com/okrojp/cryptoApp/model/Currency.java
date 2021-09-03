package com.okrojp.cryptoApp.model;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class Currency {
    private Double rate;
    private Double amount;
    private Double result;
    private Double fee;

    public Currency(Double rate, Double amount) {
        this.rate = rate;
        this.amount = amount;
    }
}
