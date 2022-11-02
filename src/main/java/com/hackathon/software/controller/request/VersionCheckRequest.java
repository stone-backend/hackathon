package com.hackathon.software.controller.request;

import lombok.Data;

@Data
public class VersionCheckRequest {
    private String repo;

    private String version;
}
