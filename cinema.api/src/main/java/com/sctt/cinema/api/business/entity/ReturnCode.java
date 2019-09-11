package com.sctt.cinema.api.business.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ReturnCode")
@Data
public class ReturnCode {

    @Id
    public int returnCode;

    @Column
    public String returnMessage;
}
