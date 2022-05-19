//----------------------------------------------------------------------------------------------
import com.sun.source.tree.SynchronizedTree;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.lang.Object.*;
public class WebCrawler implements Runnable {

    private int threadId;
    public String hashString="123456789123456789";

    public WebCrawler(int threadId)  {

        this.threadId=threadId;
    }

    @Override
    public void run() {

        String str=FilesDealer.removeFromSeedSetFileAndGetTheUrl();
        Crawl(str);
    }
    public   boolean isValid(String url)
    {
        /* Try creating a valid URL */
        try {
            new URL(url).toURI();
            return true;
        }

        // If there was an Exception
        // while creating URL object
        catch (Exception e) {
            return false;
        }
    }


    public  Document requestDocument(String url) {
        //here i am requesting the document of the popped url from the seedSet then compare if the url is valid then compare
        // if then compare  this url html title when get hashed with has item not belong to the hashed documents if not
        // then hash its title and put it in the hashed documents also put its link in the visited url text file
        try {
            boolean isValid=isValid(url);
            if(isValid) {
                Connection connection = Jsoup.connect(url);
                Document document = connection.get();
                if (connection.response().statusCode() == 200) {

                    if (FilesDealer.checkIfCompressedDocumentIsInsideCompressedDocumentsFile(document.title()+hashString).equals("notVisited")&&FilesDealer.checkIfUrlIsInsideVisitedLinksFile(url).equals("notVisited")) {
                        FilesDealer.updateFile(document.title()+hashString,"compressedDocuments.txt");
                        System.out.print(" thread:  "+threadId+" got the url of the webpage  and it is : " + url + "\n");
                        FilesDealer.updateFile(url,"visitedLinks.txt");
                        return document;

                    } else {
                        System.out.print("document is a duplicate of another crawled document" + "\n");
                        return null;
                    }
                }
                System.out.print("document request failed" + "\n");
                return null;
            }
            else
            {
                return  null;
            }
        } catch (IOException e) {
            System.out.print("document request failed"+"\n");
            return null;
        }

    }
    public   String urlNormalizer(String url) {
        String urlTemp="";
        for(int i=0;i<url.length();i++)
        {
            char s = url.charAt(i);
            if(s=='#')
            {
                if(urlTemp.charAt(urlTemp.length()-1)=='/')
                {
                    urlTemp = urlTemp.substring(0, urlTemp.length() - 1);
                    break;
                }
                break;
            }
            else if (i==url.length()-1 && s=='/')
            {
                break;
            }
            else
            {
                urlTemp=urlTemp+s;
            }
        }
        return urlTemp;
//        return urlTemp.replaceAll("\\W", "").toLowerCase();

    }

    public  void Crawl(String url) {
        if (FilesDealer.readRemainingPagesToBeCrawled() >0) {
            String normalizedUrl=urlNormalizer(url);
            Document document = requestDocument(normalizedUrl);
            if (document != null) {

                for (Element hyperLink : document.select("a[href]")) {
                    String nextPageLink = hyperLink.absUrl("href");
                    String normalizedNextPageLink = urlNormalizer(nextPageLink);
                    if ( FilesDealer.checkIfUrlIsInsideVisitedLinksFile(normalizedNextPageLink).equals("notVisited")) {
                        {
                            if (!nextPageLink.contains("javascript")) {
                                FilesDealer.updateFile(normalizedNextPageLink, "SeedSetFile.txt");
                            }
                        }
                    }
                }
                FilesDealer.updatePagesNumberToBeCrawled(FilesDealer.readRemainingPagesToBeCrawled()- 1);
                String str=FilesDealer.removeFromSeedSetFileAndGetTheUrl();
                Crawl(str);
            }


            else
            {
//                if(seedSet.size()>0) {
                    FilesDealer.updatePagesNumberToBeCrawled(FilesDealer.readRemainingPagesToBeCrawled());
                    String str=FilesDealer.removeFromSeedSetFileAndGetTheUrl();
                    Crawl(str);
//                }
            }
        }

    }
}

