package com.sctt.cinema.api.business.entity.jpa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

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
    public int rowNo;

    @Column
    public int columnNo;

    public String getKey(){
        return this.roomID + "_" + this.seatCode;
    }

    @Data
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class SeatKey implements Serializable{

        public int roomID;
        public String seatCode;

        public SeatKey(String key){
            this.roomID = Integer.parseInt(key.split("_")[0]);
            this.seatCode = key.split("_")[1];
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            SeatKey that = (SeatKey) o;
            return roomID == that.roomID && seatCode.equalsIgnoreCase(that.seatCode);
        }

        @Override
        public int hashCode() {
            return Objects.hash(roomID, seatCode);
        }


    }
}
