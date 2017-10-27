package g.service.engine.cache;

import g.service.chesscard.enums.ParamEnum;
import g.service.engine.manager.IParamManager;
import g.service.engine.model.BaseTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Created by longer on 2017/1/10.
 */
public class ParamManagerTest extends BaseTestCase {

    @Autowired
    private IParamManager paramManager;

    @Test
    public void get() throws Exception {
        paramManager.load();

        Map<String,String> rs = paramManager.get(ParamEnum.BULL_100_BEI);
        System.out.println(rs);

        rs = paramManager.get(ParamEnum.BULL_BAO_BEI);
        System.out.println(rs);
    }

}