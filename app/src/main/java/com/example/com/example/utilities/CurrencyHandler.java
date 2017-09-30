package com.example.com.example.utilities;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by woodw on 9/30/2017.
 */

//shelanskey US4 - Utility for manipulating currency values
public class CurrencyHandler {
    private static final String DOLLAR = "Dollar";
    private static final String POUND = "Pound";
    private static final String PESOS = "Pesos";
    private static final String RUPEES = "Rupees";
    private static final String EURO = "Euro";
    private static Map<String, Double> currencyConversionMap;
    static {
        currencyConversionMap = new HashMap<String, Double>();
        currencyConversionMap.put(DOLLAR, 1.0);
        currencyConversionMap.put(POUND, 1.34);
        currencyConversionMap.put(PESOS, 18.25);
        currencyConversionMap.put(RUPEES, 104.51);
        currencyConversionMap.put(EURO, 1.18);
    }

    public CurrencyHandler() {

    }

    public static Double convertToDollars(String currencyType, Double amount){
        Double conversionRatio = currencyConversionMap.get(currencyType);
        Double newAmount = amount / conversionRatio;
        return newAmount;
    }

    public static Double convertToNative(String currencyType, Double amount){
        Double conversionRatio = currencyConversionMap.get(currencyType);
        Double newAmount = amount * conversionRatio;
        return newAmount;
    }
    public static int lookupPosition(String currencyType){
        int pos = 0;
        if (currencyType.equals(DOLLAR))
            pos = 0;
        if (currencyType.equals(POUND))
            pos = 1;
        if (currencyType.equals(PESOS))
            pos = 2;
        if (currencyType.equals(RUPEES))
            pos = 3;
        if (currencyType.equals(EURO))
            pos = 4;

        return pos;
    }

}
