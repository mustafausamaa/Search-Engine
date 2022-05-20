package com.Search.SearchEngine;

import com.Search.SearchEngine.model.Doc;
import com.Search.SearchEngine.model.Wordinfo;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

import static com.mongodb.client.model.Filters.eq;

@Service
public class DocumentService {

    public List<Doc> getDocuments(String searchWord) throws IOException {
        RelevanceRanker ranker = new RelevanceRanker();
        Map<Doc, Float> sortedoutputRank = new LinkedHashMap<Doc, Float>();
        sortedoutputRank=RelevanceRanker.Ranker(searchWord);
        return sortedoutputRank.keySet().stream().toList();
    }

    private List<Doc> documents= Arrays.asList(
            new Doc("www.google.com","h1","8 of the Most Important HTML Tags for SEO | Social Media Today","8 of the Most Important HTML Tags for SEO"),
            new Doc("www.facebook.com","h2","8 of the Most Important HTML Tags for ","8 of the Most Important HTML Tags for SEO"),
            new Doc("www.instagram.com","h3","HTML Tags for SEO | Social Media Today","8 of the Most Important HTML Tags for SEO")
            );


}