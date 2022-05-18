import java.util.ArrayList;

public class Doc {
    public String url="";
    public ArrayList<String> tag=new ArrayList<String>();
    public Doc(){}

    @Override
    public String toString() {
        return "Doc{" +
                "url='" + url + '\'' +
                ", tag=" + tag +
                ", TF=" + TF +
                '}';
    }

    public String getUrl() {
        return url;
    }

    public ArrayList<String> getTag() {
        return tag;
    }

    public void setTag(ArrayList<String> tag) {
        this.tag = tag;
    }

    public int getTF() {
        return TF;
    }

    public void setTF(int TF) {
        this.TF = TF;
    }

    int TF;

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

    public void incTF() {
        TF++;
    }
}