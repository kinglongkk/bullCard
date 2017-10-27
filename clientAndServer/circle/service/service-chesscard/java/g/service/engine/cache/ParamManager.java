package g.service.engine.cache;

import g.model.DictEnum;
import g.service.chesscard.enums.ParamEnum;
import g.service.engine.manager.IParamManager;
import org.soul.commons.cache.CacheKey;
import org.soul.data.mapper.sys.SysParamMapper;
import org.soul.model.sys.po.SysParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.nosql.redis.JedisTemplate;

import java.util.List;
import java.util.Map;

/**
 * Created by longer on 2017/1/10.
 * 参数管理器
 */
public class ParamManager implements IParamManager {

    @Autowired
    private JedisTemplate jedisTemplateGame;

    @Autowired
    private SysParamMapper sysParamMapper;

    @Override
    public void load() {
        List<SysParam> paramList = sysParamMapper.allSearch();
        for (SysParam sysParam : paramList) {
            //TODO Double use bulk
            jedisTemplateGame.hset(
                    CacheKey.getCacheKey(CacheKey.CACHE_KEY_PARAMS, sysParam.getModule(), sysParam.getParamType()),
                    sysParam.getParamCode(),
                    sysParam.getParamValue()
            );
        }
    }

    @Override
    public Map<String, String> get(ParamEnum dictEnum) {
        return jedisTemplateGame.hgetAll(
                CacheKey.getCacheKey(CacheKey.CACHE_KEY_PARAMS, dictEnum.getModule().getCode(), dictEnum.getType())
        );
        //TODO:Double 如果为空的情况(过期)
    }

    @Override
    public void refresh(DictEnum dictEnum) {

    }
}
