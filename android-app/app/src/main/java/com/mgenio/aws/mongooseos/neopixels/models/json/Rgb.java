package com.mgenio.aws.mongooseos.neopixels.models.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Rgb {
    @JsonProperty("b")
    private int b;
    @JsonProperty("g")
    private int g;
    @JsonProperty("r")
    private int r;

    public Rgb() {
    }

    public Rgb(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    @Override
    public String toString() {
        return "Rgb{" +
                "b=" + b +
                ", g=" + g +
                ", r=" + r +
                '}';
    }
}
