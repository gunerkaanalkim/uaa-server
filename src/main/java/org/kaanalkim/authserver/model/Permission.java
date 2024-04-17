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
@Entity(name = "Permission")
@Table
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Permission extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "realm_id", referencedColumnName = "id")
    private Realm realm;

    private String groupName;
    private String controller;
    private String title;
    private String description;
    private String url;
}