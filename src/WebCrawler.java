import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;

public class WebCrawler implements Runnable{

    public  int x=0;
    public int fetchedPages=0;
    public int numberOfPagesToFetch;
    public String pageLink;
    public int threadId;
    public String[] seedSet;
    public ArrayList<String> visitedLinks=new ArrayList<String>();
    ArrayList<Document>documents=new ArrayList<Document>();
    public  WebCrawler(String link,int numberPages,int threadId,String[] urls)
    {
        this.numberOfPagesToFetch=numberPages;
        this.pageLink=link;
        this.threadId=threadId;
    }
    private Document requestDocument(String url)
    {
        try {
            Connection connection= Jsoup.connect(url);
            Document document=connection.get();
            if (connection.response().statusCode()==200)
            {
                System.out.print("thread id is : "+threadId + " and url of the webpage is : "+url +"\n");
                System.out.print("webPage title is : "+document.title()+"\n");
                visitedLinks.add(url);
                return  document;
            }
            return null;
        }
        catch (IOException e)
        {
            return  null;
        }

    }
    private void Crawl(String url,int f)
    {
        if(f<numberOfPagesToFetch)
        {
            Document document=requestDocument(url);
            if(document!=null)
            {
                documents.add(document);
                for(Element hyperLink:document.select("a[href]"))
                {
                    String nextPageLink=hyperLink.absUrl("href");
                    if(!visitedLinks.contains(nextPageLink))
                    {
                        Crawl(nextPageLink,fetchedPages++);
                    }
                }
            }
        }

    }
    @Override
    public void run()
    {
        Crawl(pageLink,fetchedPages);
    }
}
