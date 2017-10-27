package g.api.biz;

import g.api.base.BaseService;
import g.api.service.IGameFetchBalanceService;
import g.model.admin.agent.message.po.UserAgent;
import g.model.api.enums.ResultStatusEnums;
import g.model.api.param.FetchBalanceParams;
import g.model.api.param.Params;
import g.model.api.result.ObjectResults;
import g.model.api.result.Results;
import g.model.player.param.BalanceParam;
import g.model.player.vo.PlayerTransactionVo;
import g.service.common.IPlayerTransactionService;
import org.soul.commons.collections.CollectionTool;
import org.soul.model.gameapi.result.BalancesResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class FetchBalanceService extends BaseService implements IGameFetchBalanceService {

    @Autowired
    private IPlayerTransactionService playerTransactionService;

    @Override
    public Results fetchBalance(FetchBalanceParams params) {

        ObjectResults result = new ObjectResults();
        if (!validatePreConditions(params)) {
            throwBusinessException(ResultStatusEnums.VALIDATE_PARAMS_FAILED);
        }
        UserAgent userAgent = getUserAgent(params.getMerchantNo());
        if (userAgent == null) {
            throwBusinessException(ResultStatusEnums.AGENT_NOT_EXISTS);
        }
        PlayerTransactionVo playerTransactionVo = new PlayerTransactionVo();
        List<String> account = params.getAccounts();
        BalanceParam balanceParam = new BalanceParam(account, userAgent.getAgentId());
        playerTransactionVo.setBalanceParam(balanceParam);
        Map<String, Double> accountToBalanceMap = this.playerTransactionService.queryWalletBalance(playerTransactionVo, getAgentPrefix(userAgent.getId()));
        Set<String> keys = accountToBalanceMap.keySet();
        Map<String, Set<BalancesResult>> balanceSetMap = new HashMap<>();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String userAccount = iterator.next();
            BalancesResult balancesResult = new BalancesResult();
            balancesResult.setBalance(accountToBalanceMap.get(userAccount));
            Set<BalancesResult> balaneSet = new HashSet<>();
            balaneSet.add(balancesResult);
            balanceSetMap.put(userAccount, balaneSet);
            result.setData(balanceSetMap);
        }
        return result;
    }

    @Override
    public boolean validatePreConditions(Params params) {

        FetchBalanceParams fetchBalanceParams = (FetchBalanceParams)params;
        if (CollectionTool.isNotEmpty(fetchBalanceParams.getAccounts())) {
            return true;
        } else {
            return false;
        }
    }

}
