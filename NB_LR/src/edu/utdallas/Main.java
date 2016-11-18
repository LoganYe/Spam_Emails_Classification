package edu.utdallas;

import java.util.Map;

/**
 * Created by sycheng on 2/21/16.
 */
public class Main {
    public static void main(String []args) {

        String filter = null;
        if (args.length < 7) {
            System.out.println("Argument: eta lambda iteration TrainHamDir TrainSpamDir TestHamDir TestSpamDir [filterFile]");
        }
        double eta = Double.parseDouble(args[0]);
        double lambda = Double.parseDouble(args[1]);
        int iteraton = Integer.parseInt(args[2]);
        if (args.length == 8)
            filter = args[7];

        String positive = args[3];
        String negative = args[4];
        String testPositive = args[5];
        String testNegative = args[6];

        NaiveBayes nb = new NaiveBayes(positive, negative, filter);
        nb.getAccuracy(testPositive, testNegative);

        DocVocabulary dv = new DocVocabulary(filter);
        dv.processPath(positive, true);
        dv.processPath(negative, false);

        LogisticRegression lr = new LogisticRegression(dv.getSet(), eta, lambda);
        lr.gradientDescent(iteraton, dv.getpList());

        DocVocabulary dvT = new DocVocabulary();
        dvT.processPath(testPositive, true);
        dvT.processPath(testNegative, false);

        int count = 0;
        int positiveCount = 0;
        for (Map.Entry<String, SingleVocabulary> entry : dvT.getpList().entrySet()) {
            count++;
            SingleVocabulary svt = entry.getValue();
            if (svt.getLabel() == lr.getDecision(svt.getStringToWord()))
                positiveCount++;
        }
        System.out.println("Ac:" + 1.0D* positiveCount/count);
        System.out.println("W0:" + lr.getW());
    }
}
