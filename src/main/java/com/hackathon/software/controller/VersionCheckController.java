package com.hackathon.software.controller;

import com.hackathon.software.service.BO.CheckPublishResultBO;
import com.hackathon.software.service.ProductsCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Deprecated
@RequiredArgsConstructor
public class VersionCheckController {
    private final ProductsCheckService productsCheckService;

    @GetMapping("/check")
    public CheckPublishResultBO checkPublish(@RequestParam String serviceName, @RequestParam String version) {
        if (StringUtils.hasText(serviceName) && StringUtils.hasText(version)) {
            return productsCheckService.checkProduct(serviceName, version);
        }
        throw new RuntimeException("invalid param");
    }
}
