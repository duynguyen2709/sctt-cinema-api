package com.sctt.cinema.api.business.service.jpa;

import com.sctt.cinema.api.business.entity.response.SeatStatusDTO;
import com.sctt.cinema.api.business.entity.jpa.Seat;
import com.sctt.cinema.api.business.entity.request.RoomSeatMappingDTO;
import com.sctt.cinema.api.common.enums.CacheKeyEnum;
import com.sctt.cinema.api.util.GsonUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class RoomSeatService extends BaseJPAService<RoomSeatMappingDTO, Integer> {

    @Autowired
    private SeatService seatService;

    @Override
    @EventListener(ApplicationReadyEvent.class)
    @Order(Ordered.LOWEST_PRECEDENCE)
    protected void init() {
        loadCacheMap(CacheKeyEnum.ROOM_SEAT);
    }

    @Override
    public List<RoomSeatMappingDTO> findAll() {
        return new ArrayList<>(cacheMap.values());
    }

    @Override
    public RoomSeatMappingDTO create(RoomSeatMappingDTO dto) {
        try {
            for (int i = 0; i < dto.seats.length; i++) {
                for (int j = 0; j < dto.seats[i].length; j++) {
                    if (dto.seats[i][j] != null) {
                        Seat seat = new Seat();
                        seat.roomID = dto.roomID;
                        seat.seatCode = dto.seats[i][j].seatCode;
                        seat.seatType = dto.seats[i][j].seatType;
                        seat.rowNo = i;
                        seat.columnNo = j;

                        if (seat.isValid()) {
                            seatService.create(seat);
                        } else {
                            throw new Exception(String.format("seat %s not valid", GsonUtils.toJsonString(seat)));
                        }
                    }
                }
            }
            return dto;
        } catch (Exception e) {
            log.error("[create] ex {}", e.getMessage());
            return null;
        } finally {
            loadCacheMap(CacheKeyEnum.ROOM_SEAT);
        }
    }

    @Override public RoomSeatMappingDTO update(RoomSeatMappingDTO dto) {
        try {
            seatService.findAll()
                    .stream()
                    .filter(s -> s.roomID == dto.roomID)
                    .collect(Collectors.toList())
                    .forEach(c -> seatService.delete(c.getKey()));

            return this.create(dto);
        } catch (Exception e) {
            log.error("[update] ex {}", e.getMessage());
            return null;
        }
    }

    @Override
    public RoomSeatMappingDTO findById(Integer key) {
        return cacheMap.get(key);
    }

    @Override
    public void delete(Integer key) {

    }

    public SeatStatusDTO[][] convert(Seat[][] seats) {
        if (seats == null) {
            log.error("[convert] ex {null}");
            return null;
        }

        SeatStatusDTO[][] res = new SeatStatusDTO[seats.length][seats[0].length];

        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                if (seats[i][j] == null) {
                    res[i][j] = null;
                } else {
                    res[i][j] = seats[i][j].convert();
                }
            }
        }

        return res;
    }
}
