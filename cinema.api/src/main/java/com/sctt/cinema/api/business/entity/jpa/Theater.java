package com.sctt.cinema.api.business.entity.jpa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name="Theater")
@AllArgsConstructor
@NoArgsConstructor
public class Theater extends BaseJPAEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Integer theaterID;

    @Column
    public String theaterName;

    @Column
    public String address;

    @Column
    public int provinceCode;

    @Override
    public boolean isValid() {
        return !theaterName.isEmpty() && !address.isEmpty() && provinceCode > 0;
    }
}
