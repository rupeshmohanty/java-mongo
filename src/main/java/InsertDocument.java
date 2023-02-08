// Java Code to insert Mongo document into a collection

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class InsertDocument {
    public static void main(String[] args) {
        // DB Connection
        ConnectionString connectionString = new ConnectionString("mongodb+srv://rupesh:Password12345@testcluster.uldqq9y.mongodb.net/?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .serverApi(ServerApi.builder()
                        .version(ServerApiVersion.V1)
                        .build())
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("sample_training");
        MongoCollection<Document> collection = database.getCollection("inspections");

        Document inspection = new Document("_id", new ObjectId())
                .append("id", "10021-2015-ENFO")
                .append("certificate_number", 9278806)
                .append("business_name", "ATLIXCO DELI GROCERY INC.")
                .append("date", Date.from(LocalDate.of(2015, 2, 20).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .append("result", "No Violation Issued")
                .append("sector", "Cigarette Retail Dealer - 127")
                .append("address", new Document().append("city", "RIDGEWOOD").append("zip", 11385).append("street", "MENAHAN ST").append("number", 1712));

        InsertOneResult result = collection.insertOne(inspection);
        BsonValue id = result.getInsertedId();
        System.out.println(id);
    }
}
