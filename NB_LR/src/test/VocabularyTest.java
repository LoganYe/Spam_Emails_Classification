package test;

import edu.utdallas.Vocabulary;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by sycheng on 2/20/16.
 */
public class VocabularyTest {
    @Test
    public void parseFileTest() {
        Vocabulary v = new Vocabulary();
        v.processInput("./Sample", true);
        assertEquals(1, v.getWord("forwarded"));
        assertEquals(4, v.getWord("/"));
        assertEquals(4, v.getWord("/", true));
        assertEquals(0, v.getWord("/", false));

        v.processInput("./Sample/", false);
        assertEquals(2, v.getWord("forwarded"));
        assertEquals(8, v.getWord("/"));
        assertEquals(4, v.getWord("/", true));
        assertEquals(4, v.getWord("/", false));

        assertEquals(v.getNegativeDoc(), 1);
        assertEquals(v.getPositiveDoc(), 1);

        assertEquals(27, v.getTotalCount(true));
        assertEquals(27, v.getTotalCount(false));

        assertEquals(82, v.getPositiveTotalCount());
        assertEquals(82, v.getNegativeTotalCount());
        v.updateStatistics();
        assertEquals(Math.log(1.0D*3/(82+27)),v.getProb("stella", true), 0.0000001);
    }

}