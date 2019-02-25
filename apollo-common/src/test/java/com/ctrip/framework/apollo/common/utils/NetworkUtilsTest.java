package com.ctrip.framework.apollo.common.utils;

import org.assertj.core.util.Sets;
import org.junit.Test;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author roc
 * @date 2019/2/25 14:49
 */
public class NetworkUtilsTest {

    @Test
    public void getNICs() {

        Set<NetworkInterface> physicalNICs = NetworkUtils.getPhysicalNICs();
        if (Objects.nonNull(physicalNICs)) {
                List<Stream<String>> collect = physicalNICs.stream()
                        .map(networkInterface -> {
                            return Stream.of(networkInterface.getInetAddresses()).map(Enumeration::nextElement).map(inetAddress -> inetAddress.getHostAddress());
                            /*Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                            while (inetAddresses.hasMoreElements()){
                                InetAddress inetAddress = inetAddresses.nextElement();
                                if(Objects.nonNull(inetAddress)){

                                }
                            }*/
                        }).collect(Collectors.toList());

            List<String> hostAddress = physicalNICs.stream()
                    .map(networkInterface -> Collections.list(networkInterface.getInetAddresses()))
                    .flatMap(inetAddresses -> inetAddresses.stream())
                    .map(netAddress -> netAddress.getHostAddress())
                    .collect(Collectors.toList());
            System.out.println(hostAddress);
        }
    }

    @Test
    public void testJDK8(){

        Set<RNetworkInterface> networkInterfaces = Sets.newHashSet();
        RNetworkInterface rNetworkInterface1 = new RNetworkInterface();
        rNetworkInterface1.setAddrs(new RInetAddress[]{new RInetAddress("127.0.0.1")});

        RNetworkInterface rNetworkInterface2 = new RNetworkInterface();
        rNetworkInterface2.setAddrs(new RInetAddress[]{new RInetAddress("127.0.0.2"), new RInetAddress("127.0.0.3")});

        RNetworkInterface rNetworkInterface3 = new RNetworkInterface();
        rNetworkInterface3.setAddrs(new RInetAddress[]{new RInetAddress("127.0.0.4"), new RInetAddress("127.0.0.5")});

        networkInterfaces.add(rNetworkInterface1);
        networkInterfaces.add(rNetworkInterface2);
        networkInterfaces.add(rNetworkInterface3);

        List<String> hostAddress = networkInterfaces.stream()
                .map(networkInterface -> Collections.list(networkInterface.getInetAddresses()))
                .flatMap(inetAddresses -> inetAddresses.stream())
                .map(netAddress -> netAddress.getHostAddress())
                .collect(Collectors.toList());
        System.out.println(hostAddress);
    }

}