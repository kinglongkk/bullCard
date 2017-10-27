package g.service.chesscard.netBeans.common;

import g.service.chesscard.enums.TipEnum;
import g.service.webSocket.codeCreator.NetBeanField;

import java.util.HashMap;

/**
 * Created by MK on 2017/1/16.
 */
public class NbTest {
    @NetBeanField(collectionClass = Integer.class)
    public int[] ints = {111, 112, 113};
    @NetBeanField(collectionClass = String.class)
    public HashMap<String, String> strings = new HashMap<>();
    @NetBeanField(collectionClass = NbTip.class)
    public NbTip[] tipss = new NbTip[1];

    public NbTest() {
        int x = 111;
        strings.put("string1", "呵呵test1");
        strings.put("string2", "呵呵test2");
        strings.put("string3", "呵呵test3");
        tipss[0] = NbTip.tip(TipEnum.BALANCE_EMPTY);
    }
}
