package g.service.chesscard.param;

import g.model.SiteParamEnum;
import g.service.param.IParamService;
import g.service.engine.model.BaseTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Double on 2016/9/18.
 */
public class ParamServiceTest extends BaseTestCase{

    @Autowired
    private IParamService paramService;

    @Test
    public void get() throws Exception {
        String param1 = paramService.get(SiteParamEnum.DEPOSIT_DATA_HOURS);
        System.out.println("第一次加载:" + param1);
        String param2 = paramService.get(SiteParamEnum.DEPOSIT_DATA_HOURS);
        System.out.println("第二次加载:" + param2);
    }

    @Test
    public void evict() throws Exception {
        System.out.println("清除加载");
        paramService.evict(SiteParamEnum.DEPOSIT_DATA_HOURS);
        String param1 = paramService.get(SiteParamEnum.DEPOSIT_DATA_HOURS);
        System.out.println("第一次加载:" + param1);
        System.out.println("清除加载");
        paramService.evict(SiteParamEnum.DEPOSIT_DATA_HOURS);
        String param2 = paramService.get(SiteParamEnum.DEPOSIT_DATA_HOURS);
        System.out.println("第二次加载:" + param2);
    }

}