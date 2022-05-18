import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
//import com.mongodb.util.JSON;
import com.mongodb.util.JSON;
import org.bson.Document;

public class Main {
    public static void main(String args[]) throws Exception {
        Logger logger = Logger.getLogger("org.mongodb.driver");
        logger.setLevel(Level.SEVERE);

        MongoClient client = MongoClients.create("mongodb+srv://admin:admin@cluster0.z7e5l.mongodb.net/?retryWrites=true&w=majority");

        MongoDatabase searchIndexDb = client.getDatabase("SearchEngine");

        MongoCollection collection = searchIndexDb.getCollection("words");


        String[] urls={"https://stackoverflow.com/questions/46906163/how-to-write-data-to-firebase-with-a-java-program",
                "https://www.socialmediatoday.com/news/8-of-the-most-important-html-tags-for-seo/574987/",
                "https://clutch.co/seo-firms/resources/meta-tags-that-improve-seo"
        };
        InvertedIndex indexer = new InvertedIndex();

        indexer.Indexer(urls);

        for (String s :  indexer.index.index.keySet()) {
            //System.out.println("\n\n(" + s + ") : ");
            Document x = new Document("Word", s);
            x.append("DF",indexer.index.index.get(s).size());
            Gson gson = new Gson();
            BasicDBList y=(BasicDBList) JSON.parse(gson.toJson(indexer.index.index.get(s)));
            x.append("docs",y);
            System.out.println(x);

            //collection.insertOne(x);
        }


    }
}