package com.sctt.cinema.api.business.entity.jpa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Entity
@Table(name="Showtime")
@AllArgsConstructor
@NoArgsConstructor
public class Showtime extends BaseJPAEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Integer showtimeID;

    @Column
    public int movieID;

    @Column
    public int movieFormat;

    @Column
    public int theaterID;

    @Column
    public int roomID;

    @Column
    public Timestamp timeFrom;

    @Column(nullable = true)
    public Timestamp timeTo;

    @Column
    public int status;

    public long getTimeFrom(){
        return timeFrom.getTime();
    }

    public void setTimeFrom(long timestamp){
        this.timeFrom = new Timestamp(timestamp);
    }

    public long getTimeTo(){
        return timeTo.getTime();
    }

    public void setTimeTo(long timestamp){
        this.timeTo = new Timestamp(timestamp);
    }

    @Override
    public boolean isValid() {
        return movieID > 0 &&
                (movieFormat == 0 || movieFormat == 1) &&
                getTimeFrom() > 0 && getTimeTo() > 0 &&
                (status == 0 || status == 1);
    }
}
