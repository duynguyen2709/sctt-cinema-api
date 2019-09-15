package com.sctt.cinema.api.business.entity.jpa;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Entity
@Table(name="User")
public class User implements Serializable {

    @Id
    public String email;

    @Column
    public String password;

    @Column
    public String fullName;

    @Column
    public int role;

    @Column
    public String phoneNumber;

    @Column
    public long totalAccumulation;

    @Column
    public int status;

    @Column
    public Timestamp creationDate;

}
