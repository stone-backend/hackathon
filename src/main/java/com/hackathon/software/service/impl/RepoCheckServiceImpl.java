package com.hackathon.software.service.impl;

import com.hackathon.software.common.Constants;
import com.hackathon.software.common.enums.CodeMergeStatusEnum;
import com.hackathon.software.common.enums.PublishStatusEnum;
import com.hackathon.software.service.BO.RepoPublishInfoBO;
import com.hackathon.software.service.RepoCheckService;
import lombok.RequiredArgsConstructor;
import org.kohsuke.github.GHBranch;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHTag;
import org.kohsuke.github.GitHub;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RepoCheckServiceImpl implements RepoCheckService {

    private final GitHub gitHubClient;

    private static final String RELEASE_BRANCH_PREFIX = "release-v";

    private static final String REPO_ORGANIZE_PREFIX = "NeuralGalaxy/";

    @Override
    public RepoPublishInfoBO getRepoPublishInfo(String repoName, String targetVersion) {
        RepoPublishInfoBO repoPublishInfoBO = new RepoPublishInfoBO();
        repoPublishInfoBO.setRepo(repoName);
        repoPublishInfoBO.setTargetVersion(targetVersion);
        repoPublishInfoBO.setCodeMerged(CodeMergeStatusEnum.UNKNOWN);
        repoPublishInfoBO.setPublished(PublishStatusEnum.UNKNOWN);
        repoPublishInfoBO.setCommitId("");
        GHRepository repository;
        try {
            repository = gitHubClient.getRepository(getFullRepoName(repoName));
            List<GHTag> tags = repository.listTags().toList();
            if (!CollectionUtils.isEmpty(tags)) {
                boolean codeMergedFlag = tags.stream().anyMatch(tag -> tag.getName().equals("v" + targetVersion));
                //先给codeMerged赋值，获取published状态抛exception也不会影响codeMerged结果的输出
                repoPublishInfoBO.setCodeMerged(codeMergedFlag ? CodeMergeStatusEnum.MERGED :
                        CodeMergeStatusEnum.NOT_MERGE);
                //获取master最新commitId
                GHBranch masterBranch = repository.getBranch(Constants.MAIN_BRANCH);
                repoPublishInfoBO.setCommitId(masterBranch.getSHA1());
                if (codeMergedFlag) {
                    //获取对应release分支是否创建成功
                    Map<String, GHBranch> branchMap = repository.getBranches();
                    GHBranch branch = branchMap.get(RELEASE_BRANCH_PREFIX + targetVersion);
                    if (Objects.nonNull(branch)) {
                        repoPublishInfoBO.setPublished(PublishStatusEnum.PUBLISHED);
                    } else {
                        repoPublishInfoBO.setPublished(PublishStatusEnum.NOT_PUBLISH);
                    }
                } else {
                    repoPublishInfoBO.setPublished(PublishStatusEnum.NOT_PUBLISH);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return repoPublishInfoBO;
    }

    private String getFullRepoName(String repoName) {
        return REPO_ORGANIZE_PREFIX + repoName;
    }
}
