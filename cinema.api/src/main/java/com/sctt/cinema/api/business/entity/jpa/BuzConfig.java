package com.sctt.cinema.api.business.entity.jpa;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Data
@Entity
@Table(name="BuzConfig")
public class BuzConfig implements Serializable {

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
}
