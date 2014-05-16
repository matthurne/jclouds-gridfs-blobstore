package com.commercehub.jclouds.gridfs.blobstore

import org.jclouds.ContextBuilder
import org.jclouds.blobstore.BlobStore
import org.jclouds.blobstore.BlobStoreContext
import spock.lang.Specification

import static com.commercehub.jclouds.gridfs.blobstore.Constants.GRIDFS_URI_SCHEME
import static org.jclouds.Constants.PROPERTY_ENDPOINT

class GridFSBlobStoreEndpointSpec extends Specification {
    private static final HOST = "localhost"
    private static final PORT = 27017

    private BlobStoreContext context
    private BlobStore blobStore

    private void initBlobStore(String endpoint) {
        // TODO: use embedded mongo
        def overrides = new Properties()
        overrides.setProperty(PROPERTY_ENDPOINT, endpoint)
        context = ContextBuilder.newBuilder("gridfs")
                .overrides(overrides)
                .buildView(BlobStoreContext)
        blobStore = context.getBlobStore()
    }

    def cleanup() {
        if (context) {
            context.close()
        }
    }

    def "can create blobStore with 'gridfs://' endpoint"() {
        expect:
        initBlobStore("${GRIDFS_URI_SCHEME}://${HOST}:${PORT}")
    }

    def "can create blobStore with standard connection string endpoint"() {
        expect:
        initBlobStore("mongodb://${HOST}:${PORT}")
    }
}
