package com.commercehub.jclouds.gridfs.blobstore;

import com.mongodb.ServerAddress;
import com.mongodb.gridfs.GridFS;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import static com.commercehub.jclouds.gridfs.blobstore.Constants.GRIDFS_URI_SCHEME;

class Util {
    static ServerAddress parseServerAddress(String addressStr) throws UnknownHostException {
        String[] parts = addressStr.split(":");
        String host = !parts[0].isEmpty() ? parts[0] : ServerAddress.defaultHost();
        int port = parts.length > 1 ? Integer.parseInt(parts[1]) : ServerAddress.defaultPort();
        return new ServerAddress(host, port);
    }

    static List<ServerAddress> parseServerAddresses(String addressesUri) throws UnknownHostException {
        String addressesStr = removePrefixIfPresent(removePrefixIfPresent(addressesUri, GRIDFS_URI_SCHEME + ":"), "//");
        List<ServerAddress> addresses = new ArrayList<>();
        for (String address : addressesStr.split("[;,]")) {
            addresses.add(parseServerAddress(address));
        }
        return addresses;
    }

    static GridFSIdentifier parseGridFSIdentifier(String container) {
        String[] parts = container.split("/");
        String dbName = parts[0];
        String bucket = parts.length > 1 ? parts[1] : GridFS.DEFAULT_BUCKET;
        return new GridFSIdentifier(dbName, bucket);
    }

    private static String removePrefixIfPresent(String str, String prefix) {
        return str.startsWith(prefix) ? str.substring(prefix.length()) : str;
    }
}
