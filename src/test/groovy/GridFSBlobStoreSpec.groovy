import com.mongodb.Mongo
import com.mongodb.MongoClient
import org.jclouds.Constants
import org.jclouds.ContextBuilder
import org.jclouds.blobstore.BlobStore
import org.jclouds.blobstore.BlobStoreContext
import org.jclouds.blobstore.options.CreateContainerOptions
import org.jclouds.blobstore.options.PutOptions
import spock.lang.Shared
import spock.lang.Specification

class GridFSBlobStoreSpec extends Specification {
    private static final DB_NAME = this.simpleName
    private static final BUCKET = "bk1"
    private static final CONTAINER = "${DB_NAME}/${BUCKET}"
    private static final BLOB_NAME = "JabbaTheHutt"
    private static final PAYLOAD = "Random data"

    @Shared
    private Mongo mongo
    @Shared
    private BlobStoreContext context
    @Shared
    private BlobStore blobStore

    def setupSpec() {
        String host = "localhost"
        int port = 27017
        mongo = new MongoClient(host, port)
        mongo.getDB(DB_NAME).dropDatabase()
        // TODO: use embedded mongo
        def overrides = new Properties()
        overrides.setProperty(Constants.PROPERTY_ENDPOINT, "${host}:${port}");
        context = ContextBuilder.newBuilder("gridfs")
            .overrides(overrides)
            .buildView(BlobStoreContext)
        blobStore = context.getBlobStore()
    }

    def cleanupSpec() {
        if (context) {
            context.close()
        }
        if (mongo) {
            mongo.getDB(DB_NAME).dropDatabase()
            mongo.close()
        }
    }

    def "can create and delete containers without create container options"() {
        assert !blobStore.containerExists(CONTAINER)

        expect:
        blobStore.createContainerInLocation(null, CONTAINER)
        blobStore.containerExists(CONTAINER)
        !blobStore.createContainerInLocation(null, CONTAINER)
        blobStore.deleteContainer(CONTAINER)
        !blobStore.containerExists(CONTAINER)
    }

    def "can create and delete containers with NONE create container options"() {
        assert !blobStore.containerExists(CONTAINER)

        expect:
        blobStore.createContainerInLocation(null, CONTAINER, CreateContainerOptions.NONE)
        blobStore.containerExists(CONTAINER)
        !blobStore.createContainerInLocation(null, CONTAINER, CreateContainerOptions.NONE)
        blobStore.deleteContainer(CONTAINER)
        !blobStore.containerExists(CONTAINER)
    }

    def "doesn't allow creating containers with public read"() {
        when:
        blobStore.createContainerInLocation(null, CONTAINER, CreateContainerOptions.Builder.publicRead())

        then:
        def e = thrown(IllegalArgumentException)
        e.message.contains("public read is not supported")
    }

    def "can create and delete blobs without put options"() {
        assert !blobStore.blobExists(CONTAINER, BLOB_NAME)
        // TODO: test put when container doesn't exist
        // TODO: test remove when container doesn't exist
        // TODO: test put to overwrite
        // TODO: test get blob, payloads and metadata

        expect:
        def payload = blobStore.blobBuilder(BLOB_NAME).payload(PAYLOAD).build()
        blobStore.putBlob(CONTAINER, payload) == "fd6073b6d8ba3a3c1ab5316b9c79e12b"
        blobStore.blobExists(CONTAINER, BLOB_NAME)

        when:
        blobStore.removeBlob(CONTAINER, BLOB_NAME)

        then:
        !blobStore.blobExists(CONTAINER, BLOB_NAME)
    }

    def "can create and delete blobs with multipart put options"() {
        assert !blobStore.blobExists(CONTAINER, BLOB_NAME)

        expect:
        def payload = blobStore.blobBuilder(BLOB_NAME).payload(PAYLOAD).build()
        blobStore.putBlob(CONTAINER, payload, PutOptions.Builder.multipart()) == "fd6073b6d8ba3a3c1ab5316b9c79e12b"
        blobStore.blobExists(CONTAINER, BLOB_NAME)

        when:
        blobStore.removeBlob(CONTAINER, BLOB_NAME)

        then:
        !blobStore.blobExists(CONTAINER, BLOB_NAME)
    }

    def "doesn't allow put with null payload"() {
        when:
        blobStore.putBlob(CONTAINER, blobStore.blobBuilder(BLOB_NAME).build())

        then:
        thrown(NullPointerException)
    }

    def "doesn't allow put with non-multipart"() {
        when:
        def payload = blobStore.blobBuilder(BLOB_NAME).payload(PAYLOAD).build()
        blobStore.putBlob(CONTAINER, payload, PutOptions.Builder.multipart(false))

        then:
        def e = thrown(IllegalArgumentException)
        e.message.contains("only multipart is supported")
    }
}
