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
    public Integer seatID;

    @Id
    public int showtimeID;

    @Data
    @AllArgsConstructor
    public static class BookedSeatKey implements Serializable{
        public int seatID;
        public int showtimeID;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof BookedSeatKey)) return false;
            BookedSeatKey that = (BookedSeatKey) o;
            return seatID == that.seatID &&
                    showtimeID == that.showtimeID;
        }

        @Override
        public int hashCode() {
            return Objects.hash(seatID, showtimeID);
        }
    }

}
