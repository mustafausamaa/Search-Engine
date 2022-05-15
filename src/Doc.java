import java.util.ArrayList;

public class Doc {
    String url="";
    ArrayList<String> tag=new ArrayList<String>();
    int DF;

    public Doc( String url,String tag) {
        this.url = url;
        addTag(tag);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void addTag(String tag) {
        this.tag.add(tag);
    }

    public void incDF() {
        DF++;
    }
}
