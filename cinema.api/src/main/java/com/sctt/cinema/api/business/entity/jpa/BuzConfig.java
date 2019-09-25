package com.sctt.cinema.api.business.entity.jpa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Data
@Entity
@Table(name="BuzConfig")
@AllArgsConstructor
@NoArgsConstructor
public class BuzConfig extends BaseJPAEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Integer buzID;

    @Column
    public String section;

    @Column
    public String buzKey;

    @Column
    public String buzValue;

    @Column
    public String description;

    @Override public boolean isValid() {
        return !section.isEmpty() && !buzKey.isEmpty() && !buzValue.isEmpty();
    }
}
