package org.kaanalkim.authserver.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.kaanalkim.common.model.base.AbstractEntity;
import org.springframework.security.core.GrantedAuthority;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "Role")
@Table
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Role extends AbstractEntity implements GrantedAuthority {
    @ManyToOne
    @JoinColumn(name = "realm_id", referencedColumnName = "id")
    private Realm realm;

    private String name;
    private String code;

    @Override
    public String getAuthority() {
        return name;
    }
}