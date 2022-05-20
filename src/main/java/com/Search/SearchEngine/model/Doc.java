package com.Search.SearchEngine.model;

import java.util.ArrayList;

public class Doc {
    public String url="";
    public String title;
    public String description;
    public int tf;
    public ArrayList<String> tag=new ArrayList<String>();
    public Doc(){}

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Doc{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", TF=" + tf +
                ", tag=" + tag +
                '}';
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getTag() {
        return tag;
    }

    public void setTag(ArrayList<String> tag) {
        this.tag = tag;
    }

    public int getTf() {
        return tf;
    }

    public void setTf(int tf) {
        this.tf = tf;
    }


    public Doc(String url) {
        this.url = url;
    }

    public Doc(String url, String tag, String description,String title) {
        this.url = url;
        this.description=description;
        this.title=title;
        addTag(tag);
    }


    public void setUrl(String url) {
        this.url = url;
    }

    public void addTag(String tag) {
        this.tag.add(tag);
    }

    public void incTF() {
        tf++;
    }
}