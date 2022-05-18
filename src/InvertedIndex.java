import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.lang.Object;



import org.apache.lucene.analysis.*;


class Index {
    HashMap<String, ArrayList<Doc>> index;
    HashMap<String, Integer> TF;
    static List<String> stopwords;
    int noOfWords=0;

    Index() throws IOException {
        index = new HashMap<String, ArrayList<Doc>>();
        TF = new  HashMap<String, Integer>();
        loadStopwords();
    }


    public static void loadStopwords() throws IOException {
            stopwords = Files.readAllLines(Paths.get("D://Uni//Spring 22//APT//Project//Eclipse//SE//src//english_stopwords.txt"));
            Collection<String> s= List.of(new String[]{" 1", " 2", " 3", " 4", " 5", " 6", " 7", " 8", " 9", " 0", " A", " Q"});
            stopwords.addAll(s);
    }

    public void buildIndexfromlist(Map<String, String> files, String url) {
        PorterStemmer stem = new PorterStemmer();
        Object[] tags = files.keySet().toArray();
        Object[] content = files.values().toArray();
        for (int j = 0; j < content.length; j++) {
            String tag = tags[j].toString();
            List<String> listOfWords = new ArrayList<String>();
            //split sentences to words
            listOfWords.addAll(Arrays.asList(content[j].toString().split("\\W+")));
            //remove stopping words;
            listOfWords.removeAll(stopwords);
            for (int i = 0; i < listOfWords.size(); i++) {
                if (listOfWords.get(i).equals("1")||listOfWords.get(i).equals("2")||
                        listOfWords.get(i).equals("3")||listOfWords.get(i).equals("4")||
                        listOfWords.get(i).equals("5")||listOfWords.get(i).equals("6")||
                        listOfWords.get(i).equals("7")||listOfWords.get(i).equals("8")||
                        listOfWords.get(i).equals("9")||listOfWords.get(i).equals("0")||listOfWords.get(i).equals("10")
                )
                    listOfWords.remove(i);
                //stemming words
                listOfWords.set(i, stem.stem(listOfWords.get(i)));
            }

            for (String word : listOfWords) {
                noOfWords++;
                word = word.toLowerCase();
                Doc d = new Doc(url, tag);
                if (! index.containsKey(word)) {
                    // the hashmap doesn't containts the word
                    TF.put(word,1);
                    index.put(word, new ArrayList<Doc>());
                    d.incTF();
                    index.get(word).add(d);

                } else {
                    // the hashmap containts the word
                    // now we want to check if the url is there and if exist check for the tags
                    // if the url is not there we will add a new doc to doclist
                    boolean found = false;
                    int tfnew= TF.get(word);
                    tfnew++;
                    TF.replace(word,tfnew);
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


    public static HashMap<String, ArrayList<Doc>> Indexer(String[] urls) throws Exception {
        URLReader urlcontent = new URLReader();
        Map<String, String> page_content;
        for (String url : urls) {
            System.out.println(url);
            page_content = urlcontent.getContent(url);
            index.buildIndexfromlist(page_content, url);

        }
        index.index.keySet().removeAll(index.stopwords);
        System.out.println(index.noOfWords);

        return index.index;
    }
}