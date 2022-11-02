package com.hackathon.software.service.BO;

import com.hackathon.software.common.enums.CodeMergeStatusEnum;
import com.hackathon.software.common.enums.PublishStatusEnum;
import lombok.Data;

@Data
public class RepoPublishInfoBO {
    private String repo;

    private String targetVersion;

    private CodeMergeStatusEnum codeMerged;

    private PublishStatusEnum published;

    private String commitId;
}
