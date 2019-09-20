package com.sctt.cinema.api.business.entity.jpa;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name="Showtime")
public class Showtime implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Integer showtimeID;

    @Column
    public int roomID;

    @Column
    public int movieID;

    @Column
    public int movieFormat;

    @Column
    public long timeFrom;

    @Column
    public long timeTo;

    @Column
    public int status;
}
