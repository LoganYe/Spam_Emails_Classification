package test;

import edu.utdallas.DocVocabulary;
import edu.utdallas.LogisticRegression;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by sycheng on 2/21/16.
 */
public class LogisticRegressionTest {
    @Test
    public void testGradientDescent() {
        String positive = "Example/positive/";
        String negative = "Example/negative/";

        DocVocabulary dv = new DocVocabulary();
        dv.processPath(positive, true);
        dv.processPath(negative, false);

        LogisticRegression lr = new LogisticRegression(dv.getSet(), 0.01, 1.0);
        lr.gradientDescent(500, dv.getpList());
        assertEquals(0, lr.getDecision(dv.getpList().get("D1").getStringToWord()));
        assertEquals(1, lr.getDecision(dv.getpList().get("D2").getStringToWord()));
        assertEquals(1, lr.getDecision(dv.getpList().get("D3").getStringToWord()));
        assertEquals(1, lr.getDecision(dv.getpList().get("D4").getStringToWord()));
        System.out.println(lr.getW());
        System.out.println(lr.getW0());
    }

}