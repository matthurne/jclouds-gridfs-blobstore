# Overview

A JClouds BlobStore provider backed by MongoDB's GridFS.  Not all capabilities are supported, but there should be enough for common use cases.

# Usage

First, add a dependency to your build file.  Releases are published to [Bintray JCenter](https://bintray.com/bintray/jcenter).  See the [changelog](CHANGES.md) for the latest version.

Gradle:

```groovy
repositories {
    jcenter()
}
dependencies {
    compile "com.commercehub.jclouds:jclouds-gridfs-blobstore:1.0.0"
}
```

Maven:

```xml
<dependency>
    <groupId>com.commercehub.jclouds</groupId>
    <artifactId>jclouds-gridfs-blobstore</artifactId>
    <version>1.0.1</version>
</dependency>
```

Next, obtain an instance of the `BlobStore`.

```java
Properties overrides = new Properties();
overrides.setProperty(Constants.PROPERTY_ENDPOINT, "gridfs://my_mongo_server:27017");
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

To use a replica set, specify additional members as a comma or semicolon-separated list, like this:

```java
overrides.setProperty(Constants.PROPERTY_ENDPOINT, "gridfs://node1:27017;node2:27017;node3:27017");
```
