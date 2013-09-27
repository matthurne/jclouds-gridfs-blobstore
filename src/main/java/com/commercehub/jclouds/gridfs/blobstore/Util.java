package com.commercehub.jclouds.gridfs.blobstore;

import com.mongodb.ServerAddress;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

class Util {
    static ServerAddress parseServerAddress(String addressStr) throws UnknownHostException {
        String[] parts = addressStr.split(":");
        String host = !parts[0].isEmpty() ? parts[0] : ServerAddress.defaultHost();
        int port = parts.length > 1 ? Integer.parseInt(parts[1]) : ServerAddress.defaultPort();
        return new ServerAddress(host, port);
    }

    static List<ServerAddress> parseServerAddresses(String addressesStr) throws UnknownHostException {
        List<ServerAddress> addresses = new ArrayList<>();
        for (String address : addressesStr.split("[;, ]")) {
            addresses.add(parseServerAddress(address));
        }
        return addresses;
    }
}
