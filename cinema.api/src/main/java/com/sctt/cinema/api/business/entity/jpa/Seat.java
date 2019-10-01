package com.sctt.cinema.api.business.entity.jpa;

import com.sctt.cinema.api.business.entity.response.SeatStatusDTO;
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
@IdClass(Seat.SeatKey.class)
@AllArgsConstructor
@NoArgsConstructor
public class Seat extends BaseJPAEntity {

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

    public SeatStatusDTO convert(){
        SeatStatusDTO dto = new SeatStatusDTO();
        dto.seatCode = this.seatCode;
        dto.seatType = this.seatType;
        dto.status = 0;

        return dto;
    }

    public String getKey(){
        return this.roomID + "_" + this.seatCode;
    }

    @Override
    public boolean isValid() {
        return roomID > 0 && !seatCode.isEmpty() &&
                (seatType == 1 || seatType == 2)
                && rowNo >= 0  && columnNo >= 0;
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
