package com.commercehub.jclouds.gridfs.blobstore;

import com.google.common.base.Supplier;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.gridfs.GridFS;
import org.jclouds.blobstore.BlobStoreContext;
import org.jclouds.blobstore.domain.Blob;
import org.jclouds.blobstore.domain.BlobMetadata;
import org.jclouds.blobstore.domain.PageSet;
import org.jclouds.blobstore.domain.StorageMetadata;
import org.jclouds.blobstore.internal.BaseBlobStore;
import org.jclouds.blobstore.options.CreateContainerOptions;
import org.jclouds.blobstore.options.GetOptions;
import org.jclouds.blobstore.options.ListContainerOptions;
import org.jclouds.blobstore.options.PutOptions;
import org.jclouds.blobstore.util.BlobUtils;
import org.jclouds.collect.Memoized;
import org.jclouds.domain.Location;
import org.jclouds.javax.annotation.Nullable;
import org.jclouds.providers.ProviderMetadata;

import javax.inject.Inject;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GridFSBlobStore extends BaseBlobStore {
    private final MongoClient mongo;

    @Inject
    protected GridFSBlobStore(ProviderMetadata providerMetadata, BlobStoreContext context, BlobUtils blobUtils, Supplier<Location> defaultLocation, @Memoized Supplier<Set<? extends Location>> locations) throws UnknownHostException {
        super(context, blobUtils, defaultLocation, locations);
        List<ServerAddress> addresses = Util.parseServerAddresses(providerMetadata.getEndpoint());
        List<MongoCredential> credentials = new ArrayList<>(); // TODO support credentials
        MongoClientOptions options = MongoClientOptions.builder().build(); // TODO support options configuration
        if (addresses.size() > 1) {
            this.mongo = new MongoClient(addresses, credentials, options);
        } else {
            // If only one address, assume we want single-node mode.
            // You should always use multiple seeds with a replica set.
            this.mongo = new MongoClient(addresses.get(0), credentials, options);
        }
    }

    private GridFS getGridFS(String container) {
        String[] parts = container.split("/");
        String dbName = parts[0];
        String bucket = parts.length > 1 ? parts[1] : GridFS.DEFAULT_BUCKET;
        DB db = mongo.getDB(dbName);
        // TODO: cache instances
        return new GridFS(db, bucket);
    }

    @Override
    protected boolean deleteAndVerifyContainerGone(String container) {
        return false;  // TODO: implement
    }

    @Override
    public PageSet<? extends StorageMetadata> list() {
        return null;  // TODO: implement
    }

    @Override
    public boolean containerExists(String container) {
        return false;  // TODO: implement
    }

    @Override
    public boolean createContainerInLocation(@Nullable Location location, String container) {
        return false;  // TODO: implement
    }

    @Override
    public boolean createContainerInLocation(@Nullable Location location, String container, CreateContainerOptions options) {
        return false;  // TODO: implement
    }

    @Override
    public PageSet<? extends StorageMetadata> list(String container, ListContainerOptions options) {
        return null;  // TODO: implement
    }

    @Override
    public boolean blobExists(String container, String name) {
        return false;  // TODO: implement
    }

    @Override
    public String putBlob(String container, Blob blob) {
        return null;  // TODO: implement
    }

    @Override
    public String putBlob(String container, Blob blob, PutOptions options) {
        return null;  // TODO: implement
    }

    @Override
    public BlobMetadata blobMetadata(String container, String name) {
        return null;  // TODO: implement
    }

    @Override
    public Blob getBlob(String container, String name, GetOptions options) {
        return null;  // TODO: implement
    }

    @Override
    public void removeBlob(String container, String name) {
        // TODO: implement
    }
}
