package com.mgenio.aws.mongooseos.neopixels.models.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class State {
    @JsonProperty("desired")
    private Desired desired;
    @JsonProperty("delta")
    private Delta delta;

    public State() {
    }

    public State(Desired desired) {
        this.desired = desired;
    }

    public Desired getDesired() {
        return desired;
    }

    public void setDesired(Desired desired) {
        this.desired = desired;
    }

    public Delta getDelta() {
        return delta;
    }

    public void setDelta(Delta delta) {
        this.delta = delta;
    }
}
