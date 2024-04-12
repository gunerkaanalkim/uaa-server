package org.kaanalkim.authserver.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.kaanalkim.authserver.model.Role;
import org.kaanalkim.authserver.model.User;
import org.kaanalkim.authserver.payload.mapper.AbstractDTO;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class RoleUserDTO extends AbstractDTO {
    protected Long id;
    private User user;
    private Role role;
}
