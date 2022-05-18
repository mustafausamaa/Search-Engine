//----------------------------------------------------------------------------------------------
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class WebCrawler {

    public int fetchedPages = 0;
    public int numberOfPagesToFetch;
    public int seedSetCounter=0;
    Queue<String> seedSet=new LinkedList<String>();
    public ArrayList<String> visitedLinks = new ArrayList<String>();
    public ArrayList<String> normalizedUrlLinks = new ArrayList<String>();
    public ArrayList<Document> documents = new ArrayList<Document>();
    public WebCrawler(int numberPages, Queue<String> urls) {
        this.numberOfPagesToFetch = numberPages;
        this.seedSet=urls;
        Crawl(seedSet.remove(),urls,fetchedPages);
    }

    private Document requestDocument(String url) {
        try {
            Connection connection = Jsoup.connect(url);
            Document document = connection.get();

            if (connection.response().statusCode() == 200) {
                System.out.print(" and url of the webpage is : " + url + "\n");
                System.out.print("webPage title is : " + document.title() + "\n");
                visitedLinks.add(url);
                urlNormalizerForDocument(url);
                documents.add(document);
                return document;
            }
            System.out.print("document request failed"+"\n");
            return null;
        } catch (IOException e) {
            System.out.print("document request failed"+"\n");
            return null;
        }

    }
    private String urlNormalizerForCrawling(String url)
    {
        String urlTemp="";
        for(int i=0;i<url.length();i++)
        {
            char s = url.charAt(i);
            if(s=='#')
            {
                break;
            }
            else
            {
                urlTemp=urlTemp+s;
            }
        }
        return urlTemp.replaceAll("\\W", "").toLowerCase();

    }
    private void urlNormalizerForDocument(String url)
    {
        String urlTemp="";
        for(int i=0;i<url.length();i++)
        {
            char s = url.charAt(i);
            if(s=='#')
            {
                break;
            }
            else
            {
                urlTemp=urlTemp+s;
            }
        }
        String normalizedUrl = urlTemp.replaceAll("\\W", "").toLowerCase();
        normalizedUrlLinks.add(normalizedUrl);


    }
    public void Crawl(String url,Queue<String> urls,int fPages) {

        if (fPages < numberOfPagesToFetch) {
            Document document = requestDocument(url);
            if (document != null) {
                try {
                    for (Element hyperLink : document.select("a[href]")) {
                        String nextPageLink = hyperLink.absUrl("href");
                        if (!normalizedUrlLinks.contains(urlNormalizerForCrawling(nextPageLink))) {
                            seedSet.add(nextPageLink);
                            Crawl(seedSet.remove(),urls,fetchedPages++);
                        }
                    }
                }
                catch (Exception e)
                {
                    System.out.print("Invalid url");
                    fetchedPages--;
                }

            }
//            else
//            {
//                Crawl(seedSet.remove(),urls,fetchedPages++);
//            }
        }

    }
}