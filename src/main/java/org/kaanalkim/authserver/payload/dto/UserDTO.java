package org.kaanalkim.authserver.payload.dto;

import org.kaanalkim.authserver.mapper.AbstractDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class UserDTO extends AbstractDTO {
    private String name;
    private String surname;
    private String username;
    private String email;
    private String password;
    private RealmDTO realm;
}
