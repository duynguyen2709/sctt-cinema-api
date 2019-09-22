package com.sctt.cinema.api.business.entity.jpa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Data
@Entity
@Table(name="Seat")
@IdClass(Seat.SeatID.class)
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
    @AllArgsConstructor
    //@RequiredArgsConstructor
    @NoArgsConstructor(force = true)
    public static class SeatID implements Serializable{
        public int roomID;
        public String seatCode;


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Seat.SeatID)) return false;
            Seat.SeatID that = (Seat.SeatID) o;
            return roomID == that.roomID &&
                    seatCode == that.seatCode;
        }

        @Override
        public int hashCode() {
            return Objects.hash(roomID, seatCode);
        }
    }
}
