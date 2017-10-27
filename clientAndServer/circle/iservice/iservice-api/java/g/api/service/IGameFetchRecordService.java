package g.api.service;

import g.model.api.param.BetRecordParams;
import g.model.api.result.ListResults;

/**
 * Created by tom on 16-4-8.
 */
public interface IGameFetchRecordService {

    /**
     * 获取下单记录
     * @param betRecordParams 获取下单记录参数对象
     * @return 获取下单记录结果对象
     */
    ListResults fetchRecord(BetRecordParams betRecordParams);
}
