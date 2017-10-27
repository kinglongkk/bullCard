package g.api.service;

import g.model.api.param.CheckTransferParams;
import g.model.api.result.Results;

/**
 * @author: tom
 * @date: 16-4-8
 */
public interface IGameCheckTransferService {

    /**
     * 检查转账状态
     *
     * @param checkTransferParams 检查转账状态参数对象
     * @return 检查转账状态结果对象
     */
    Results checkTransfer(CheckTransferParams checkTransferParams);
}
