import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;

public class UpdateDocument {
    public static void main(String[] args) {

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

        Bson query = Filters.eq("id","16845-2015-ENFO");
        Bson updates = Updates.combine(Updates.set("result","Violation Issued"));
        UpdateResult result = collection.updateOne(query,updates);
        System.out.println("Update done!");
    }
}
