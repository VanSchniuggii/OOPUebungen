package com.hszg.currencycalculator.logic;
public class Currency {

    String name;
    double exchangeRateToUSD;

    public Currency(String name, double exchangeRateToUSD) {
        this.name = name;
        this.exchangeRateToUSD = exchangeRateToUSD;
    }

    public String getName() {
        return name;
    }

}

