package edu.utdallas;

/**
 * Created by sycheng on 2/20/16.
 */
public class Word {
    private String content;
    private int positiveCount;
    private int negativeCount;

    private double positiveRatio = 0.0;
    private double negativeRatio = 0.0;

    public Word(String word, boolean positive) {
        this.content = word;
        if (positive)
            this.positiveCount++;
        else
            this.negativeCount++;
    }

    public String getContent() {
        return content;
    }

    public int getPositiveCount() {
        return positiveCount;
    }

    public int getNegativeCount() {
        return negativeCount;
    }

    public Word(String content) {
        this.content = content;
    }

    public void addCount(boolean positive) {
        if (positive)
            this.positiveCount++;
        else
            this.negativeCount++;
    }

    public void setPositiveRatio(double positiveRatio) {
        this.positiveRatio = positiveRatio;
    }

    public void setNegativeRatio(double negativeRatio) {
        this.negativeRatio = negativeRatio;
    }

    public double getProb(boolean positive) {
        if (positive)
            return this.positiveRatio;
        else
            return this.negativeRatio;
    }

}
