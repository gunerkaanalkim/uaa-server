package org.kaanalkim.authserver.payload.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.kaanalkim.authserver.mapper.AbstractDTO;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class RoleDTO extends AbstractDTO {
    protected Long id;
    private String name;
    private String code;
}
