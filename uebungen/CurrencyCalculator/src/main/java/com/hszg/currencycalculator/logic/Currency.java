package com.hszg.currencycalculator.logic;
public class Currency {

    private String name;
    private double exchangeRateToUSD;

    Currency(String name, double exchangeRateToUSD) {
        this.name = name;
        this.exchangeRateToUSD = exchangeRateToUSD;
    }

    String getName() {
        return name;
    }

    Double getExchangeRate()
    {
        return exchangeRateToUSD; 
    }

    void setName(String name) {
        this.name = name;
    }

    void setExchangeRate(double exchangeRateToUSD) {
        this.exchangeRateToUSD = exchangeRateToUSD;
    }

}

