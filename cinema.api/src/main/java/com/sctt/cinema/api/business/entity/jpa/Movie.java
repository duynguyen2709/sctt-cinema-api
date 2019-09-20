package com.sctt.cinema.api.business.entity.jpa;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Data
@Entity
@Table(name="Movie")
public class Movie implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Integer movieID;

    @Column
    public String movieName;

    @Column
    public int timeInMinute;

    @Column
    public String category;

    @Column
    public String imageURL;

    @Column
    public String trailerURL;

    @Column
    public String description;

    @Column
    public Date dateFrom;

    @Column
    public Date dateTo;

    @Column
    public int status;

    @Column
    public long baseTicketPrice;

}
