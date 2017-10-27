package g.api.biz;

import g.api.base.BaseService;
import g.api.service.IGamePlayerTransactionService;
import g.api.service.IGameWithdrawService;
import g.common.tool.DoubleTool;
import g.model.QuotaStatusEnum;
import g.model.SubSysCodeEnum;
import g.model.admin.agent.message.po.UserAgent;
import g.model.api.enums.ResultStatusEnums;
import g.model.api.param.Params;
import g.model.api.param.WithdrawParams;
import g.model.api.result.Results;
import g.model.api.result.WithdrawResults;
import g.model.player.param.BalanceParam;
import g.model.player.po.PlayerTransaction;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WithdrawService extends BaseService implements IGameWithdrawService {

    @Autowired
    private IPlayerTransactionService playerTransactionService;

    @Autowired
    private IPlayerTransferService playerTransferService;

    @Autowired
    private IGamePlayerTransactionService transactionService;

    @Override
    @Transactional
    public Results withdraw(WithdrawParams params) {

        WithdrawResults result = new WithdrawResults();
        if (!validatePreConditions(params)) {
            throwBusinessException(ResultStatusEnums.VALIDATE_PARAMS_FAILED);
        }
        UserAgent userAgent = getUserAgent(params.getMerchantNo());
        if (userAgent == null) {
            throwBusinessException(ResultStatusEnums.AGENT_NOT_EXISTS);
        }
        if (userAgent.getQuotaStatus().equals(QuotaStatusEnum.ARREARAGE.getCode())) {
            throwBusinessException(ResultStatusEnums.AGENT_HAS_ARREARAGE);
        }
        String playerAccount = getPlayerAccount(userAgent.getId(), params.getUserAccount());
        SysUser player = getPlayer(playerAccount, userAgent.getAgentId());
        if (player == null) {
            throwBusinessException(ResultStatusEnums.USER_NOT_EXIST);
        }
        PlayerTransferListVo listVo = new PlayerTransferListVo();
        listVo.getQuery().setCriterions(new Criterion[]{
                new Criterion(PlayerTransfer.PROP_SOURCE_ORDER_NO, Operator.EQ, params.getApiTranId())
        });
        listVo = playerTransferService.search(listVo);
        if (listVo.getResult().size() > 0) {
            throwBusinessException(ResultStatusEnums.TRAN_ID_HAS_FINSHED);
        }
        //判断余额
        PlayerTransactionVo playerTransactionVo = new PlayerTransactionVo();
        playerTransactionVo.setSysUser(player);
        List<String> account = new ArrayList<>();
        account.add(params.getUserAccount());
        BalanceParam param = new BalanceParam(account, userAgent.getAgentId(), SubSysCodeEnum.PLAYER.getCode());
        playerTransactionVo.setBalanceParam(param);
        Map<String, Double> balanceMap = playerTransactionService.queryWalletBalance(playerTransactionVo, getAgentPrefix(userAgent.getId()));
        Double balance = balanceMap.get(params.getUserAccount());
        if (balance < DoubleTool.doubleFormat(DoubleTool.mul(params.getAmount(), 100), 2)) {
            throwBusinessException(ResultStatusEnums.INSUFFICIENT_BALANCE);
        }
        playerTransactionVo.setAmount(-DoubleTool.doubleFormat(DoubleTool.mul(params.getAmount(), 100), 2));
        playerTransactionVo.setSourceOrderNo(params.getApiTranId());

        playerTransactionVo = this.playerTransactionService.withdrawTransaction(playerTransactionVo);
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
        if (!isSuccess) {
            throwBusinessException(ResultStatusEnums.OTHER_ERR);
        }
        result.setTransactionAmount(playerTransactionVo.getAmount());
        result.setTransactionNo(playerTransactionVo.getResult().getTransactionNo());
        return result;
    }

    public void sendWithdrawMessage(String transactionNo) {

        List<PlayerTransaction> result = playerTransactionService.selectByTransactionNo(transactionNo);
        if (result != null && !result.isEmpty()) {
            transactionService.sendTransactionMessage(result.get(0));
            log.info("发送信息到客户端true");
        }
    }

    @Override
    public boolean validatePreConditions(Params params) {

        WithdrawParams withdrawParam = (WithdrawParams)params;
        if (StringTool.isNotEmpty(withdrawParam.getUserAccount()) && withdrawParam.getAmount() > 0
                && withdrawParam.getApiTranId().length() <= 32) {
            return true;
        }else {
            return false;
        }
    }

}
