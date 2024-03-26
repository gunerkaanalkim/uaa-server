package org.kaanalkim.authserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.kaanalkim.authserver.model.base.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "Permission")
@Table
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Permission extends AbstractEntity {
    private String controller;
    private String title;
    private String description;
    private String url;
}