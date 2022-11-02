package com.hackathon.software.service.impl;

import com.hackathon.software.client.FeiShuBotClient;
import com.hackathon.software.controller.request.VersionCheckRequest;
import com.hackathon.software.service.BO.MsgContent;
import com.hackathon.software.service.BO.RepoPublishInfoBO;
import com.hackathon.software.service.RepoCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

@Service
@RequiredArgsConstructor
public class FeiShuBotService {

    private final RepoCheckService repoCheckService;

    private final FeiShuBotClient feiShuBotClient;

    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;;

    private static final String REPO_FORMAT = "%-25s";

    public void sendPublishInfo2FeiShu(List<VersionCheckRequest> versionCheckRequestList) {
        if (CollectionUtils.isEmpty(versionCheckRequestList)) {
            return;
        }
        List<RepoPublishInfoBO> repoPublishInfoBOS = buildPublishInfosAsync(versionCheckRequestList);
        MsgContent msgContent = buildMsgContent(repoPublishInfoBOS);
        feiShuBotClient.send(msgContent);
        System.out.println(LocalDateTime.now());
    }

    private List<RepoPublishInfoBO> buildPublishInfos(List<VersionCheckRequest> versionCheckRequestList) {
        List<RepoPublishInfoBO> repoPublishInfoBOList = new ArrayList<>();
        versionCheckRequestList.forEach(versionCheckRequest -> {
            repoPublishInfoBOList.add(repoCheckService.getRepoPublishInfo(versionCheckRequest.getRepo(),
                    versionCheckRequest.getVersion()));
            System.out.println("query " + versionCheckRequest.getRepo() + ": " + LocalDateTime.now());
        });
        return repoPublishInfoBOList;
    }

    private List<RepoPublishInfoBO> buildPublishInfosAsync(List<VersionCheckRequest> versionCheckRequestList) {
        List<RepoPublishInfoBO> repoPublishInfoBOList = new Vector<>();
        final CountDownLatch latch = new CountDownLatch(versionCheckRequestList.size());
        versionCheckRequestList.forEach(versionCheckRequest -> {
            threadPoolTaskExecutor.submit(new Thread(()->{
                repoPublishInfoBOList.add(repoCheckService.getRepoPublishInfo(versionCheckRequest.getRepo(),
                        versionCheckRequest.getVersion()));
                latch.countDown();
                System.out.println("query " + versionCheckRequest.getRepo() + ": " + LocalDateTime.now());
            }));
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return repoPublishInfoBOList;
    }

    private MsgContent buildMsgContent(List<RepoPublishInfoBO> repoPublishInfoBOList) {
        MsgContent msgContent = new MsgContent();
        msgContent.setMsgType("text");
        msgContent.setContent(buildMsgContentText(repoPublishInfoBOList));
        return msgContent;
    }

    private MsgContent.Content buildMsgContentText(List<RepoPublishInfoBO> repoPublishInfoBOList) {
        MsgContent.Content content = new MsgContent.Content();
        StringBuilder text = new StringBuilder();
        text.append(String.join(" | ", String.format(REPO_FORMAT, "仓库"), "版本", "代码合并情况",
                "制品发布情况", "commit id"));
        text.append("\n");
        repoPublishInfoBOList.forEach(repoPublishInfoBO -> {
            text.append(String.join(" | ", String.format(REPO_FORMAT, repoPublishInfoBO.getRepo()),
                    repoPublishInfoBO.getTargetVersion(), repoPublishInfoBO.getCodeMerged().getText(),
                    repoPublishInfoBO.getPublished().getText(), repoPublishInfoBO.getCommitId()));
            text.append("\n");
        });
        content.setText(text.toString());
        return content;
    }

}
