package com.hackathon.software.service.BO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MsgContent {

    @JsonProperty("msg_type")
    private String msgType;

    @JsonProperty("content")
    private Content content;

    @Data
    public static class Content {

        @JsonProperty("text")
        private String text;

    }
}
