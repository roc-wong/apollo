package com.ctrip.framework.apollo.common.utils;

import org.junit.Test;

import java.net.NetworkInterface;
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

            List<String> hostAddress = physicalNICs.stream().flatMap(networkInterface -> Stream.of(networkInterface.getInetAddresses()))
                    .map(Enumeration::nextElement).map(netAddress -> netAddress.getHostAddress())
                    .collect(Collectors.toList());
            System.out.println(hostAddress);
        }

    }
}