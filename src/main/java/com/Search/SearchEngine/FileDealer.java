package com.Search.SearchEngine;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;


public class FileDealer {
        static synchronized public String removeFromSeedSetFileAndGetTheUrl()
        {
            try {
                File file = new File("D://Uni//Spring 22//APT//Team12T//Search-Engine//src//main//java//com//Search//SearchEngine//SeedSetFile.txt");
                Scanner fileScanner = new Scanner(file);
                String str = fileScanner.nextLine();
                FileWriter fileStream = new FileWriter("D://Uni//Spring 22//APT//Team12T//Search-Engine//src//main//java//com//Search//SearchEngine//SeedSetFile.txt");
                BufferedWriter out = new BufferedWriter(fileStream);
                while (fileScanner.hasNextLine()) {
                    String next = fileScanner.nextLine();
                    if (next.equals("\n"))
                        out.newLine();
                    else
                        out.write(next);
                    out.newLine();
                }
                out.close();
                return str;

            }
            catch (Exception e)
            {
                System.out.print("error in updating the thread next number of pages to crawl \n");
                return null;
            }
        }
        static public synchronized void updateFile(String url,String file)
        {
            try {
                Writer output;
                output = new BufferedWriter(new FileWriter(file,true));  //clears file every time
                output.append(url+"\n");
                output.close();
            }
            catch (Exception e)
            {
                System.out.print("error in updating "+file +"\n");
            }

        }
        static public synchronized int readRemainingPagesToBeCrawled()
        {
            try {
                FileReader fr = new FileReader("D://Uni//Spring 22//APT//Team12T//Search-Engine//src//main//java//com//Search//SearchEngine//pagesToCrawl.txt");
                BufferedReader br = new BufferedReader(fr);
                String numberPages = br.readLine();
                return  Integer.parseInt(numberPages);
            }
            catch (Exception e)
            {
                System.out.print("error while reading page number\n");
            }
            System.out.print("7asal moshkla feh reading remaining pages to be crawled");
            return 0;
        }
        static public synchronized void updatePagesNumberToBeCrawled( int fPages)
        {
            try {
                FileWriter fw=new FileWriter("D://Uni//Spring 22//APT//Team12T//Search-Engine//src//main//java//com//Search//SearchEngine//pagesToCrawl.txt");
                PrintWriter pw=new PrintWriter(fw);
                pw.println (fPages);
                File realName = new File("D://Uni//Spring 22//APT//Team12T//Search-Engine//src//main//java//com//Search//SearchEngine//pagesToCrawl.txt");
                realName.delete(); // remove the old file
                new File("D://Uni//Spring 22//APT//Team12T//Search-Engine//src//main//java//com//Search//SearchEngine//pagesToCrawl.txt").renameTo(realName); // Rename temp file
                fw.close();
                pw.close();
            }
            catch (Exception e)
            {
                System.out.print("error in updating the thread next number of pages to crawl \n");
            }

        }
        static public synchronized String checkIfUrlIsInsideVisitedLinksFile(String url)
        {
            try{
                FileReader vl=new FileReader ("D://Uni//Spring 22//APT//Team12T//Search-Engine//src//main//java//com//Search//SearchEngine//visitedLinks.txt");
                BufferedReader vll=new BufferedReader (vl);
                String visitedLink;
                while ((visitedLink=vll.readLine ()) != null){
                    if(visitedLink.equals(url))
                    {
                        vll.close ();
                        vl.close();
                        return "visited";
                    }
                }
                vll.close ();
                vl.close();
                return "notVisited";
//                System.out.print(visitedLinks.size());
            }catch (IOException e) {
                System.out.print("error reading from the visitedLink file\n");
                return "error";
            }
        }

        static public synchronized String checkIfCompressedDocumentIsInsideCompressedDocumentsFile(String hashedDocumentTitle)
        {
            try{
                FileReader vs=new FileReader ("D://Uni//Spring 22//APT//Team12T//Search-Engine//src//main//java//com//Search//SearchEngine//compressedDocuments.txt");
                BufferedReader vss=new BufferedReader (vs);
                String hashedDocument;
                while ((hashedDocument=vss.readLine ()) != null){
                    if(hashedDocument.equals(hashedDocumentTitle))
                    {
                        vs.close ();
                        vss.close();
                        return "visited";
                    }
                }
                vss.close ();
                vs.close();
                return "notVisited";
//                System.out.print(compressedDocumentsHashed.size());
            }catch (IOException e) {
                System.out.print("error reading from the hashedDocuments file\n");
                return "error";
            }
        }

        static public synchronized Vector<String> fillRankerFile(String url) throws IOException {
            Document document = Jsoup.connect(url).timeout(30000).get();
            Vector<String> pageRankUrls = null;
            for (Element hyperLink : document.select("a[href]")) {
                String nextPageLink = hyperLink.absUrl("href");
                pageRankUrls.add(nextPageLink);
            }
            return pageRankUrls;
        }



    }