package com.mgenio.aws.mongooseos.neopixels.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by Austin Nelson on 7/6/2017.
 */

public class Utils {

    public static boolean awsIotCredentialsCheck(View v) {
        if (null == Constants.COGNITO_POOL_ID) {
            Snackbar.make(v, "COGNITO_POOL_ID cannot be null", Snackbar.LENGTH_INDEFINITE).show();
            return false;
        } else if (null == Constants.CUSTOMER_SPECIFIC_ENDPOINT) {
            Snackbar.make(v, "CUSTOMER_SPECIFIC_ENDPOINT cannot be null", Snackbar.LENGTH_INDEFINITE).show();
            return false;
        } else if (null == Constants.MY_REGION) {
            Snackbar.make(v, "MY_REGION cannot be null", Snackbar.LENGTH_INDEFINITE).show();
            return false;
        } else {
            return true;
        }
    }
}
