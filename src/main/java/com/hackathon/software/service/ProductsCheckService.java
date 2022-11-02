package com.hackathon.software.service;

import com.hackathon.software.service.BO.CheckPublishResultBO;

public interface ProductsCheckService {
    CheckPublishResultBO checkProduct(String repoName, String version);
}
