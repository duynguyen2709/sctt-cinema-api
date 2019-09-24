package com.sctt.cinema.api.business.config;

import com.sctt.cinema.api.business.entity.config.HazelCastConfig;
import com.sctt.cinema.api.util.HazelCastUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@Order(1)
public class HazelCastInit {

    @Autowired
    private HazelCastConfig hazelCastConfig;

    @Bean
    public void init(){
        if (hazelCastConfig.useHazelCast) {
            HazelCastUtils.getInstance().initHazelCastConfig(hazelCastConfig);
        }
    }
}