package org.kaanalkim.authserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.kaanalkim.common.model.base.AbstractEntity;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@SuperBuilder
public class User extends AbstractEntity {
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "realm_id", referencedColumnName = "id")
    private Realm realm;

    private String name;
    private String surname;
    private String username;
    private String email;

    @JsonIgnore
    private String password;
}
