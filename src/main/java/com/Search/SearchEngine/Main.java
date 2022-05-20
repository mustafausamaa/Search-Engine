package com.Search.SearchEngine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;

import com.Search.SearchEngine.model.Wordinfo;

//import com.mongodb.util.JSON;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;

public class Main {
    static List<String> urls;
    public static void loadUrl() throws IOException {
        urls = Files.readAllLines(Paths.get("D://Uni//Spring 22//APT//Search-Engine (1)//Search-Engine//src//main//java//com//Search//SearchEngine//visitedLinks.txt"));

    }
    public static void main(String args[]) throws Exception {
        MongoCollection<Wordinfo> collection=intializeDB();
        loadUrl();

        System.out.println(urls);

        InvertedIndex indexer = new InvertedIndex();
        indexer.collection=collection;
        List<Wordinfo> words = new ArrayList<Wordinfo>();

        //+collection.find();

        indexer.Indexer(urls);

        ArrayList<String> sortedKeys
                = new ArrayList<String>(indexer.index.index.keySet());

        Collections.sort(sortedKeys);


        for (String s :  sortedKeys) {
            //System.out.println("\n\n(" + s + ") : ");

            Wordinfo found =null;
            Bson queryFilter = new Document("word",s);
            found= collection.find(queryFilter).first();
            if(found!=null){
                //System.out.println("found word"+found);
            //BasicDBList docsdbList= (BasicDBList) found.get("docs");

                found.docs=indexer.index.index.get(s);
                found.df =indexer.index.index.get(s).size();
                //System.out.println(found.toString());
                collection.replaceOne(eq("word", s),found);



            }else
            {
                Wordinfo w = new Wordinfo(s,indexer.index.index.get(s),indexer.index.index.get(s).size());
                System.out.println(w.toString());
                collection.insertOne(w);
            }


        }


    }

    static MongoCollection<Wordinfo> intializeDB() {
        ((LoggerContext) LoggerFactory.getILoggerFactory()).getLogger("org.mongodb.driver").setLevel(Level.ERROR);
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoClient client = MongoClients.create("mongodb+srv://admin:admin@cluster0.z7e5l.mongodb.net/?retryWrites=true&w=majority");

        MongoDatabase searchIndexDb = client.getDatabase("SearchEngine");


        searchIndexDb = searchIndexDb.withCodecRegistry(pojoCodecRegistry);
        MongoCollection<Wordinfo> collection = searchIndexDb.getCollection("words", Wordinfo.class);

        System.out.println("Database Connected.");
        return collection;
    }
}