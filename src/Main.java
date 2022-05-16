import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.Map;

public class Main {
    public static void main(String args[]) throws Exception {
        int threadNumber=3;
//        ArrayList<String> linksSet=new ArrayLis<>
        String[] urls={"https://stackoverflow.com/questions/46906163/how-to-write-data-to-firebase-with-a-java-program",
                "https://www.wikipedia.org/",
                "https://clutch.co/seo-firms/resources/meta-tags-that-improve-seo",
                "https://www.investopedia.com/terms/e/economics.asp",
                "https://pubmed.ncbi.nlm.nih.gov/",
                "https://github.com/",
                "https://en.wikipedia.org/wiki/Film",
                "https://www.coursera.org/",
                "https://en.wikipedia.org/wiki/Engineering",
                "https://www.udacity.com/online-learning-for-individuals?utm_source=gsem_brand&utm_medium=ads_r&utm_campaign=12907726228_c_individuals&utm_term=128479270744&utm_keyword=udacity_e&gclid=CjwKCAjw7IeUBhBbEiwADhiEMc95nes0YLsQQnbyMxuQ35r4hDvBNtFykC8316sE3JwazuPOqxRs5RoClGEQAvD_BwE"

        };
        ArrayList<Document>documents=new ArrayList<Document>();
        ArrayList<Thread> threads=new ArrayList<Thread>();
        ArrayList<WebCrawler> crawlingThreads=new ArrayList<WebCrawler>();
        for (int i=0;i<10;i++)
        {
            WebCrawler crawlingThread=new WebCrawler(urls[i],2,i,urls);
            crawlingThreads.add(crawlingThread);
            Thread newThread=new Thread(crawlingThread);
            threads.add(newThread);
            newThread.start();
        }
        for (int i=0; i<threads.size();i++)
        {
            threads.get(i).join();
        }
        for(int i=0;i<crawlingThreads.size();i++)
        {
            System.out.print("Thread with id : "+crawlingThreads.get(i).threadId+ " downloaded pages are :"+"\n");
            for(int j=0;j<crawlingThreads.get(i).documents.size();j++)
            {
                documents.add(crawlingThreads.get(i).documents.get(j));
            }
        }
        //--documents is a list containing the downloaded pages
        //-------------------------------------------------------------------------------------
//        InvertedIndex indexer = new InvertedIndex();
//        indexer.Indexer(urls);
    }
}
//-----------------------------------------------------------------------------------------------------------------------------
