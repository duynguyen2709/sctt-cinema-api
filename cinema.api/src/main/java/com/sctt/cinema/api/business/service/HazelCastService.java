package com.sctt.cinema.api.business.service;

import com.sctt.cinema.api.common.HazelCast;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class HazelCastService {

    @Value("${hazelcast.useHazelCast}")
    private boolean useHazelcast;

    private static HazelCast hazelCast = HazelCast.getInstance();

    public void initCacheMaps(){

    }

}
