package com.commercehub.jclouds.gridfs.blobstore;

import com.google.common.base.Function;
import com.mongodb.gridfs.GridFSDBFile;
import org.jclouds.blobstore.domain.MutableBlobMetadata;
import org.jclouds.blobstore.domain.internal.MutableBlobMetadataImpl;
import org.jclouds.io.MutableContentMetadata;

class DBFileToBlobMetadata implements Function<GridFSDBFile, MutableBlobMetadata> {
    @Override
    public MutableBlobMetadata apply(GridFSDBFile input) {
        MutableBlobMetadata metadata = new MutableBlobMetadataImpl();
        MutableContentMetadata contentMetadata = metadata.getContentMetadata();
        String contentType = input.getContentType();
        if (contentType != null) {
            contentMetadata.setContentType(contentType);
        }
        contentMetadata.setContentLength(input.getLength());
        metadata.setETag(input.getMD5());
        metadata.setLastModified(input.getUploadDate());
        metadata.setName(input.getFilename());
        metadata.getUserMetadata().putAll(input.getMetaData().toMap());
        // TODO: support populating metadata.getContentMetadata().setContentMD5()
        return metadata;
    }
}
