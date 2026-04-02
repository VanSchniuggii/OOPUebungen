package uebungen.src.main.java.com.hszg.exercises.aufgabe1.Logic;

import java.util.List;

/**
 * Defines operations for converting between currencies and managing available currencies.
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

    
    /**
     * Returns a list of all configured currencies.
     * 
     * @return a list of all configured currencies
     *
     * @param currencies the list of currencies to save
     */
    void saveCurrencyData(List<Currency> currencies);


    /**
     * Loads currency data from a persistent storage (e.g., a JSON file) and initializes the internal currency list.
     * This method should be called during the initialization of the currency calculator to ensure that previously saved currencies are available for conversion.
     * 
     * 
     */
    void loadCurrencyData();
}
