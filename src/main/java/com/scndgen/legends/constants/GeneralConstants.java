package com.scndgen.legends.constants;

import java.time.LocalDate;

/**
 * Created by ifunga on 26/05/2017.
 */
public class GeneralConstants {
    public static final int INFINITE_TIME = 181;
    public static String years() {
        String ans = "2011";
        int currentYear = LocalDate.now().getYear();
        if (!(currentYear == 2011)) {
            ans = "2011 - " + currentYear;
        }
        return ans;
    }
}
