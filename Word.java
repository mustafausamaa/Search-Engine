import java.util.ArrayList;
import java.util.List;

public final class Word {
    public String word;
    public List<Doc> docs;
    public int DF;

    public int getDF() {
        return DF;
    }

    public void setDF(int DF) {
        this.DF = DF;
    }

    @Override
    public String toString() {
        return "Word{" +
                "word='" + word + '\'' +
                ", docs=" + docs +
                '}';
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public List<Doc> getDocs() {
        return docs;
    }

    public void setDocs(List<Doc> docs) {
        this.docs = docs;
    }

    public Word(String word, List<Doc> docs,int DF) {
        this.DF=DF;
        this.word = word;
        this.docs = docs;
    }

    public Word() {
    }
}