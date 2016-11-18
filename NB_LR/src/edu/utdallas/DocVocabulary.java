package edu.utdallas;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by sycheng on 2/20/16.
 */
public class DocVocabulary {
    private static Set<String> filter;
    private static String filterFile;
    private Map<String, SingleVocabulary> pList = new HashMap<String, SingleVocabulary>();
    private Set<String> set = new HashSet<String>();

    public DocVocabulary() {

    }
    public DocVocabulary(String filterFile) {
        this.filterFile = filterFile;
    }

    public void processPath(String path, boolean positive) {
        if (filterFile != null) {
            filter = new HashSet<String>();
            File filterInput = new File(filterFile);
            try {
                Scanner scanner = new Scanner(filterInput);
                while (scanner.hasNext()){
                    filter.add(scanner.next());
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        File directory = new File(path);

        File[] fList = directory.listFiles();
        for (File file : fList) {
            if (file.isFile()) {
                parseFile(file, positive);
            }
        }
    }

    public Map<String, SingleVocabulary> getpList() {
        return pList;
    }

    private void parseFile(File file, boolean positive) {

        try {
            Scanner scanner = new Scanner(file);
            SingleVocabulary sv = new SingleVocabulary(positive);

            while (scanner.hasNext()) {
                String word = scanner.next();
                if (filter != null && filter.contains(word))
                    continue;
                sv.pubWord(word);
            }
            scanner.close();
            this.pList.put(file.getName(), sv);
            this.set.addAll(sv.getWords());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Set<String> getSet() {
        return set;
    }
}
