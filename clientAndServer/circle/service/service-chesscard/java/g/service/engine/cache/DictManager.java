package g.service.engine.cache;

import g.model.DictEnum;
import g.service.engine.manager.IDictManager;
import org.soul.commons.cache.CacheKey;
import org.soul.data.mapper.sys.SysDictMapper;
import org.soul.model.sys.po.SysDict;
import org.soul.model.sys.so.SysDictSo;
import org.soul.model.sys.vo.SysDictListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.nosql.redis.JedisTemplate;

import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by longer on 2017/1/10.
 * 字典管理器
 */
public class DictManager implements IDictManager {

    @Autowired
    private JedisTemplate jedisTemplateGame;

    @Autowired
    private SysDictMapper sysDictMapper;

    private ReentrantLock reentrantLock = new ReentrantLock();


    @Override
    public void load() {
        List<SysDict> sysDictList = loadActive();
        toCache(sysDictList);
    }

    /**
     * 保存到缓存
     *
     * @param sysDictList
     */
    protected void toCache(List<SysDict> sysDictList) {
        //TODO Double:需要bulk操作
        for (SysDict sysDict : sysDictList) {
            jedisTemplateGame.sadd(
                    CacheKey.getCacheKey(CacheKey.CACHE_KEY_DICTS, sysDict.getModule(), sysDict.getDictType())
                    , sysDict.getDictCode()
            );
        }
    }

    /**
     * 有效的字典
     *
     * @return
     */
    private List<SysDict> loadActive() {
        //TODO: double 只查询有效的
//        SysDictListVo dictListVo = new SysDictListVo();
//        SysDictSo sysDictSo = new SysDictSo();
//        sysDictSo.setActive(true);
//        dictListVo = sysDictService.search(dictListVo);
        List<SysDict> sysDicts = sysDictMapper.allSearch();
        return sysDicts;
    }

    /**
     * 指定模块,类别
     *
     * @param module
     * @param type
     * @return
     */
    private List<SysDict> loadByModuleAndType(String module, String type) {
        SysDictListVo dictListVo = new SysDictListVo();
        SysDictSo sysDictSo = new SysDictSo();
        sysDictSo.setModule(module);
        sysDictSo.setModule(type);
        sysDictSo.setActive(true);
        return sysDictMapper.search(dictListVo.getQuery().getCriteria());
    }


    @Override
    public Set<String> get(DictEnum dictEnum) {
        Set<String> rs = doGet(dictEnum);
        if (rs == null) {
            loadAndToCache(dictEnum);
            rs = doGet(dictEnum);
        }
        return rs;
    }

    protected void loadAndToCache(DictEnum dictEnum) {
        try {
            reentrantLock.lock();
            List<SysDict> sysDicts = loadByModuleAndType(dictEnum.getModule().getCode(), dictEnum.getType());
            toCache(sysDicts);
        } finally {
            reentrantLock.unlock();
        }
    }

    protected Set<String> doGet(DictEnum dictEnum) {
        return jedisTemplateGame.smembers(
                CacheKey.getCacheKey(CacheKey.CACHE_KEY_DICTS, dictEnum.getModule().getCode(), dictEnum.getType())
        );
    }

    @Override
    public void refresh(DictEnum dictEnum) {
        jedisTemplateGame.del(
                CacheKey.getCacheKey(CacheKey.CACHE_KEY_DICTS, dictEnum.getModule().getCode(), dictEnum.getType())
        );
        loadAndToCache(dictEnum);
    }

}
