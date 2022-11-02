package com.hackathon.software.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CodeMergeStatusEnum {
    NOT_MERGE("未合并"), MERGED("已合并"), UNKNOWN("未知");

    private String text;
}
