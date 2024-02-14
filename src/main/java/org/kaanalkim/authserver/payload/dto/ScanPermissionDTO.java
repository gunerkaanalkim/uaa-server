package org.kaanalkim.authserver.payload.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class ScanPermissionDTO {
    private String controllerName;
    private String controllerAlias;
    private Map<String, String> endpoints = new HashMap<>();
}
