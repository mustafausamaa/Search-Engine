package com.Search.SearchEngine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import static com.Search.SearchEngine.FileDealer.fillRankerFile;

public class PopularityRank{
    private static final double DAMPING_FACTOR = 0.85;
    private double currentScore;
    private double prevScore;
    private Vector<String> hyperlinks;
    public Map<String, ArrayList<Double>> hyperLinksMap = new HashMap<>();

    public double getCurrentScore() {
        return currentScore;
    }

    public double getPreviousScore() {
        return prevScore;
    }

    public double setCurrentScore(double currentScore) {
        this.currentScore = currentScore;
        return currentScore;
    }

    public double setPreviousScore(double previousScore) {
        this.prevScore = previousScore;
        return previousScore;
    }

    // Takes document to call fillfilesRanker
    public PopularityRank(Map<String, ArrayList<Double>> hyperLinksMap){
        this.hyperLinksMap=hyperLinksMap;
    }


    //using damping factor
    private void setPagesPopularity() throws IOException {
        double score=0.0;
        //calculate page popularity rank iterating on document size containing hyperlinks
        for (int i = 0; i < hyperlinks.size(); i++) {
            score = 0.0;
            for (int j = 0; j < hyperlinks.size(); j++) {
                // if condition if the page is have a link to the page
                if (fillRankerFile(hyperlinks.get(j)).contains(hyperlinks.get(i))){
                    score += hyperLinksMap.get(hyperlinks.get(j)).get(0)/
                            (double) fillRankerFile(hyperlinks.get(j)).size();
                }
                hyperLinksMap.get(hyperlinks.get(i)).set(1,((1.0 - DAMPING_FACTOR) / (double) hyperlinks.size() + DAMPING_FACTOR * score));
            }
        }

        for (int i = 0; i < hyperlinks.size(); i++) {
            hyperLinksMap.get(hyperlinks.get(i)).set(0,hyperLinksMap.get(hyperlinks.get(i)).get(1));
        }
    }

    public void calculatePopularity(int numIterations) {
        double initialScore = 1.0 / (double) hyperlinks.size();
        for (int i = 0; i < hyperlinks.size(); i++) {
            hyperLinksMap.get(hyperlinks.get(i)).set(1,initialScore);
            hyperLinksMap.get(hyperlinks.get(i)).set(0,initialScore);
        }
        for(int i = 0; i < numIterations; i++) {
            try {
                setPagesPopularity();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}