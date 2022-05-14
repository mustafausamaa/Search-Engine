import java.util.ArrayList;

public class Doc {
    String word;
    ArrayList<String> url=new ArrayList<String>();
    ArrayList<String> tag=new ArrayList<String>();
    int DF;

    public Doc(String word, String url,String tag) {
        this.word = word;

        addUrl(url);
        addTag(tag);
    }

    public void addUrl(String url) {
        this.url.add(url);
    }

    public void addTag(String tag) {
        this.tag.add(tag);
    }

    public void incDF() {
        DF++;
    }
}
