package g.service.param;

import g.model.common.Const;
import org.soul.commons.cache.CacheKey;
import org.soul.commons.cache.CacheToolProxy;
import org.soul.commons.collections.MapTool;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.soul.commons.param.IParamEnum;
import org.soul.model.sys.po.SysParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.Map;

/**
 * Created by Longer on 2016/9/18.
 * 参数服务,支持本地缓存
 */
public class ParamService implements IParamService {

    private static Log log = LogFactory.getLog(ParamService.class);

    @Autowired
    private CacheToolProxy cacheToolProxy;

    @Cacheable(value = "paramCache")
    @Override
    public String get(IParamEnum paramEnum) {
        SysParam sysParam = raw(Const.Default_Site_Id, paramEnum);
        if (sysParam == null) {
            return "";
        }

        if (StringTool.isNotBlank(sysParam.getParamValue())) {
            return sysParam.getParamValue();
        }

        if (StringTool.isNotBlank(sysParam.getDefaultValue())) {
            return sysParam.getDefaultValue();
        }

        return "";
    }

    @CacheEvict(value = "paramCache")
    @Override
    public void evict(IParamEnum paramEnum) {
        cacheToolProxy.refresh(cacheKey(Const.Default_Site_Id, paramEnum));

    }

    @CacheEvict(value = "paramCache", allEntries = true)
    @Override
    public void evictAll() {
//        cacheToolProxy.refresh();

    }

    /**
     * 获取某个参数原始记录
     *
     * @param siteId
     * @param paramEnum
     * @return
     */
    private SysParam raw(Integer siteId, IParamEnum paramEnum) {
        Map<String, SysParam> params = cacheToolProxy.get(cacheKey(siteId, paramEnum));
        if (MapTool.isEmpty(params)) {
            log.error("参数缓存为空!");
            return null;
        }
        return params.get(paramEnum.getCode());
    }

    /**
     * 完整的KEY,for CacheTool
     *
     * @param siteId
     * @param paramEnum
     * @return
     */
    private String cacheKey(Integer siteId, IParamEnum paramEnum) {
        return CacheKey.getCacheKey(CacheKey.CACHE_KEY_PARAMS, String.valueOf(siteId), paramEnum.getModule().getCode(), paramEnum.getType());
    }
}
