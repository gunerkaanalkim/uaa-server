package org.kaanalkim.authserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.kaanalkim.common.model.base.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "Role")
@Table
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Role extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "realm_id", referencedColumnName = "id")
    private Realm realm;

    private String name;
    private String code;
}