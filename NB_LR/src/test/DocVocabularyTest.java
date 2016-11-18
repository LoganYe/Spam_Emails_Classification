package test;

import edu.utdallas.DocVocabulary;
import org.junit.Test;

import javax.print.Doc;

import static org.junit.Assert.*;

/**
 * Created by sycheng on 2/21/16.
 */
public class DocVocabularyTest {
    @Test
    public void testProcessPath() {
        String positive = "Example/positive/";
        String negative = "Example/negative/";

        DocVocabulary dv = new DocVocabulary();
        dv.processPath(positive, true);
        dv.processPath(negative, false);
        assertEquals(1, dv.getpList().get("D1").getWordValue("Chinese"), 0.00001);
        assertEquals(2, dv.getpList().get("D2").getWordValue("Chinese"), 0.00001);
        assertEquals(6, dv.getSet().size());
    }

}