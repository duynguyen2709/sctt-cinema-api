package com.sctt.cinema.api.business.entity.jpa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Entity
@Table(name="User")
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseJPAEntity {

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
    public Timestamp creationDate;

    @Override
    public boolean isValid() {
        return !email.isEmpty() &&
                !fullName.isEmpty() && !phoneNumber.isEmpty()
                && (role == 0 || role == 1 || role == 2);
    }
}
