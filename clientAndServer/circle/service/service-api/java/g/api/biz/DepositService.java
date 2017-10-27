package g.api.biz;

import g.api.base.BaseService;
import g.api.service.IGameDepositService;
import g.api.service.IGamePlayerTransactionService;
import g.common.tool.DoubleTool;
import g.model.admin.agent.message.po.UserAgent;
import g.model.api.enums.ResultStatusEnums;
import g.model.api.param.DepositParams;
import g.model.api.param.Params;
import g.model.api.result.DepositResults;
import g.model.api.result.Results;
import g.model.player.po.PlayerTransfer;
import g.model.player.vo.PlayerTransactionVo;
import g.model.player.vo.PlayerTransferListVo;
import g.service.common.IPlayerTransactionService;
import g.service.player.IPlayerTransferService;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.query.Criterion;
import org.soul.commons.query.enums.Operator;
import org.soul.model.security.privilege.po.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

public class DepositService extends BaseService implements IGameDepositService {

    @Autowired
    private IPlayerTransactionService playerTransactionService;

    @Autowired
    private IPlayerTransferService playerTransferService;

    @Autowired
    private IGamePlayerTransactionService transactionService;

    @Override
    @Transactional
    public Results deposit(DepositParams params) {

        DepositResults result = new DepositResults();
        if (!validatePreConditions(params)) {
            throwBusinessException(ResultStatusEnums.VALIDATE_PARAMS_FAILED);
        }
        UserAgent userAgent = getUserAgent(params.getMerchantNo());
        if (userAgent == null) {
            throwBusinessException(ResultStatusEnums.AGENT_NOT_EXISTS);
        }
        PlayerTransferListVo listVo = new PlayerTransferListVo();
        listVo.getQuery().setCriterions(new Criterion[]{

            new Criterion(PlayerTransfer.PROP_SOURCE_ORDER_NO, Operator.EQ, params.getApiTranId())
        });
        listVo = playerTransferService.search(listVo);
        if (listVo.getResult().size() > 0){
            throwBusinessException(ResultStatusEnums.TRAN_ID_HAS_FINSHED);
        }
        SysUser player = getPlayer(getPlayerAccount(userAgent.getId(), params.getUserAccount()), userAgent.getAgentId());
        if (player == null) {
            throwBusinessException(ResultStatusEnums.USER_NOT_EXIST);
        }
        PlayerTransactionVo playerTransactionVo = new PlayerTransactionVo();
        playerTransactionVo.setSysUser(player);
        playerTransactionVo.setAmount(DoubleTool.doubleFormat(DoubleTool.mul(params.getAmount(), 100), 2));
        playerTransactionVo.setSourceOrderNo(params.getApiTranId());
        //玩家交易表
        playerTransactionVo = this.playerTransactionService.depositTransaction(playerTransactionVo);
        if (!playerTransactionVo.isSuccess() || playerTransactionVo.getResult() == null ||
                StringTool.isEmpty(playerTransactionVo.getResult().getTransactionNo())) {
            throwBusinessException(ResultStatusEnums.OTHER_ERR);
        }
        //玩家转账表
        Map map = new HashMap();
        map.put("sourceOrderNo", playerTransactionVo.getSourceOrderNo());
        map.put("transactionId", playerTransactionVo.getResult().getId());
        map.put("agentId", userAgent.getAgentId());
        map.put("playerId", player.getId());
        map.put("amount", playerTransactionVo.getAmount());
        map.put("createTime", playerTransactionVo.getResult().getCreateTime());
        boolean isSuccess = playerTransferService.insertNewRecord(map);
        if (!isSuccess){
            throwBusinessException(ResultStatusEnums.OTHER_ERR);
        }
        result.setTransactionAmount(playerTransactionVo.getAmount());
        result.setTransactionNo(playerTransactionVo.getResult().getTransactionNo());
        //玩家预取款信息通知服务端
        transactionService.sendTransactionMessage(playerTransactionVo.getResult());
        return result;
    }

    @Override
    public boolean validatePreConditions(Params params) {

        DepositParams depositParams = (DepositParams) params;
        if (StringTool.isNotEmpty(depositParams.getUserAccount()) && depositParams.getAmount() > 0 &&
                depositParams.getApiTranId().length() <= 32) {
            return true;
        }else {
            return false;
        }
    }

}
