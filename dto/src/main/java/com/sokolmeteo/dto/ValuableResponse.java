package com.sokolmeteo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public class ValuableResponse<T> extends BaseResponse {
    @JsonProperty("result")
    @Getter @Setter
    private T result;

    public ValuableResponse(T result) {
        super("OK");
        this.result = result;
    }
}
