package edu.utdallas;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by sycheng on 2/20/16.
 */
public class SingleVocabulary {
    private int wordCount = 0;
    private boolean positive = true;
    private Map<String, Double> stringToWord = new HashMap<String, Double>();
    private Set<String> words = new HashSet<String>();

    public Set<String> getWords() {
        return words;
    }

    public SingleVocabulary(boolean positive) {
        this.positive = positive;
    }

    public  Double getWordValue(String word) {
        return this.stringToWord.get(word);
    }

    public void pubWord(String word) {
        wordCount ++;
        this.words.add(word);
        if (this.stringToWord.containsKey(word)) {
            this.stringToWord.put(word, this.stringToWord.get(word) + 1);
        }
        else {
            this.stringToWord.put(word, 1.0);
        }
    }
    public int getLabel () {
        if (positive)
            return 1;
        return 0;
    }

    public Map<String, Double> getStringToWord() {
        return stringToWord;
    }
}
