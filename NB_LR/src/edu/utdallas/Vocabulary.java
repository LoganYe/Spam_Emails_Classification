package edu.utdallas;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by sycheng on 2/19/16.
 */
public class Vocabulary {
    private Map<String, Word> stringToWord = new HashMap<String, Word>();
    private Set<String> filter;
    private String filterFile;

    private int positiveDoc = 0;
    private int negativeDoc = 0;
    private int positiveWordsCount = 0;
    private int negativeWordsCount = 0;
    private int positiveTotalCount = 0;
    private int negativeTotalCount = 0;

    public Vocabulary() {

    }
    public Vocabulary(String filter) {
        this.filterFile = filter;
    }

    public int getPositiveDoc() {
        return positiveDoc;
    }

    public int getNegativeDoc() {
        return negativeDoc;
    }

    public void processInput(String path, boolean positive) {
        File directory = new File(path);
        if (this.filterFile != null) {
            this.filter = new HashSet<String>();
            File filterInput = new File(this.filterFile);
            try {
                Scanner scanner = new Scanner(filterInput);
                while (scanner.hasNext()){
                    this.filter.add(scanner.next());
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        File[] fList = directory.listFiles();
        for (File file : fList) {
            if (positive)
                positiveDoc ++;
            else
                negativeDoc ++;
            if ( file.isFile() ){
                parseFile(file, positive);
            }
        }
    }

    private void parseFile(File file, boolean positive) {
        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNext()) {
                String word = scanner.next();
                if (this.filter != null && this.filter.contains(word))
                    continue;
                addWord(word, positive);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void addWord(String str, boolean positive) {
        Word w = null;
        if (this.stringToWord.containsKey(str)) {
            w = this.stringToWord.get(str);
            w.addCount(positive);
        }
        else {
            w = new Word(str, positive);
            this.stringToWord.put(str, w);
        }
        if (positive && w.getPositiveCount() == 1)
            this.positiveWordsCount++;
        else if (!positive && w.getNegativeCount() == 1)
            this.negativeWordsCount++;

        if (positive)
            this.positiveTotalCount++;
        else
            this.negativeTotalCount++;
    }

    public int getWord(String str) {
        int result = 0;
        int pos = getWord(str, true);
        int neg = getWord(str, false);
        if (pos != -1)
            result += pos;
        if (neg != -1)
            result += neg;
        return result;
    }

    public int getWord(String str, boolean positive) {
        if (this.stringToWord.containsKey(str)) {
            Word w = this.stringToWord.get(str);
            if (positive)
                return w.getPositiveCount();
            else
                return w.getNegativeCount();
        }
        return -1;
    }
    public int getTotalCount(boolean positive) {
        if (positive)
            return this.positiveWordsCount;
        else
            return this.negativeWordsCount;
    }

    public void updateStatistics() {
        for (Map.Entry<String, Word> entry : this.stringToWord.entrySet()) {
            getProbability(entry.getValue());
        }
    }

    private void getProbability(Word w) {
        w.setPositiveRatio(Math.log(w.getPositiveCount() + 1) - Math.log(this.stringToWord.size() + this.positiveTotalCount));
        w.setNegativeRatio(Math.log(w.getNegativeCount() + 1) - Math.log(this.stringToWord.size() + this.negativeTotalCount));
    }

    public double getProb(String w, boolean positive) {
        Word word = this.stringToWord.get(w);
        if (word == null) return 0.0;
        return word.getProb(positive);
    }

    public int getPositiveTotalCount() {
        return positiveTotalCount;
    }

    public int getNegativeTotalCount() {
        return negativeTotalCount;
    }
}
