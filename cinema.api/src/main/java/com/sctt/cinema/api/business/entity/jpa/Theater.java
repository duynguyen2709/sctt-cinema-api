package com.sctt.cinema.api.business.entity.jpa;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name="Theater")
public class Theater implements Serializable {

    @Id
    public String theaterID;

    @Column
    public String theaterName;

    @Column
    public String address;

    @Column
    public String phoneNumber;

    @Column
    public int provinceCode;
}
