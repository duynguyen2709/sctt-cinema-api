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
public class ShowtimeByFormatDTO implements Serializable {
    // 2D, 3D...
    public String format;

    // Detail each showtime
    public List<ShowtimeDetailDTO> details = new ArrayList<>();

    public ShowtimeByFormatDTO(String format, Showtime original){
        int typeOfFormat;
        if(format.equals("2D"))
            typeOfFormat = 0;
        else
            typeOfFormat = 1;

        if(original.movieFormat == typeOfFormat){
            details.add(new ShowtimeDetailDTO(original));
        }
    }
}
