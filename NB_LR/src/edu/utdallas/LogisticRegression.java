package edu.utdallas;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by sycheng on 2/20/16.
 */
public class LogisticRegression {
    private Map<String, Double> w = new HashMap<String, Double>();
    private double w0 = 0.01D;
    private double w0Prime = 0.1;
    private double lambda = 1.0D;
    private double eta = 0.01;
    private double prediction = 0.0;

    public LogisticRegression(Set<String> w, double eta, double lambda) {
        this.eta = eta;
        this.lambda = lambda;
        for (String word: w)
            this.w.put(word, 0.00D);
    }

    public void gradientDescent(int n, Map<String, SingleVocabulary> map) {
        for (int i = 0; i < n; i++) {
            Map<String, Double> wPrime = new HashMap<String, Double>();
            updateW0(map);
            for (Map.Entry<String, Double> entry : this.w.entrySet()) {
                wPrime.put(entry.getKey(), getUpdate(entry.getKey(), map));
            }
            updateW(wPrime);
        }
    }

    private Double getUpdate(String key, Map<String, SingleVocabulary> map) {
        double result = this.w.get(key) * (1 - this.eta * this.lambda);
        double sum = 0.0;
        for (Map.Entry<String, SingleVocabulary> entry : map.entrySet()) {
            if (entry.getValue().getWordValue(key) != null)
                sum += (entry.getValue().getLabel()
                        - getProb1(entry.getValue().getStringToWord()))
                        * entry.getValue().getWordValue(key);

        }
        result += sum * this.eta;
        return result;
    }

    private void updateW(Map<String, Double> wPrime) {
        this.w = wPrime;
        this.w0 = this.w0Prime;
    }

    private void updateW0(Map<String, SingleVocabulary> map) {
        w0Prime = w0 * (1 - this.eta * this.lambda);
        double sum = 0.0;
        for (Map.Entry<String, SingleVocabulary> entry : map.entrySet()) {
            sum += (entry.getValue().getLabel()
                    - getProb1(entry.getValue().getStringToWord()));
        }
        w0Prime += this.eta * sum;
        System.out.println("w0:" + w0Prime);
    }

    public int getDecision(Map<String, Double> map) {
        double result = getProb(map);
        System.out.println(result);
        if (result > 0)
            return 1;
        return 0;
    }

    private double getProb(Map<String, Double> map) {
        double result = w0;
        for (Map.Entry<String, Double> entry : w.entrySet()) {
            if (map.containsKey(entry.getKey()))
                result += map.get(entry.getKey()) * entry.getValue();
        }
        //System.out.println("power:" + result);
        return result;
    }
    private double getProb1(Map<String, Double> map) {
        return 1 - 1/(1+Math.exp(getProb(map)));
    }

    public double getW0() {
        return w0;
    }

    public Map<String, Double> getW() {
        return w;
    }

    public void setW(Map<String, Double> w) {
        this.w = w;
    }

    public void setW0(double w0) {
        this.w0 = w0;
    }

    public void setW0Prime(double w0Prime) {
        this.w0Prime = w0Prime;
    }

    public void setLambda(double lambda) {
        this.lambda = lambda;
    }

    public void setEta(double eta) {
        this.eta = eta;
    }

    public void setPrediction(double prediction) {
        this.prediction = prediction;
    }
}
