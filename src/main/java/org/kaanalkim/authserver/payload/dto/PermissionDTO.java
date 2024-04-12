package org.kaanalkim.authserver.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.kaanalkim.authserver.payload.mapper.AbstractDTO;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PermissionDTO extends AbstractDTO {
    private Long id;
    private String groupName;
    private String controller;
    private String title;
    private String description;
    private String url;
}
