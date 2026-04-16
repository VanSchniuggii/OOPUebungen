package com.hszg.currencycalculator.logic;

import java.util.List;

/**
 * Defines operations for converting between currencies and managing available currencies. Such as:
 * <ul>
 *      <li><b>Converting</b> an amount from one currency to another.</li>
 *      <li><b>Retrieving</b> exchange rates between currencies.</li>
 *      <li><b>Adding</b> new currencies with their exchange rates relative to a reference currency.</li>
 *      <li><b>Getting a list</b> of all available currency names.</li>
 * </ul>
 */
public interface CurrencyCalculator {
    /**
     * Converts a monetary amount from one currency to another.
     *
     * @param amount the amount to convert
     * @param fromCurrency the source currency name
     * @param toCurrency the target currency name
     * @return the converted amount in the target currency
     */
    double convert(double amount, String fromCurrency, String toCurrency);

    /**
     * Returns the exchange rate between two currencies.
     *
     * @param fromCurrency the source currency
     * @param toCurrency the target currency
     * @return the exchange rate from source to target currency
     */
    double getExchangeRate(Currency fromCurrency, Currency toCurrency);

    /**
     * Adds a currency and defines its rate relative to a reference currency.
     *
     * @param name the name of the currency to add
     * @param exchangeRate the exchange rate relative to the reference currency
     * @param referenceCurrency the reference currency name
     */
    void addCurrency(String name, double exchangeRate, String referenceCurrency);

    /**
     * Returns all available currency names.
     *
     * @return a list of configured currency names
     */
    List<String> getCurrencyNames();  

}

