package com.sctt.cinema.api.business.entity.jpa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Data
@Entity
@Table(name="BookedSeat")
@IdClass(BookedSeat.BookedSeatKey.class)
public class BookedSeat implements Serializable {

    @Id
    public int roomID;

    @Id
    public String seatCode;

    @Id
    public int showtimeID;

    public String getKey(){
        return this.roomID + "_" + this.seatCode + "_" + this.showtimeID;
    }

    @Data
    @AllArgsConstructor
    @RequiredArgsConstructor
    public static class BookedSeatKey implements Serializable{
        public int roomID;
        public String seatCode;
        public int showtimeID;

        public BookedSeatKey(String key){
            this.roomID = Integer.parseInt(key.split("_")[0]);
            this.seatCode = key.split("_")[1];
            this.showtimeID = Integer.parseInt(key.split("_")[2]);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof BookedSeatKey)) return false;
            BookedSeatKey that = (BookedSeatKey) o;
            return roomID == that.roomID &&
                    seatCode.equalsIgnoreCase(that.seatCode) &&
                    showtimeID == that.showtimeID;
        }

        @Override
        public int hashCode() {
            return Objects.hash(roomID, seatCode, showtimeID);
        }
    }

}
