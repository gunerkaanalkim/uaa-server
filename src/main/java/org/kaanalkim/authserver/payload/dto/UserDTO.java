package org.kaanalkim.authserver.payload.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.kaanalkim.authserver.payload.mapper.AbstractDTO;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class UserDTO extends AbstractDTO {
    protected Long id;
    private String name;
    private String surname;
    private String username;
    private String email;
    private String password;
}
