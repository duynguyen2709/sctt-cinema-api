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
public class ShowtimeByFormatDTO implements Serializable {
    // 2D, 3D...
    public String format;

    // Detail each showtime
    public List<ShowtimeDetailDTO> details = new ArrayList<>();
}
