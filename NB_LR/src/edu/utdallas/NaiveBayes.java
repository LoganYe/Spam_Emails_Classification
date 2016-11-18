package edu.utdallas;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sycheng on 2/26/16.
 */
public class NaiveBayes {
    private Vocabulary v;
    private DocVocabulary dv = new DocVocabulary();
    private Map<Boolean, Double> prior = new HashMap<Boolean, Double>();

    public NaiveBayes(String positive, String negative, String filter) {
        if (filter != null) {
            this.v = new Vocabulary(filter);
        }
        else
            this.v = new Vocabulary();
        v.processInput(positive, true);
        v.processInput(negative, false);
        double positiveRate = 1.0D * v.getPositiveDoc() / (v.getNegativeDoc() + v.getPositiveDoc());
        double negativeRate = 1.0D - positiveRate;
        this.prior.put(true, Math.log(positiveRate));
        this.prior.put(false, 1.0 - Math.log(negativeRate));
        v.updateStatistics();
    }

    public double getAccuracy(String positive, String negative) {
        dv.processPath(positive, true);
        dv.processPath(negative, false);

        int count = 0, positiveCount = 0;
        for (Map.Entry<String, SingleVocabulary> entry : dv.getpList().entrySet()) {
            count++;
            SingleVocabulary svt = entry.getValue();
            if (svt.getLabel() == applyNavieBayes(svt.getStringToWord()))
                positiveCount++;
        }
        System.out.println("Ac:" + 1.0D* positiveCount/count);

        return 0.0;
    }

    private int applyNavieBayes(Map<String, Double> map) {
        boolean maxPosible = true;
        double maxPosibility = Double.NEGATIVE_INFINITY;

        for (Map.Entry<Boolean, Double> entry : this.prior.entrySet()) {
            double sum = entry.getValue();
            for (Map.Entry<String, Double> word : map.entrySet()) {
                sum += v.getProb(word.getKey(), entry.getKey());
            }
            if (sum > maxPosibility) {
                maxPosibility = sum;
                maxPosible = entry.getKey();
            }
        }
        return maxPosible ? 1 : 0;
    }
}
