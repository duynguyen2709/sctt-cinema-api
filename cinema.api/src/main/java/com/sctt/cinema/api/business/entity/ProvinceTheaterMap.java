package com.sctt.cinema.api.business.entity;

import com.sctt.cinema.api.business.entity.jpa.Theater;
import com.sctt.cinema.api.business.repository.TheaterRepository;
import com.sctt.cinema.api.util.GsonUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Configuration
public class ProvinceTheaterMap {

    // <provinceCode, List<Theater>>
    public static Map<Integer, List<Theater>> provinceTheaterMap = new HashMap<>();

    @Autowired
    private void initProvinceTheaterMap(TheaterRepository repo){
        List<Theater> list = repo.findAll();
        for (Theater t : list){
            int province_code = t.provinceCode;
            if (!provinceTheaterMap.containsKey(province_code))
                provinceTheaterMap.put(province_code,new ArrayList<>());

            provinceTheaterMap.get(province_code).add(t);
        }

        log.info(GsonUtils.toJsonString(provinceTheaterMap));
    }
}
