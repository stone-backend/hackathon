package com.hackathon.software.service.impl;

import com.aliyun.cr20181201.Client;
import com.aliyun.cr20181201.models.ListRepoTagRequest;
import com.aliyun.cr20181201.models.ListRepoTagResponse;
import com.aliyun.cr20181201.models.ListRepoTagResponseBody;
import com.hackathon.software.common.enums.PublishStatusEnum;
import com.hackathon.software.service.BO.CheckPublishResultBO;
import com.hackathon.software.service.ProductsCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class ProductsCheckServiceImpl implements ProductsCheckService {

    private final Client aliACRClient;

    @Value("${ali-yun.acr-instance-id}")
    private String INSTANCE_ID;

    // todo: config in yaml
    public static final Map<String, String> REPO_NAME2ID = new HashMap<>();

    static {
    }

    @Override
    public CheckPublishResultBO checkProduct(String repoName, String version) {
        String repoId = REPO_NAME2ID.get(repoName);
        if (!StringUtils.hasText(repoId)) {
            throw new RuntimeException("invalid repoName");
        }
        ListRepoTagRequest listRepoTagRequest = new ListRepoTagRequest()
                .setInstanceId(INSTANCE_ID)
                .setRepoId(repoId);
        ListRepoTagResponse response;
        try {
            response = aliACRClient.listRepoTag(listRepoTagRequest);
        } catch (Exception error) {
            throw new RuntimeException("error occurs when request repo tags from ali-yun service");
        }
        if (Objects.isNull(response) || Objects.isNull(response.getBody())) {
            throw new RuntimeException("response from ali-yun is invalid");

        }
        ListRepoTagResponseBody listRepoTagResponseBody = response.getBody();
        if (CollectionUtils.isEmpty(listRepoTagResponseBody.getImages())) {
            CheckPublishResultBO CheckPublishResultBO = new CheckPublishResultBO();
            CheckPublishResultBO.setPublishStatusEnum(PublishStatusEnum.NOT_PUBLISH);
            CheckPublishResultBO.setMsg("制品仓库中未找到制品");
            return CheckPublishResultBO;
        }
        if (listRepoTagResponseBody.getImages().stream().anyMatch(image -> version.equals(image.getTag()))) {
            CheckPublishResultBO CheckPublishResultBO = new CheckPublishResultBO();
            CheckPublishResultBO.setPublishStatusEnum(PublishStatusEnum.PUBLISHED);
            CheckPublishResultBO.setMsg("已发布");
            return CheckPublishResultBO;
        }
        CheckPublishResultBO CheckPublishResultBO = new CheckPublishResultBO();
        CheckPublishResultBO.setPublishStatusEnum(PublishStatusEnum.NOT_PUBLISH);
        CheckPublishResultBO.setMsg("该版本还未发布到制品仓库中");
        return CheckPublishResultBO;
    }
}
