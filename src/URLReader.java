import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class URLReader {
    static String url;
    static Document doc;
    String get_Title(){
        Elements body = doc.select("title");
        String title= body.text();
        return title;
    }
    String get_H1(){
        Elements body = doc.select("h1");
        String h1= body.text();
        return h1;
    }
    String get_H2(){
        Elements body = doc.select("h2");
        String h2= body.text();
        return h2;
    }
    String get_H3(){
        Elements body = doc.select("h3");
        String h3= body.text();
        return h3;
    }
    String get_p(){
        Elements body = doc.select("p");
        String p= body.text();
        return p;
    }
    String get_table(){
        Elements body = doc.select("table");
        String p= body.text();
        return p;
    }

    public  Map<String, String> getContent(String url) throws Exception {
        doc = Jsoup.connect(url).timeout(6000).get();
        Map<String, String> temp= new HashMap<>();
        temp.put("title",get_Title());
        temp.put("h1",get_H1());
        temp.put("h2",get_H2());
        temp.put("h3",get_H3());
        temp.put("table",get_table());
        temp.put("p",get_p());
        return temp;
    }
}