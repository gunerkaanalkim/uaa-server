package org.kaanalkim.authserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.kaanalkim.common.model.base.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "Realm")
@Table
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Realm extends AbstractEntity {
    private String name;
    private String code;
    private String description;
}