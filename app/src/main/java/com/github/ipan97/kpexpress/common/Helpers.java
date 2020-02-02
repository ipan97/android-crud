package com.github.ipan97.kpexpress.common;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * @author Ipan Taupik Rahman.
 */
public class Helpers {

    public static String numberFormatIndonesia(Object value) {
        DecimalFormat currencyInstance = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        currencyInstance.setDecimalFormatSymbols(formatRp);
        return currencyInstance.format(value);
    }

}
