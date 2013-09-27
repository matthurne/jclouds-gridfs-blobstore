import com.mongodb.Mongo
import com.mongodb.MongoClient
import org.jclouds.Constants
import org.jclouds.ContextBuilder
import org.jclouds.blobstore.BlobStore
import org.jclouds.blobstore.BlobStoreContext
import org.jclouds.blobstore.options.CreateContainerOptions
import spock.lang.Shared
import spock.lang.Specification

class GridFSBlobStoreSpec extends Specification {
    private static final DB_NAME = getClass().simpleName
    private static final BUCKET = "bk1"
    private static final CONTAINER = "${DB_NAME}/${BUCKET}"

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

    def "can create and delete containers without container options"() {
        assert !blobStore.containerExists(CONTAINER)

        expect:
        blobStore.createContainerInLocation(null, CONTAINER)
        blobStore.containerExists(CONTAINER)
        !blobStore.createContainerInLocation(null, CONTAINER)
        blobStore.deleteContainer(CONTAINER)
        !blobStore.containerExists(CONTAINER)
    }

    def "can create and delete containers with NONE container options"() {
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
}
