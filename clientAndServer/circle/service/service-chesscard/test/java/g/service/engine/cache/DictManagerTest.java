package g.service.engine.cache;

import g.model.DictEnum;
import g.service.engine.manager.IDictManager;
import g.service.engine.model.BaseTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * Created by longer on 2017/1/10.
 */
public class DictManagerTest extends BaseTestCase{

    @Autowired
    private IDictManager dictManager;

    @Test
    public void get() throws Exception {
        dictManager.load();
        Set<String> rs = dictManager.get(DictEnum.COMMON_LANGUAGE);
        System.out.println(rs);
    }

}