package com.sctt.cinema.api.business.entity.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class OrderDTO implements Serializable {

    public int showtimeID;
    public int roomID;
    public List<String> seatCodes;
    public String email;
}
