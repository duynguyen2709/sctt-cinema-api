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
public class Room implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Integer roomID;

    @Column
    public int roomType;

    @Column
    public int roomNumber;

    @Column
    public int theaterID;
}
