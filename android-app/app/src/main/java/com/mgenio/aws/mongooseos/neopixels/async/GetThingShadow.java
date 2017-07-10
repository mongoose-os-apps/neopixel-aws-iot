package com.mgenio.aws.mongooseos.neopixels.async;

/**
 * Created by Austin Nelson on 5/25/2017.
 */

import android.os.AsyncTask;
import android.util.Log;

import com.amazonaws.services.iotdata.AWSIotDataClient;
import com.amazonaws.services.iotdata.model.GetThingShadowRequest;
import com.amazonaws.services.iotdata.model.GetThingShadowResult;
import com.amazonaws.services.iotdata.model.ResourceNotFoundException;
import com.mgenio.aws.mongooseos.neopixels.interfaces.OnGetThingShadow;
import com.mgenio.aws.mongooseos.neopixels.models.Thing;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Austin Nelson on 5/25/2017.
 */

public class GetThingShadow extends AsyncTask<String, Void, String[]> {

    private AWSIotDataClient awsIotDataClient;
    private OnGetThingShadow onGetThingShadow;

    public GetThingShadow(AWSIotDataClient awsIotDataClient, OnGetThingShadow onGetThingShadow) {
        this.awsIotDataClient = awsIotDataClient;
        this.onGetThingShadow = onGetThingShadow;
    }

    @Override protected String[] doInBackground(String... params) {
        GetThingShadowRequest getThingShadowRequest = new GetThingShadowRequest().withThingName(params[0]);
        GetThingShadowResult result = null;

        try {
            result = awsIotDataClient.getThingShadow(getThingShadowRequest);
        } catch (ResourceNotFoundException e) {
            return null;
        }

        byte[] bytes = new byte[result.getPayload().remaining()];
        result.getPayload().get(bytes);
        String resultString = new String(bytes);
        String[] results = new String[2];
        results[0] = params[0];
        results[1] = resultString;

        return results;
    }

    @Override protected void onPostExecute(String[] results) {
        if (null == results) {
            onGetThingShadow.onGetResult(null);
            return;
        }

        try {
            JSONObject object = new JSONObject(results[1]);
            JSONObject state = object.getJSONObject("state");
            JSONObject reported = state.getJSONObject("reported");

            Thing thing = new Thing();
            thing.setName(results[0]);
            thing.setState(state);

            onGetThingShadow.onGetResult(thing);

        } catch (JSONException e) {
            Log.e("GetThingShadow", e.toString());
            onGetThingShadow.onGetResult(null);
        }
    }
}
