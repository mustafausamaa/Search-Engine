import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.net.URL;

public class Main {
    public static void main(String args[]) throws Exception {
        int threadNumber=3;
        Queue<String> seedSet=new LinkedList<String>();
        seedSet.add("https://stackoverflow.com/questions/46906163/how-to-write-data-to-firebase-with-a-java-program");
        seedSet.add("https://www.socialmediatoday.com/news/8-of-the-most-important-html-tags-for-seo/574987/");
        seedSet.add("https://clutch.co/seo-firms/resources/meta-tags-that-improve-seo");

        WebCrawler crawlingThread=new WebCrawler(14,seedSet);
        seedSet.toArray();
//        String url1="https://www.socialmediatoday.com/news/8-of-the-most-important-html-tags-for-seo/574987/";
//        String url2="https://www.socialmediatoday.com/news/8-of-the-most-important-html-tags-for-seo/574987/#skip-link-target";

        try(BufferedReader in = new BufferedReader(
                new InputStreamReader(new URL("https://clutch.co/seo-firms/resources/meta-tags-that-improve-seo/robots.txt").openStream()))) {
            String line = null;
            while((line = in.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//        for(int i=0;i<crawlingThread.visitedLinks.size();i++)
//        {
//            System.out.print(crawlingThread.visitedLinks.get(i)+"\n");
//            System.out.print(crawlingThread.documents.get(i).title()+"\n");
//        }

//        InvertedIndex indexer = new InvertedIndex();
//        indexer.Indexer(urls);
    }
}


