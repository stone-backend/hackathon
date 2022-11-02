package com.hackathon.software.controller;

import com.hackathon.software.controller.request.VersionCheckRequest;
import com.hackathon.software.service.impl.FeiShuBotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("chat")
@RequiredArgsConstructor
public class FeiShuBotController {

    private final FeiShuBotService feiShuBotService;

    @PostMapping("/status")
    public void sendStatusToGroup(@RequestBody List<VersionCheckRequest> versionCheckList) {
        System.out.println(LocalDateTime.now());
        feiShuBotService.sendPublishInfo2FeiShu(versionCheckList);
    }
}
