package g.api.biz;

import g.api.base.BaseService;
import g.api.service.IGameCheckTransferService;
import g.model.TransactionStatusEnum;
import g.model.admin.agent.message.po.UserAgent;
import g.model.api.enums.ResultStatusEnums;
import g.model.api.param.CheckTransferParams;
import g.model.api.param.Params;
import g.model.api.result.CheckTransferResults;
import g.model.api.result.Results;
import g.model.player.po.PlayerTransaction;
import g.model.player.vo.PlayerTransactionListVo;
import g.service.common.IPlayerTransactionService;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.query.Criterion;
import org.soul.commons.query.enums.Operator;
import org.soul.model.security.privilege.po.SysUser;
import org.springframework.beans.factory.annotation.Autowired;

public class CheckTransferService extends BaseService implements IGameCheckTransferService{

    @Autowired
    private IPlayerTransactionService playerTransactionService;

    @Override
    public Results checkTransfer(CheckTransferParams params) {

        CheckTransferResults result = new CheckTransferResults();
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
        listVo = this.playerTransactionService.search(listVo);
        if (listVo.getResult().size() != 1) {
            throwBusinessException(ResultStatusEnums.PLATFORM_TRANS_ID_NOT_EXIST);
        }
        String orderStatus = TransactionStatusEnum.FAILURE.getDesc();
        if (TransactionStatusEnum.PENDING.getCode().equals(listVo.getResult().get(0).getStatus())) {
            orderStatus = TransactionStatusEnum.PENDING.getDesc();
        } else if (TransactionStatusEnum.SUCCESS.getCode().equals(listVo.getResult().get(0).getStatus())) {
            orderStatus = TransactionStatusEnum.SUCCESS.getDesc();
        }
        result.setOrderStatus(orderStatus);
        return result;
    }

    @Override
    public boolean validatePreConditions(Params params) {

        CheckTransferParams checkTransferParams = (CheckTransferParams) params;
        if (StringTool.isNotEmpty(checkTransferParams.getUserAccount()) && StringTool.isNotEmpty(checkTransferParams.getTransactionNo())) {
            return true;
        }else {
            return false;
        }
    }

}
