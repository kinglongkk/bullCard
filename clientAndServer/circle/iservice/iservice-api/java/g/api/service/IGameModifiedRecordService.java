package g.api.service;

import org.soul.model.gameapi.param.FetchModifiedRecordParam;
import org.soul.model.gameapi.result.FetchModifiedRecordResult;

/**
 * Created by tom on 16-4-8.
 */
public interface IGameModifiedRecordService {

    /**
     * 获取变更的下单记录
     *
     * @param fetchModifiedRecordParam 获取变更的下单记录参数对象
     * @return 获取变更的下单记录结果对象
     */
    FetchModifiedRecordResult fetchModifiedRecord(FetchModifiedRecordParam fetchModifiedRecordParam);
}
