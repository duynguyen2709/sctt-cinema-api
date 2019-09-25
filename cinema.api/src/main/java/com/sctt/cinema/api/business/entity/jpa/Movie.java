package com.sctt.cinema.api.business.entity.jpa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Data
@Entity
@Table(name="Movie")
@AllArgsConstructor
@NoArgsConstructor
public class Movie extends BaseJPAEntity {

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

    @Override
    public boolean isValid() {
        return !movieName.isEmpty() && !imageURL.isEmpty() && !trailerURL.isEmpty()
                && dateFrom != null && dateTo != null && baseTicketPrice > 0
                && (status == 0 || status == 1 || status == 2);
    }
}
