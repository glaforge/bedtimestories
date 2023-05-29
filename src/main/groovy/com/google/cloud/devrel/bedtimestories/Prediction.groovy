package com.google.cloud.devrel.bedtimestories

import com.fasterxml.jackson.annotation.JsonProperty
import io.micronaut.serde.annotation.Serdeable

@Serdeable
class Prediction {
    @JsonProperty("safetyAttributes")
    SafetyAttributes safetyAttributes
    @JsonProperty("content")
    String content
}
