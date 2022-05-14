
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


class Index {
    Map<Integer,String> sources;
    HashMap<String, Doc> index;
    static List<String> stopwords;
    Index() throws IOException {
        sources = new HashMap<Integer,String>();
        index = new HashMap<String, Doc>();
        loadStopwords();
    }
    public static void loadStopwords() throws IOException {
        stopwords = Files.readAllLines(Paths.get("english_stopwords.txt"));
    }
    public void buildIndexfromlist(Map<String, String> files,String url){
        int i=0;
        Object[] tags= files.keySet().toArray();
        Object[] content=  files.values().toArray();
        for(int j=0;j<content.length;j++){
            String tag = tags[j].toString();
            String[] words = content[j].toString().split("\\W+");
            for (String word : words) {
                word = word.toLowerCase();
                if (!index.containsKey(word)) {
                    Doc d = new Doc(word, url, tag);
                    index.put(word, d);
                }
                if (!index.get(word).tag.contains(tag))
                    index.get(word).addTag(tag);
                if (!index.get(word).url.contains(url))
                    index.get(word).addUrl(url);
            }
            i++;
        }

    }

//    public void find(String phrase){
//        String[] words = phrase.split("\\W+");
//        HashSet<Integer> res = new HashSet<Integer>(index.get(words[0].toLowerCase()));
//        for(String word: words){
//            res.retainAll(index.get(word));
//        }
//
//        if(res.size()==0) {
//            System.out.println("Not found");
//
//            return;
//        }
//        System.out.println("Found in: ");
//        for(int num : res){
//            System.out.println("\t"+sources.get(num));
//        }
//    }

}

public class InvertedIndex {


    public static void main(String args[]) throws Exception {
        URLReader urlcontent = new URLReader();
        String url="https://stackoverflow.com/questions/46906163/how-to-write-data-to-firebase-with-a-java-program";
        Map<String, String> page_content= urlcontent.getContent(url);
        // System.out.println(page_content.);

        Index index = new Index();
        index.buildIndexfromlist(page_content,url);
        url="https://www.socialmediatoday.com/news/8-of-the-most-important-html-tags-for-seo/574987/";
        page_content= urlcontent.getContent(url);
        index.buildIndexfromlist(page_content,url);

        url="https://clutch.co/seo-firms/resources/meta-tags-that-improve-seo";
        page_content= urlcontent.getContent(url);
        index.buildIndexfromlist(page_content,url);
        index.index.keySet().removeAll(index.stopwords);
        for (String s:index.index.keySet()) {
            System.out.println(s+" : "+index.index.get(s).url+ index.index.get(s).tag);

        }
//        System.out.println("Print search phrase: ");
//        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//        String phrase = in.readLine();
//        index.find(phrase);


    }
}