jclouds-gridfs-blobstore
========================

A JClouds BlobStore provider backed by MongoDB's GridFS

# Usage

First, add a dependency to your build file.  See the [changelog](CHANGES.md) for the latest version.

TODO: example

Next, obtain an instance of the `BlobStore`.

```java
Properties overrides = new Properties();
overrides.setProperty(Constants.PROPERTY_ENDPOINT, "my_mongo_server:27017");
BlobStoreContext context = ContextBuilder.newBuilder("gridfs").overrides(overrides)
    .buildView(BlobStoreContext.class);
BlobStore blobStore = context.getBlobStore();
```

Then, use the blob store to put/get blobs.  The container is in the format **DB**[/**BUCKET**], where **DB** is the name of the database and **BUCKET** is the optional name of the GridFS bucket (which defaults to `fs`).

```java
blobStore.createContainerInLocation(null, "blobStore");
Blob blobToPut = blobStore.blobBuilder("myBlob").payload("myPayload").build();
blobStore.putBlob("blobStore", blob);
Blob blobFromGet = blobStore.getBlob("blobStore", "myBlob");
```

Finally, close the context to allow it to properly clean up.

```java
context.close();
```
