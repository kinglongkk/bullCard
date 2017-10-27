package g.service.param;

import org.soul.commons.param.IParamEnum;

/**
 * Created by Longer on 2016/9/18.
 */
public interface IParamService {

    /**
     * 获取参数值，当前值为空，则使用默认和值
     * @param paramEnum
     * @return
     */
    String get(IParamEnum paramEnum);

    /**
     * 清除个别参数缓存
     * @param paramEnum
     */
    void evict(IParamEnum paramEnum);

    /**
     * 清除全部参数缓存
     */
    void evictAll();
}
