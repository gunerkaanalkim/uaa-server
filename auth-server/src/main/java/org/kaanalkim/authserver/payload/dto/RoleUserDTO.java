package org.kaanalkim.authserver.payload.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.kaanalkim.authserver.model.Role;
import org.kaanalkim.authserver.model.User;
import org.kaanalkim.authserver.payload.mapper.AbstractDTO;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class RoleUserDTO extends AbstractDTO {
    protected Long id;
    private User user;
    private Role role;
}
