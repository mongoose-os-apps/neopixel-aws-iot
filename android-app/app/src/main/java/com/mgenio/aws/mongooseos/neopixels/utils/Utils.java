package com.mgenio.aws.mongooseos.neopixels.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by Austin Nelson on 7/6/2017.
 */

public class Utils {

    public static boolean awsIotCredentialsCheck(View v) {
        if (isNullOrEmpty(Constants.CUSTOMER_SPECIFIC_ENDPOINT)) {
            Snackbar.make(v, "Constants.CUSTOMER_SPECIFIC_ENDPOINT cannot be null", Snackbar.LENGTH_INDEFINITE).show();
            return false;
        } else if (isNullOrEmpty(Constants.ACCESS_KEY)) {
            Snackbar.make(v, "Constants.ACCESS_KEY cannot be null", Snackbar.LENGTH_INDEFINITE).show();
            return false;
        } else if (isNullOrEmpty(Constants.SECRET_KEY)) {
            Snackbar.make(v, "Constants.SECRET_KEY cannot be null", Snackbar.LENGTH_INDEFINITE).show();
            return false;
        } else if (Constants.MY_REGION == null) {
            Snackbar.make(v, "Constants.MY_REGION cannot be null", Snackbar.LENGTH_INDEFINITE).show();
            return false;
        }
        return true;
    }

    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}
