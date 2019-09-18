package com.sctt.cinema.api.business.entity.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "hazelcast")
@Order((Ordered.HIGHEST_PRECEDENCE))
public class HazelCastConfig {
    public int          networkPort;
    public boolean      isPortAutoIncrement;
    public List<String> tcpIPMembers;
    public String       instanceName;
    public boolean      useHazelCast;
}