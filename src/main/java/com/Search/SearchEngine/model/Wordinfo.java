package com.Search.SearchEngine.model;

import java.util.List;

public final class Wordinfo {
    public String word;
    public List<Doc> docs;
    public int df;

    public int getDf() {
        return df;
    }

    public void setDf(int df) {
        this.df = df;
    }

    @Override
    public String toString() {
        return "WordInfo{" +
                "word='" + word + '\'' +
                ", docs=" + docs +
                ", DF=" + df +
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

    public Wordinfo(String word, List<Doc> docs, int DF) {
        this.df =DF;
        this.word = word;
        this.docs = docs;
    }

    public Wordinfo() {
    }
}