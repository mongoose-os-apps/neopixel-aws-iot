package com.mgenio.aws.mongooseos.neopixels.models.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Thing {
    @JsonProperty("state")
    private State state;
    @JsonProperty("version")
    private Integer version;
    @JsonProperty("timestamp")
    private Integer timestamp;

    public Thing() {
    }

    public Thing(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }
}
