import java.util.Map;

public class Main {
    public static void main(String args[]) throws Exception {
        String[] urls={"https://stackoverflow.com/questions/46906163/how-to-write-data-to-firebase-with-a-java-program",
                "https://www.socialmediatoday.com/news/8-of-the-most-important-html-tags-for-seo/574987/",
                "https://clutch.co/seo-firms/resources/meta-tags-that-improve-seo"
        };
        InvertedIndex indexer = new InvertedIndex();
        indexer.Indexer(urls);
    }
}
