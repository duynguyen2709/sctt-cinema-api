package com.sctt.cinema.api.business.entity.DTO;

import com.sctt.cinema.api.business.entity.jpa.Showtime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowtimeDTO implements Serializable {

    public String text;

    public List<ShowtimeByFormatDTO> showtimes = new ArrayList<>();
}
