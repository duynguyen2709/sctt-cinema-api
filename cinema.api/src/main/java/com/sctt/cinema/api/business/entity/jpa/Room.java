package com.sctt.cinema.api.business.entity.jpa;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name="Room")
public class Room implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Integer roomID;

    @Column
    public int roomType;

    @Column
    public String theaterID;

    public int totalNormalSeat = 0;
    public int totalVIPSeat = 0;

}
