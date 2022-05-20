package com.Search.SearchEngine;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class WebCrawlerRunner {
    public static void main(String args[]) throws Exception {
        ArrayList<Thread> threads=new ArrayList<Thread>();
        for(int i=0;i<5;i++) {
            WebCrawler crawlingThread = new WebCrawler(i);
            Thread thread = new Thread(crawlingThread);
            thread.start();
            threads.add(thread);
        }
        for(int i=0;i<5;i++) {
            threads.get(i).join();
        }

        //visitedLinks.txt contains all the urls
        System.out.print("Finished crawling :\n");

    }
}