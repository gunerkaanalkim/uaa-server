package org.kaanalkim.authserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.kaanalkim.authserver.model.base.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "RolePermission")
@Table
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class RolePermission extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "roleId", referencedColumnName = "id")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "permissionId", referencedColumnName = "id")
    private Permission permission;
}