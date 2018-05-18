package com.mgenio.aws.mongooseos.neopixels.async;

import android.os.AsyncTask;
import android.util.Log;

import com.amazonaws.services.iotdata.AWSIotDataClient;
import com.amazonaws.services.iotdata.model.GetThingShadowRequest;
import com.amazonaws.services.iotdata.model.GetThingShadowResult;
import com.amazonaws.services.iotdata.model.ResourceNotFoundException;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mgenio.aws.mongooseos.neopixels.interfaces.OnGetThingShadow;
import com.mgenio.aws.mongooseos.neopixels.models.ThingData;
import com.mgenio.aws.mongooseos.neopixels.models.json.Thing;

/**
 * Created by Austin Nelson on 5/25/2017.
 */
public class GetThingShadow extends AsyncTask<String, Void, String[]> {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper() // change some configuration
            .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE) // remove visibility of all accessors
            .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY) // allow only fields
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false) // don't fail on unknown fields
            .enable(SerializationFeature.INDENT_OUTPUT);

    private AWSIotDataClient awsIotDataClient;
    private OnGetThingShadow onGetThingShadow;

    public GetThingShadow(AWSIotDataClient awsIotDataClient, OnGetThingShadow onGetThingShadow) {
        this.awsIotDataClient = awsIotDataClient;
        this.onGetThingShadow = onGetThingShadow;
    }

    @Override
    protected String[] doInBackground(String... params) {
        final GetThingShadowRequest getThingShadowRequest = new GetThingShadowRequest().withThingName(params[0]);
        final GetThingShadowResult result;

        try {
            result = awsIotDataClient.getThingShadow(getThingShadowRequest);
        } catch (ResourceNotFoundException e) {
            return null;
        }

        final byte[] bytes = new byte[result.getPayload().remaining()];
        result.getPayload().get(bytes);
        final String resultString = new String(bytes);
        final String[] results = new String[2];
        results[0] = params[0];
        results[1] = resultString;

        return results;
    }

    @Override
    protected void onPostExecute(String[] results) {
        if (results == null) {
            onGetThingShadow.onGetResult(null);
            return;
        }

        try {
            Log.i("GetShadowThing", results[1]);
            final Thing thing = OBJECT_MAPPER.readValue(results[1], Thing.class);
            final ThingData thingData = new ThingData();
            thingData.setName(results[0]);
            thingData.setThing(thing);

            onGetThingShadow.onGetResult(thingData);
        } catch (Exception e) {
            Log.e("GetThingShadow", "Failed to parse shadow thing", e);
            onGetThingShadow.onGetResult(null);
        }
    }
}
