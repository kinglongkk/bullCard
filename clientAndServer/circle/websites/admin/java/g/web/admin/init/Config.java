package g.web.admin.init;

import org.soul.web.support.BaseWebConf;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Kevice on 2015/3/26 0026.
 */
@Component
public class Config extends BaseWebConf {
    @Value("${exportFile.maxCount}")
    private Integer maxCount;

    /**
     * 导出组件限制的最大导出条数
     * @return
     */
    public Integer getMaxCount() {
        return maxCount;
    }
}
