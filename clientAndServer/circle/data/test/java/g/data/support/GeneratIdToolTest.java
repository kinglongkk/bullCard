package g.data.support;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by longer on 2016/12/29.
 */
public class GeneratIdToolTest {
    @Test
    public void genBetNo() throws Exception {

        String betNo = GeneratIdTool.genBetNo(0);
        assertEquals(betNo.length(),20);

        betNo = GeneratIdTool.genBetNo(null);
        assertEquals(betNo.length(),20);

        betNo = GeneratIdTool.genBetNo(1);
        assertEquals(betNo.length(),20);

        betNo = GeneratIdTool.genBetNo(99999);
        assertEquals(betNo.length(),20);

        betNo = GeneratIdTool.genBetNo(100000);
        assertEquals(betNo.length(),20);

        betNo = GeneratIdTool.genBetNo(999999);
        assertEquals(betNo.length(),20);

        betNo = GeneratIdTool.genBetNo(1999999);
        assertEquals(betNo.length(),20);

    }

}