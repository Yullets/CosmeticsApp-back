package com.cosmetics.app.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshRequestParams {

    private String refreshToken;

    @JsonCreator
    public RefreshRequestParams(@JsonProperty("refreshToken") String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
