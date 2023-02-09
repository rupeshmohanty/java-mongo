import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.conversions.Bson;

public class DeleteDocument {
    public static void main(String[] args) {
        // DB Connection
        ConnString CS = new ConnString();
        ConnectionString connectionString = new ConnectionString(CS.connectionString);
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .serverApi(ServerApi.builder()
                        .version(ServerApiVersion.V1)
                        .build())
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("sample_training");
        MongoCollection<Document> collection = database.getCollection("inspections");

        // deleteOne(query) working
        Bson query = Filters.eq("id","10084-2015-ENFO");
        DeleteResult deleteResult = collection.deleteOne(query);
        System.out.println("Deleted a document");
        System.out.println("\t" + deleteResult.getDeletedCount());

        // deleteMany(query) working
        Bson newQuery = Filters.eq("business_name","BISHWANATH BISWAS");
        DeleteResult deleteManyResult = collection.deleteMany(newQuery);
        System.out.println("Deleted " + deleteManyResult.getDeletedCount() + " document(s)");
    }
}
