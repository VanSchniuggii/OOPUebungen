package uebungen.src.main.java.com.hszg.exercises.aufgabe1bis3.Logic;
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
