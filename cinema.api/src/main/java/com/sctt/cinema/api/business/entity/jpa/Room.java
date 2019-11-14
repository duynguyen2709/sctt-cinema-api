package com.sctt.cinema.api.business.entity.jpa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name="Room")
@AllArgsConstructor
@NoArgsConstructor
public class Room extends BaseJPAEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Integer roomID;

    @Column
    public int roomNumber;

    @Column
    public int theaterID;

    @Transient
    public String theaterName;

    @Override
    public boolean isValid() {
        return roomNumber > 0 & theaterID > 0;
    }
}
