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

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "RoleUser")
@Table
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class RoleUser extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "roleId", referencedColumnName = "id")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;
}