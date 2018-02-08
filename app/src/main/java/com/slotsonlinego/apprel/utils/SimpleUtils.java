package com.slotsonlinego.apprel.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

public final class SimpleUtils {

    private static final String ISO_CODE_RU_1 = "ru";
    private static final String ISO_CODE_RU_2 = "rus";

    private SimpleUtils() {}

    public static boolean isSimCardInserted(Context context) {
        String countryCodeValue = null;
        if (context.getSystemService(Context.TELEPHONY_SERVICE) != null){
                     countryCodeValue = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE))
                             .getSimCountryIso();
        } else {
            return false;
        }

        return countryCodeValue != null && (
                countryCodeValue.equalsIgnoreCase(ISO_CODE_RU_1)
                        || countryCodeValue.equalsIgnoreCase(ISO_CODE_RU_2)
        );
    }
}
