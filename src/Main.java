import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
//import com.mongodb.util.JSON;
import com.mongodb.util.JSON;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;

public class Main {
    public static void main(String args[]) throws Exception {
        Logger logger = Logger.getLogger("org.mongodb.driver");
        logger.setLevel(Level.SEVERE);
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoClient client = MongoClients.create("mongodb+srv://admin:admin@cluster0.z7e5l.mongodb.net/?retryWrites=true&w=majority");

        MongoDatabase searchIndexDb = client.getDatabase("SearchEngine");


        searchIndexDb = searchIndexDb.withCodecRegistry(pojoCodecRegistry);
        MongoCollection<Word> collection = searchIndexDb.getCollection("words", Word.class);


        System.out.println("Database Connected.");


        String[] urls = {"https://stackoverflow.com/questions/46906163/how-to-write-data-to-firebase-with-a-java-program",
                "https://www.socialmediatoday.com/news/8-of-the-most-important-html-tags-for-seo/574987/"
                //, "https://clutch.co/seo-firms/resources/meta-tags-that-improve-seo"
        };
        InvertedIndex indexer = new InvertedIndex();
        List<Word> words = new ArrayList<Word>();

        //+collection.find();

        indexer.Indexer(urls);

        ArrayList<String> sortedKeys
                = new ArrayList<String>(indexer.index.index.keySet());

        Collections.sort(sortedKeys);


        for (String s : sortedKeys) {
            //System.out.println("\n\n(" + s + ") : ");

            Word found = null;
            Bson queryFilter = new Document("word", s);
            found = collection.find(queryFilter).first();
            if (found != null) {
                //System.out.println("found word"+found);
                //BasicDBList docsdbList= (BasicDBList) found.get("docs");

                System.out.println(found.toString());
                found.docs = indexer.index.index.get(s);
                collection.replaceOne(eq("word", s), found);


            } else {
                Word w = new Word(s, indexer.index.index.get(s), indexer.index.index.get(s).size());
                collection.insertOne(w);
            }


        }


    }
}
