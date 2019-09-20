package com.sctt.cinema.api.business.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomStatusDTO implements Serializable {

    public int showtimeID;

    public int roomID;

    public List<List<SeatStatusDTO>> seats = new ArrayList<>();
}
