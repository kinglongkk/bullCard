package g.api.service;

import g.model.api.param.ConfirmTransactionParams;
import g.model.api.result.Results;

/**
 * 确认转账
 * Created by black on 2016/11/18.
 */
public interface IGameConfirmTransactionService {

    /**
     * 确认转账
     * @param confirmTransactionParams
     * @return ConfirmResult
     */
    Results confirm(ConfirmTransactionParams confirmTransactionParams);

}
