package com.ctrip.framework.apollo.common.controller;

import com.ctrip.framework.apollo.Apollo;
import com.ctrip.framework.apollo.common.utils.NetworkUtils;
import com.ctrip.framework.foundation.Foundation;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = "/apollo")
public class ApolloInfoController {

    private static final Logger logger = LoggerFactory.getLogger(ApolloInfoController.class);

    @RequestMapping("app")
    public String getApp() {
        return Foundation.app().toString();
    }

    @RequestMapping("net")
    public String getNet() {
        return Foundation.net().toString();
    }

    @RequestMapping("server")
    public String getServer() {
        return Foundation.server().toString();
    }

    @RequestMapping("version")
    public String getVersion() {
        return Apollo.VERSION;
    }

    @RequestMapping("hostAddress")
    public Map<String, Object> getHostAddress() {
        Map<String, Object> results = Maps.newHashMap();
        try {
            Set<NetworkInterface> physicalNICs = NetworkUtils.getNICs();
            if (Objects.nonNull(physicalNICs)) {
                List<String> hostAddress = physicalNICs.stream().flatMap(networkInterface -> Stream.of(networkInterface.getInetAddresses()))
                        .map(Enumeration::nextElement).map(inetAddress -> inetAddress.getHostAddress())
                        .collect(Collectors.toList());
                results.put("hostAddress", hostAddress);
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return results;
    }
}
