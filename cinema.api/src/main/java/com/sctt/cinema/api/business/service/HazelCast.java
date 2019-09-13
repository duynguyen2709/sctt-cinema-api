package com.sctt.cinema.api.business.service;


import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.core.TransactionalMap;
import com.hazelcast.transaction.TransactionContext;
import com.hazelcast.transaction.TransactionOptions;
import com.hazelcast.transaction.TransactionOptions.TransactionType;
import com.sctt.cinema.api.business.entity.config.HazelCastConfig;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HazelCast {
    private static final HazelCast INSTANCE = new HazelCast();
    private HazelcastInstance hazelCastInstance;

    private HazelCast() {
    }

    public static HazelCast getInstance() {
        return INSTANCE;
    }

    public HazelcastInstance getHazelCastInstance() {
        return this.hazelCastInstance;
    }

    public void initHazelCastConfig(HazelCastConfig conf) {
        Config hazelCastConf = new Config();
        hazelCastConf.getNetworkConfig().setPort(conf.networkPort).setPortAutoIncrement(conf.isPortAutoIncrement);
        JoinConfig join = hazelCastConf.getNetworkConfig().getJoin();
        join.getMulticastConfig().setEnabled(false);
        join.getTcpIpConfig().setEnabled(true).clear().setMembers(conf.tcpIPMembers);
        hazelCastConf.setInstanceName(conf.instanceName);
        this.hazelCastInstance = Hazelcast.newHazelcastInstance(hazelCastConf);
    }

    public <K, V> IMap<K, V> getMap(String key) {
        return this.hazelCastInstance.getMap(key);
    }

    public TransactionContext getTransactionContext() {
        TransactionOptions transOp = (new TransactionOptions()).setTimeout(3L, TimeUnit.SECONDS).setTransactionType(TransactionType.ONE_PHASE);
        return this.getTransactionContext(transOp);
    }

    public TransactionContext getTransactionContext(TransactionOptions transOp) {
        TransactionContext context = this.hazelCastInstance.newTransactionContext(transOp);
        return context;
    }

    public void beginTransaction(TransactionContext context) {
        context.beginTransaction();
    }

    public <K, V> void reloadMap(TransactionContext context, String key, Map<K, V> valMap) {
        TransactionalMap<K, V> transMap = context.getMap(key);
        transMap.keySet().forEach(transMap::remove);
        valMap.forEach(transMap::put);
    }

    public void commitTransaction(TransactionContext context) {
        context.commitTransaction();
    }

    public void rollbackTransaction(TransactionContext context) {
        context.rollbackTransaction();
    }
}

