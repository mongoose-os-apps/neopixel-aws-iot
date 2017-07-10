package com.mgenio.aws.mongooseos.neopixels.models;

import org.json.JSONObject;

/**
 * Created by Austin Nelson on 6/28/2017.
 */

public class Thing {

    private String name;
    private JSONObject state;

    public Thing() {

    }

    public Thing(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JSONObject getState() {
        return state;
    }

    public void setState(JSONObject state) {
        this.state = state;
    }
}
