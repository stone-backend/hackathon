package com.hackathon.software.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PublishStatusEnum {
    NOT_PUBLISH("未发布"), PUBLISHED("已发布"), UNKNOWN("未知");

    private String text;
}
