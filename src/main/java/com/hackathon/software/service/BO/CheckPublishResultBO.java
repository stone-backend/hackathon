package com.hackathon.software.service.BO;

import com.hackathon.software.common.enums.PublishStatusEnum;
import lombok.Data;

@Data
public class CheckPublishResultBO {
    private PublishStatusEnum publishStatusEnum;

    private String msg;
}
