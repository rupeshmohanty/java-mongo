import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

public class QueryCollection {
    public static void main(String[] args) {
        ConnString conn = new ConnString();
        ConnectionString connectionString = new ConnectionString(conn.connectionString);
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .serverApi(ServerApi.builder()
                        .version(ServerApiVersion.V1)
                        .build())
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("sample_training");
        MongoCollection<Document> collection = database.getCollection("inspections");

        // Returning all the documents matching the condition
        collection.find(Filters.and(Filters.gte("address.number",2500), Filters.eq("result","Violation Issued"))).forEach(doc -> System.out.println(doc.toJson()));

        // Returning the first document matching the condition
        Document doc = collection.find(Filters.and(Filters.gte("address.number",2500), Filters.eq("result","Violation Issued"))).first();
        System.out.println(doc.toJson());
    }
}
