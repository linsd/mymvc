package com.tasxxz.mymvc.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by linshudeng on 2016/11/15.
 */
@Component
public class ConfigService {

    @Value("${host.name:123}")
    private String hostName;

    @Value("${host.port}")
    private String hostPort;

    @Value("${host.port2:222}")
    private String hostPort2;

    public String getHostName() {
        return hostName;
    }

    public String getHostPort() {
        return hostPort;
    }

    public String getHostPort2() {
        return hostPort2;
    }
}
