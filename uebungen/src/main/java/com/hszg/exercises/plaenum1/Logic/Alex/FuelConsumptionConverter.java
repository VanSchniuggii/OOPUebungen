package uebungen.src.main.java.com.hszg.exercises.plaenum1.Logic.Alex;
/**
 * Defines operations for converting fuel consumption between liters per 100 kilometers and miles per gallon.
 */
public interface FuelConsumptionConverter {

    /**
     * Converts fuel consumption from liters per 100 kilometers to miles per gallon.
     * @param litersPer100Km the fuel consumption in liters per 100 kilometers
     * @return the equivalent fuel consumption in miles per gallon
     */
    double convertLitersPer100KmToMilesPerGallon(double litersPer100Km);

    /**
     * Converts fuel consumption from miles per gallon to liters per 100 kilometers.
     * @param milesPerGallon the fuel consumption in miles per gallon
     * @return the equivalent fuel consumption in liters per 100 kilometers
     */
    double convertMilesPerGallonToLitersPer100Km(double milesPerGallon);
}
