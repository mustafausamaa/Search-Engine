import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static void main(String args[]) throws Exception {
        ArrayList<Thread>threads=new ArrayList<Thread>();
        for(int i=0;i<20;i++) {
            WebCrawler crawlingThread0 = new WebCrawler(i);
            Thread thread = new Thread(crawlingThread0);
            thread.start();
            threads.add(thread);
        }
        for(int i=0;i<20;i++) {
           threads.get(i).join();
        }
        //visitedLinks.txt contains all the urls
        System.out.print("Finished crawling :\n");

    }
}