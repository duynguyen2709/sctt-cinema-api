package com.sctt.cinema.api.business.entity.request;

import com.sctt.cinema.api.business.entity.jpa.Seat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomSeatMappingDTO implements Serializable {

    public int roomID;
    public Seat[][] seats = null;

    public int numOfRow = 0;
    public int numOfColumn = 0;

    public RoomSeatMappingDTO(int roomID){
        this.roomID = roomID;
    }
}
