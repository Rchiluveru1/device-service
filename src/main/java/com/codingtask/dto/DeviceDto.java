package com.codingtask.dto;

import lombok.Value;

@Value
public class DeviceDto {
    private String id;
    private String osName;
    private String osVersion;
    private String browserName;
    private String browserVersion;
    private int hitCount;
}
