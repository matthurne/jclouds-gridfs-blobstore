package com.commercehub.jclouds.gridfs.blobstore;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import org.jclouds.blobstore.AsyncBlobStore;
import org.jclouds.blobstore.BlobStore;
import org.jclouds.blobstore.attr.ConsistencyModel;
import org.jclouds.blobstore.config.BlobStoreMapModule;
import org.jclouds.blobstore.config.BlobStoreObjectModule;

public class GridFSBlobStoreContextModule extends AbstractModule {
    @Override
    protected void configure() {
        // TODO: remove when async binding is no longer required
        bind(AsyncBlobStore.class).to(NotSupportedAsyncBlobStore.class).in(Scopes.SINGLETON);
        bind(BlobStore.class).to(GridFSBlobStore.class).in(Scopes.SINGLETON);
        install(new BlobStoreObjectModule());
        install(new BlobStoreMapModule());
        bind(ConsistencyModel.class).toInstance(ConsistencyModel.STRICT);
        // TODO: consider whether to bind custom blob utils, blob key validator, container name validator
//      bind(LocalStorageStrategy.class).to(FilesystemStorageStrategyImpl.class);
//      bind(BlobUtils.class).to(FileSystemBlobUtilsImpl.class);
//      bind(FilesystemBlobKeyValidator.class).to(FilesystemBlobKeyValidatorImpl.class);
//      bind(FilesystemContainerNameValidator.class).to(FilesystemContainerNameValidatorImpl.class);
    }
}
