package com.commercehub.jclouds.gridfs.blobstore;

import com.google.common.base.Function;
import com.mongodb.gridfs.GridFSDBFile;
import org.jclouds.blobstore.domain.Blob;
import org.jclouds.blobstore.domain.Blob.Factory;
import org.jclouds.blobstore.domain.MutableBlobMetadata;
import org.jclouds.io.Payload;
import org.jclouds.io.Payloads;

import javax.inject.Inject;

class DBFileToBlob implements Function<GridFSDBFile, Blob> {
    private final Factory blobFactory;
    private final DBFileToBlobMetadata dbFileToBlobMetadata;

    @Inject
    DBFileToBlob(Factory blobFactory, DBFileToBlobMetadata dbFileToBlobMetadata) {
        this.blobFactory = blobFactory;
        this.dbFileToBlobMetadata = dbFileToBlobMetadata;
    }

    @Override
    public Blob apply(GridFSDBFile input) {
        MutableBlobMetadata blobMetadata = dbFileToBlobMetadata.apply(input);
        Payload payload = Payloads.newInputStreamPayload(input.getInputStream());
        payload.setContentMetadata(blobMetadata.getContentMetadata());
        Blob blob = blobFactory.create(blobMetadata);
        blob.setPayload(payload);
        return blob;
    }
}
