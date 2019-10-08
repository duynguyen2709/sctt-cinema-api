package com.sctt.cinema.api.business.entity.jpa;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sctt.cinema.api.util.DateTimeUtils;
import com.sctt.cinema.api.util.GsonUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

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
    public String screenshots;

    @Column
    public String description;

    @Column
    public Date dateFrom;

    @Column
    public int status;

    @Column
    public long baseTicketPrice;

    public List<String> getScreenshots(){
        try {
            ObjectMapper mapper  = new ObjectMapper();
            TypeReference<List<String>> typeRef = new TypeReference<List<String>>() {};
            return mapper.readValue(screenshots, typeRef);
        }catch (Exception e){
            return null;
        }
    }

    public void setScreenshots(List<String> screenshots){
        this.screenshots = (screenshots == null ? "[]" : GsonUtils.toJsonString(screenshots));
    }


    @Override
    public boolean isValid() {

        return !movieName.isEmpty() && !imageURL.isEmpty() && !trailerURL.isEmpty()
                && dateFrom != null && baseTicketPrice > 0
                && (status == 0 || status == 1 || status == 2);
    }
}
