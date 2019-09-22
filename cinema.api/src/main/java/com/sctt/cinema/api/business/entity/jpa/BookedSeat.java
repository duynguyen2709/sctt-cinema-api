package com.sctt.cinema.api.business.entity.jpa;

import lombok.AllArgsConstructor;
import lombok.Data;

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
    public int showtimeID;

    @Id
    public String seatCode;

    @Data
    @AllArgsConstructor
    public static class BookedSeatKey implements Serializable{
        public int roomID;
        public int showtimeID;
        public String seatCode;

        public BookedSeatKey(String stringID){
            this.roomID = Integer.parseInt(stringID.substring(0, stringID.indexOf("_")));
            this.showtimeID = Integer.parseInt(stringID.substring(stringID.indexOf("_") + 1, stringID.lastIndexOf("_")));
            this.seatCode = stringID.substring(stringID.lastIndexOf("_") + 1);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof BookedSeatKey)) return false;
            BookedSeatKey that = (BookedSeatKey) o;
            return roomID == that.roomID &&
                    showtimeID == that.showtimeID &&
                        seatCode == that.seatCode;
        }

        @Override
        public int hashCode() {
            return Objects.hash(roomID, showtimeID, seatCode);
        }
    }

}
