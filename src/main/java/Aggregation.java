import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class Aggregation {
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
        MongoCollection<Document> collection = database.getCollection("trips");

        // Aggregation pipeline with the different stages!
        List<? extends Bson> pipeline = Arrays.asList(
                new Document()
                        .append("$match", new Document()
                                .append("usertype", "Subscriber")
                                .append("tripduration", new Document()
                                        .append("$gt", 600.0)
                                )
                        ),
                new Document()
                        .append("$project", new Document()
                                .append("_id", 0.0)
                                .append("tripduration", 1.0)
                                .append("start_station_name", new Document()
                                        .append("$toUpper", "$start station name")
                                )
                                .append("end_station_name", new Document()
                                        .append("$toUpper", "$end station name")
                                )
                        )
        );

        // Aggregation stages on the collection
        collection.aggregate(pipeline)
                .allowDiskUse(false)
                .forEach(doc -> System.out.println(doc.toJson()));
    }
}
