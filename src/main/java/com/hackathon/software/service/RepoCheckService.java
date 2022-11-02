package com.hackathon.software.service;

import com.hackathon.software.service.BO.RepoPublishInfoBO;

public interface RepoCheckService {
    /**
     *
     * @author stone
     * @param repoName 仓库名称，全名，如NeuralGalaxy/ngiq-research-platform
     * @param targetVersion 发版的目标版本
     * @return RepoPublishInfoBO 当前发版结果信息
     */
    RepoPublishInfoBO getRepoPublishInfo(String repoName, String targetVersion);
}
