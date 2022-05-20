package com.Search.SearchEngine;

import com.Search.SearchEngine.model.Doc;
import com.Search.SearchEngine.model.Wordinfo;
import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.io.IOException;
import java.util.*;

public class RelevanceRanker {
    static float tf_idf;
    static MongoCollection<Wordinfo> collection;
    public RelevanceRanker() throws IOException {
        collection=Main.intializeDB();
    }

    public static Map<Doc, Float> Ranker(String searchWord) throws IOException {

        HashMap<String, List<Doc>> words = new HashMap<String, List<Doc>>();
        Block<Wordinfo> loadwords = new Block<Wordinfo>() {
            @Override
            public void apply(final Wordinfo wordinfo) {
                words.put(wordinfo.word, wordinfo.docs);
            }
        };
        //collection.find().forEach(loadwords);
//        ArrayList<Wordinfo> wordinfoArrayList = collection.find().into(new ArrayList<Wordinfo>());
//        for (Wordinfo w:wordinfoArrayList) {
//            words.put(w.word, w.docs);
//        }

        return relRank(searchWord);
    }

    public static Map<Doc, Float> relRank(String searchedWords) throws IOException {
        InvertedIndex index = new InvertedIndex();
        List<String> searchedWordsArray = com.Search.SearchEngine.Index.removeStoppingWords_Stemming(searchedWords);

        Map<Doc, Float> outputRank = new LinkedHashMap<Doc, Float>();

        for (String s : searchedWordsArray) {
            Wordinfo found=null;
            Bson queryFilter = new Document("word",s);
            found= collection.find(queryFilter).first();

            float eachtfidf = 0;
            String dataUrl="";
            if (found != null) {
                for (Doc d : found.docs) {
                        dataUrl = d.url;
                        eachtfidf = d.tf * (1/(float)found.docs.size());
//                        System.out.println(eachtfidf);
//                        System.out.println(dataUrl);
                        if(outputRank.containsKey(dataUrl)){
                            float value=outputRank.get(dataUrl);
                            outputRank.put(d,eachtfidf+value);
                        }else{
                            outputRank.put(d,eachtfidf);
                        }

                }

            } else {
                // Do Nothing
            }
            tf_idf=eachtfidf;
        }
        Map<Doc, Float> sortedoutputRank = new LinkedHashMap<Doc, Float>();

        outputRank.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortedoutputRank.put(x.getKey(), x.getValue()));
        System.out.println(sortedoutputRank);

        return sortedoutputRank;
    }
}