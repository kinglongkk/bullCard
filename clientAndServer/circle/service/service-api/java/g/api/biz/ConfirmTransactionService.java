package g.api.biz;

import g.api.base.BaseService;
import g.api.service.IGameConfirmTransactionService;
import g.model.TransactionStatusEnum;
import g.model.admin.agent.message.po.UserAgent;
import g.model.api.enums.ResultStatusEnums;
import g.model.api.param.ConfirmTransactionParams;
import g.model.api.param.Params;
import g.model.api.result.ConfirmTransactionResults;
import g.model.api.result.Results;
import g.model.player.po.PlayerTransaction;
import g.model.player.vo.PlayerTransactionListVo;
import g.model.player.vo.PlayerTransactionVo;
import g.service.common.IPlayerTransactionService;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.query.Criterion;
import org.soul.commons.query.enums.Operator;
import org.soul.model.security.privilege.po.SysUser;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 确认转账
 * Created by black on 2016/11/18.
 */
public class ConfirmTransactionService extends BaseService implements IGameConfirmTransactionService {

    @Autowired
    private IPlayerTransactionService playerTransactionService;

    @Override
    public Results confirm(ConfirmTransactionParams params) {

        ConfirmTransactionResults result = new ConfirmTransactionResults();
        if (!validatePreConditions(params)) {
            throwBusinessException(ResultStatusEnums.VALIDATE_PARAMS_FAILED);
        }
        UserAgent userAgent = getUserAgent(params.getMerchantNo());
        if (userAgent == null) {
            throwBusinessException(ResultStatusEnums.AGENT_NOT_EXISTS);
        }
        SysUser player = getPlayer(getPlayerAccount(userAgent.getId(), params.getUserAccount()), userAgent.getAgentId());
        if (player == null) {
            throwBusinessException(ResultStatusEnums.USER_NOT_EXIST);
        }
        PlayerTransactionListVo listVo = new PlayerTransactionListVo();
        listVo.getQuery().setCriterions(new Criterion[]{

                new Criterion(PlayerTransaction.PROP_TRANSACTION_NO, Operator.EQ, params.getTransactionNo()),
                new Criterion(PlayerTransaction.PROP_PLAYER_ID, Operator.EQ, player.getId())
        });
        listVo = playerTransactionService.search(listVo);
        if (listVo.getResult().size() != 1){
            throwBusinessException(ResultStatusEnums.PLATFORM_TRANS_ID_NOT_EXIST);
        }
        if (!listVo.getResult().get(0).getStatus().equals(TransactionStatusEnum.PENDING.getCode())){
            if (listVo.getResult().get(0).getStatus().equals(TransactionStatusEnum.WAITING.getCode())) {
                throwBusinessException(ResultStatusEnums.WAITING_HANDLING);
            }
            throwBusinessException(ResultStatusEnums.REFUSE_OPERATION);
        }
        PlayerTransactionVo playerTransactionVo = new PlayerTransactionVo();
        playerTransactionVo.setSysUser(player);
        playerTransactionVo.setSourceOrderNo(params.getTransactionNo());
        playerTransactionVo = playerTransactionService.confirmTransaction(playerTransactionVo);
        if (!playerTransactionVo.isSuccess()){
            throwBusinessException(ResultStatusEnums.OTHER_ERR);
        }
        return result;
    }

    @Override
    public boolean validatePreConditions(Params params) {

        ConfirmTransactionParams confirmTransactionParams = (ConfirmTransactionParams)params;
        if (StringTool.isNotEmpty(confirmTransactionParams.getUserAccount())) {
            return true;
        }else {
            return false;
        }
    }

}
