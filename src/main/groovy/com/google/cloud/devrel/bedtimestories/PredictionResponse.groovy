package com.google.cloud.devrel.bedtimestories

import com.fasterxml.jackson.annotation.JsonProperty
import io.micronaut.serde.annotation.Serdeable

@Serdeable
class PredictionResponse {
    @JsonProperty("predictions")
    List<Prediction> predictions
}
