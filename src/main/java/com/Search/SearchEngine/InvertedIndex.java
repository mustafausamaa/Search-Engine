package com.Search.SearchEngine;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.lang.Object;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import com.Search.SearchEngine.model.Doc;
import com.Search.SearchEngine.model.Wordinfo;
import com.mongodb.client.MongoCollection;


class Index {
    HashMap<String, ArrayList<Doc>> index;
    static List<String> stopwords;
    int noOfWords=0;

    Index() throws IOException {
        index = new HashMap<String, ArrayList<Doc>>();
        loadStopwords();
    }


    public static void loadStopwords() throws IOException {
            stopwords = Files.readAllLines(Paths.get("D://Uni//Spring 22//APT//Team12T//Search-Engine//src//main//java//com//Search//SearchEngine//english_stopwords.txt"));
            Collection<String> s= List.of(new String[]{" 1", " 2", " 3", " 4", " 5", " 6", " 7", " 8", " 9", " 0", " A", " Q"});
            stopwords.addAll(s);
    }
    public static String stemmWord(String originalWord){
        org.apache.lucene.analysis.PorterStemmer stem = new org.apache.lucene.analysis.PorterStemmer();
        return stem.stem(originalWord);
    }
    public static List<String> removeStoppingWords_Stemming(Object content)
    {
        List<String> listOfWords = new ArrayList<String>();
        //split sentences to words
        listOfWords.addAll(Arrays.asList(content.toString().split("\\W+")));
        //remove stopping words;
        //System.out.println(stopwords);
        listOfWords.removeAll(stopwords);

        // Compile the ReGex
        String regex = "[0-9]+";
        Pattern p = Pattern.compile(regex);

        for (int i = 0; i < listOfWords.size(); i++) {
//            Matcher m = p.matcher(listOfWords.get(i));
//            if (m.matches())
//                listOfWords.remove(i);
            //stemming words
            //System.out.println(listOfWords);
            listOfWords.set(i,stemmWord(listOfWords.get(i)));
        }
        return listOfWords;
    }

    public void buildIndex(Map<String, String> files, String url) {
        Object[] tags = files.keySet().toArray();
        Object[] content = files.values().toArray();

        List<String> listOfWords = new ArrayList<String>();

        for (int j = 0; j < content.length; j++) {
            String tag = tags[j].toString();
            listOfWords= removeStoppingWords_Stemming(content[j]);
            // put words in hashmap
            for (String word : listOfWords) {
                noOfWords++;
                word = word.toLowerCase();
                //System.out.println(files.get("title"));
                Doc d = new Doc(url, tag,files.get("h1"),files.get("title"));
                if (! index.containsKey(word)) {
                    // the hashmap doesn't contain the word
                    index.put(word, new ArrayList<Doc>());
                    d.incTF();
                    index.get(word).add(d);

                } else {
                    // the hashmap contains the word
                    // now we want to check if the url is there and if exist check for the tags
                    // if the url is not there we will add a new doc to doc-list
                    boolean found = false;
                    for (Doc doc : index.get(word)) {
                        if (doc.url.equals(url)) {
                            found = true;
                            doc.incTF();
                            if (! (doc.tag.contains(tag)))
                                doc.tag.add(tag);
                        }
                    }
                    if (! found) {
                        d.incTF();
                        index.get(word).add(d);
                    }

                }
            }
        }
    }

}

public class InvertedIndex {
        MongoCollection<Wordinfo> collection;

        static Index index;


    static {
        try {
            index = new Index();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public InvertedIndex() throws IOException {
    }


    public static HashMap<String, ArrayList<Doc>> Indexer(List<String> urls) throws Exception {
        URLReader urlcontent = new URLReader();
        Map<String, String> page_content;
        for (String url : urls) {
            System.out.println(url);
            page_content = urlcontent.getContent(url);
            index.buildIndex(page_content, url);

        }
        index.index.keySet().removeAll(index.stopwords);
        System.out.println(index.noOfWords);

        return index.index;
    }
}