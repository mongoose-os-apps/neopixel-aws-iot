package com.mgenio.aws.mongooseos.neopixels.models.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Desired {
    @JsonProperty("on")
    private Boolean on;
    @JsonProperty("rgb")
    private Rgb rgb;

    public Desired() {
    }

    public Desired(Boolean on, Rgb rgb) {
        this.on = on;
        this.rgb = rgb;
    }

    public boolean isOn() {
        return on != null && on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    public Rgb getRgb() {
        return rgb;
    }

    public void setRgb(Rgb rgb) {
        this.rgb = rgb;
    }
}
