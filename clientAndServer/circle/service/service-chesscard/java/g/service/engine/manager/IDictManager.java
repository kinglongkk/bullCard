package g.service.engine.manager;

import g.model.DictEnum;

import java.util.Set;

/**
 * Created by longer on 2017/1/10.
 */
public interface IDictManager {

    /**
     * 加载字典
     */
    void load();

    /**
     * 通过字典枚举,获取相关的字典
     *
     * @param dictEnum
     * @return
     */
    Set<String> get(DictEnum dictEnum);

    /**
     * 刷新字典
     *
     * @param dictEnum
     */
    void refresh(DictEnum dictEnum);
}
