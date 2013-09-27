package com.commercehub.jclouds.gridfs.blobstore;

import org.jclouds.providers.ProviderMetadata;
import org.jclouds.providers.internal.BaseProviderMetadata;

public class GridFSProviderMetadata extends BaseProviderMetadata {
    @Override
    public Builder toBuilder() {
        return new Builder().fromProviderMetadata(this);
    }

    public GridFSProviderMetadata() {
        super(new Builder());
    }

    protected GridFSProviderMetadata(Builder builder) {
        super(builder);
    }

    public static class Builder extends BaseProviderMetadata.Builder {
        protected Builder() {
            id("gridfs").name("MongoDB GridFS").apiMetadata(new GridFSApiMetadata());
        }

        @Override
        public GridFSProviderMetadata build() {
            return new GridFSProviderMetadata(this);
        }

        @Override
        public Builder fromProviderMetadata(ProviderMetadata in) {
            super.fromProviderMetadata(in);
            return this;
        }
    }
}
