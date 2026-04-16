package com.hszg.currencycalculator.logic;

/**
 * Represents a currency with its name and exchange rate relative to a reference currency (e.g., USD).
 * This class encapsulates the properties of a currency and provides methods to access and modify them.
 * @author Szczuka, Alexander
 * @author Wölflick, Richard
 */
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

