package com.commercehub.jclouds.gridfs.blobstore;

import com.google.inject.Module;
import com.mongodb.Mongo;
import org.jclouds.apis.ApiMetadata;
import org.jclouds.apis.internal.BaseApiMetadata;
import org.jclouds.blobstore.BlobStoreContext;

import java.net.URI;
import java.util.Collections;

import static org.jclouds.reflect.Reflection2.typeToken;

public class GridFSApiMetadata extends BaseApiMetadata {
    public GridFSApiMetadata() {
        this(new Builder());
    }

    protected GridFSApiMetadata(Builder builder) {
        super(builder);
    }

    @Override
    public ApiMetadata.Builder<?> toBuilder() {
        return new Builder().fromApiMetadata(this);
    }

    public static class Builder extends BaseApiMetadata.Builder<Builder> {
        protected Builder() {
            id("gridfs")
                .name("MongoDB GridFS")
                .identityName("userName")
                .credentialName("password")
                .defaultIdentity("")
                .defaultCredential("")
                .defaultEndpoint("localhost:27017")
                .documentation(URI.create("http://docs.mongodb.org/manual/core/gridfs/"))
                .version(String.format("%d.%d", Mongo.getMajorVersion(), Mongo.getMinorVersion()))
                .defaultProperties(GridFSApiMetadata.defaultProperties())
                .view(typeToken(BlobStoreContext.class))
                .defaultModules(Collections.<Class<? extends Module>>singleton(GridFSBlobStoreContextModule.class));
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public ApiMetadata build() {
            return new GridFSApiMetadata(this);
        }
    }
}
