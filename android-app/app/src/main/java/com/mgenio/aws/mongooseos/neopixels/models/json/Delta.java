package com.mgenio.aws.mongooseos.neopixels.models.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Delta {

    @JsonProperty("on")
    private String on;
    @JsonProperty("rgb")
    private Rgb rgb;

    public boolean isOn() {
        return on != null && on.equalsIgnoreCase("on");
    }

    public void setOn(boolean on) {
        this.on = on ? "on" : "off";
    }

    public Rgb getRgb() {
        return rgb;
    }

    public void setRgb(Rgb rgb) {
        this.rgb = rgb;
    }
}
