import com.mongodb.Mongo
import com.mongodb.MongoClient
import org.jclouds.Constants
import org.jclouds.ContextBuilder
import org.jclouds.blobstore.BlobStore
import org.jclouds.blobstore.BlobStoreContext
import spock.lang.Specification

class GridFSBlobStoreSpec extends Specification {
    private static final DB_NAME = getClass().simpleName
    private static final BUCKET = "bk1"
    private static final CONTAINER = "${DB_NAME}/${BUCKET}"

    private Mongo mongo
    private BlobStoreContext context
    private BlobStore blobStore

    def setup() {
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

    def cleanup() {
        if (context) {
            context.close()
        }
        if (mongo) {
            mongo.getDB(DB_NAME).dropDatabase()
            mongo.close()
        }
    }

    def "can create and delete containers"() {
        assert !blobStore.containerExists(CONTAINER)

        // TODO: consider using locations?
        // TODO: consider using CreateContainerOptions?
        expect:
        blobStore.createContainerInLocation(null, CONTAINER)
        blobStore.containerExists(CONTAINER)
        !blobStore.createContainerInLocation(null, CONTAINER)
        blobStore.deleteContainer(CONTAINER)
        !blobStore.containerExists(CONTAINER)
    }
}
