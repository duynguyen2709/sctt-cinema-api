package com.sctt.cinema.api.business.entity.jpa;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sctt.cinema.api.util.GsonUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="TicketLog")
public class TicketLog extends BaseJPAEntity {

    public static long serialVersionUID = 6235891346674782026L;

    @Id
    public Long ticketID;

    @Column
    public int showtimeID;

    @Column
    public String email;

    @Column
    public String seatCodes;

    @Column
    public long totalPrice;

    @Column(nullable = true)
    public Timestamp orderTime;

    @Column
    public String extraInfo;

    @Column
    public int status;

    public List<String> getSeatCodes(){
        try {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<String>> typeRef = new TypeReference<List<String>>() {};
            return mapper.readValue(seatCodes, typeRef);
        }catch (Exception e){
            return null;
        }
    }

    public void setSeatCodes(List<Object> seatCodes){
        if (seatCodes == null){
            this.seatCodes = "[]";
        } else {
            List<String> list = seatCodes.stream().map(String::valueOf).collect(Collectors.toList());
            this.seatCodes = GsonUtils.toJsonString(list);
        }
    }

    public long getOrderTime(){
        return orderTime.getTime();
    }

    public void setOrderTime(long timestamp){
        this.orderTime = new Timestamp(timestamp);
    }

    @Override
    public boolean isValid() {
        return true;
    }
}
