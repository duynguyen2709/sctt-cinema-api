package com.sctt.cinema.api.business.entity.jpa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public String getTimeFrom(){
        return new SimpleDateFormat("yyyy-MM-dd hh:mm").format(this.timeFrom);
    }

    public void setTimeFrom(String timestamp) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date             parsedDate = dateFormat.parse(timestamp);
        this.timeFrom  = new java.sql.Timestamp(parsedDate.getTime());
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
                timeFrom.getTime() > 0 &&
                (status == 0 || status == 1);
    }
}
