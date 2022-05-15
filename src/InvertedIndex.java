
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


class Index {
    Map<Integer,String> sources;
    HashMap<String, ArrayList<Doc>> index;
    static List<String> stopwords;
    Index() throws IOException {
        sources = new HashMap<Integer,String>();
        index = new HashMap<String, ArrayList<Doc>>();
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
                    Doc d = new Doc(url, tag);
                if (!index.containsKey(word)) {
                    // the hashmap doesn't containts the word
                    index.put(word,new ArrayList<Doc>());
                    d.incDF();
                    index.get(word).add(d);

                }
                else {
                    // the hashmap containts the word
                    // now we want to check if the url is there and if exist check for the tags
                    // if the url is not there we will add a new doc to doclist
                    boolean found=false;
                    for (Doc doc:index.get(word)) {
                        if (doc.url.equals(url)) {
                            found=true;
                            doc.incDF();
                            if(!(doc.tag.contains(tag)))
                                doc.tag.add(tag);
                        }
                    }
                    if (!found){
                        d.incDF();
                        index.get(word).add(d);
                    }

                }
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

    public static HashMap<String, ArrayList<Doc>> Indexer(String[] urls) throws Exception {
        URLReader urlcontent = new URLReader();
         Index index = new Index();
        Map<String, String> page_content;
        for (String url:urls) {
            System.out.println(url);
            page_content=urlcontent.getContent(url);
            index.buildIndexfromlist(page_content,url);

        }
        index.index.keySet().removeAll(index.stopwords);
        for (String s:index.index.keySet()) {
            System.out.println("\n\n("+s+") : ");
            for (Doc d:index.index.get(s)) {
                System.out.println("url: "+d.url+"  Tags: "+d.tag+"Document Frequency: "+d.DF);
            }

        }
        return index.index;
    }
}