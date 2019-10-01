package com.sctt.cinema.api.business.entity.response;

import com.sctt.cinema.api.business.entity.jpa.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {

    public String email;
    public String fullName;
    public String phoneNumber;
    public long totalAccumulation;

    public void clone(User user){
        this.email = user.email;
        this.fullName = user.fullName;
        this.phoneNumber = user.phoneNumber;
        this.totalAccumulation = user.totalAccumulation;
    }
}
