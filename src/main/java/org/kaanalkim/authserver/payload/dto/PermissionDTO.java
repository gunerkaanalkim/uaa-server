package org.kaanalkim.authserver.payload.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.kaanalkim.authserver.mapper.AbstractDTO;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class PermissionDTO extends AbstractDTO {
    private Long id;
    private String groupName;
    private String controller;
    private String title;
    private String description;
    private String url;
}
