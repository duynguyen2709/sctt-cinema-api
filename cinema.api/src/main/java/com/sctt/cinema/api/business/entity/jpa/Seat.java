package com.sctt.cinema.api.business.entity.jpa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name="Seat")
@IdClass(Seat.SeatKey.class)
public class Seat implements Serializable {

    @Id
    public int roomID;

    @Id
    public String seatCode;

    @Column
    public int seatType;

    @Column
    public int row;

    @Column
    public int column;

    @Data
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class SeatKey implements Serializable{
        public int roomID;
        public String seatCode;
    }
}
