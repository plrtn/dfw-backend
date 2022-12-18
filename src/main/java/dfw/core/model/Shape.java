package dfw.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Shape(
        @JsonProperty("id") long id,
        @JsonProperty("geojson") String geojson) {
}
