package com.sctt.cinema.api.business.entity.jpa;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name="Theater")
public class Theater implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Integer theaterID;

    @Column
    public String theaterName;

    @Column
    public String address;

    @Column
    public int provinceCode;
}
