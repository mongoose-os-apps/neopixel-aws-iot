package com.mgenio.aws.mongooseos.neopixels.models;

import com.mgenio.aws.mongooseos.neopixels.models.json.Thing;

/**
 * Created by Austin Nelson on 6/28/2017.
 */

public class ThingData {

    private String name;
    private Thing thing;

    public ThingData() {

    }

    public ThingData(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Thing getThing() {
        return thing;
    }

    public void setThing(Thing thing) {
        this.thing = thing;
    }
}
