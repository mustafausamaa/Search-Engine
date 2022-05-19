import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FilesDealer {
    static synchronized public String removeFromSeedSetFileAndGetTheUrl()
    {
        try {
                File file = new File("SeedSetFile.txt");
                Scanner fileScanner = new Scanner(file);
                String str = fileScanner.nextLine();
                FileWriter fileStream = new FileWriter("SeedSetFile.txt");
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
            FileReader fr = new FileReader("pagesToCrawl.txt");
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
            FileWriter fw=new FileWriter("pagesToCrawl.txt");
            PrintWriter pw=new PrintWriter(fw);
            pw.println (fPages);
            File realName = new File("pagesToCrawl.txt");
            realName.delete(); // remove the old file
            new File("pagesToCrawl.txt").renameTo(realName); // Rename temp file
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
            FileReader vl=new FileReader ("visitedLinks.txt");
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
            FileReader vs=new FileReader ("compressedDocuments.txt");
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
}
