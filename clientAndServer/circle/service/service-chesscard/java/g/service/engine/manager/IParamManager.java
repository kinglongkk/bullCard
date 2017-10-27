package g.service.engine.manager;

import g.model.DictEnum;
import g.service.chesscard.enums.ParamEnum;

import java.util.Map;
import java.util.Set;

/**
 * Created by longer on 2017/1/10.
 * 参数管理器
 */
public interface IParamManager {

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
    Map<String, String> get(ParamEnum dictEnum);

    /**
     * 刷新字典
     *
     * @param dictEnum
     */
    void refresh(DictEnum dictEnum);
}
