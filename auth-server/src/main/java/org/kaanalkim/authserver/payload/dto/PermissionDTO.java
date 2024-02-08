package org.kaanalkim.authserver.payload.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.kaanalkim.authserver.payload.mapper.AbstractDTO;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class PermissionDTO extends AbstractDTO {
    protected Long id;
    private String controller;
    private String controllerAlias;
    private String permissionName;
    private String description;
    private Map<String, String> endpoints;
}
