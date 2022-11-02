package com.hackathon.software.client;

import com.hackathon.software.service.BO.MsgContent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "feiShuBotClient", url = "https://open.feishu.cn")
public interface FeiShuBotClient {

    @PostMapping("/open-apis/bot/v2/hook/<bot-id>")
    String send(MsgContent content);
}
