package com.commercehub.jclouds.gridfs.blobstore;

import com.google.common.base.Supplier;
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

import javax.inject.Inject;
import java.util.Set;

public class GridFSBlobStore extends BaseBlobStore {
    @Inject
    protected GridFSBlobStore(BlobStoreContext context, BlobUtils blobUtils, Supplier<Location> defaultLocation, @Memoized Supplier<Set<? extends Location>> locations) {
        super(context, blobUtils, defaultLocation, locations);
    }

    // TODO: implement
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
