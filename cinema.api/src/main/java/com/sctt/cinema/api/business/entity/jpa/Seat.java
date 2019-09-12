package com.sctt.cinema.api.business.entity.jpa;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name="Seat")
public class Seat implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Integer seatID;

    @Column
    public int roomID;

    @Column
    public String seatCode;

    @Column
    public int seatType;
}
