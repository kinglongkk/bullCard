package g.service.depositdesk;

import g.data.payaccount.PayAccountMapper;
import g.data.player.PlayerTransactionMapper;
import g.data.player.UserPlayerMapper;
import g.data.support.GeneratIdTool;
import g.model.CommonStatusEnum;
import g.model.SiteParamEnum;
import g.model.depositdesk.enums.RechargeStatusEnum;
import g.model.TransactionTypeEnum;
import g.model.common.OrderTypeEnum;
import g.model.enums.PayAccountType;
import g.model.payaccount.po.PayAccount;
import g.model.player.po.PlayerTransaction;
import g.model.withdrawdesk.po.PlayerWithdraw;
import org.soul.commons.lang.DateTool;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.model.pay.enums.PayResultStatus;
import org.soul.model.pay.vo.OnlinePayVo;
import org.soul.model.sys.po.SysParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.soul.service.support.BaseService;
import g.data.depositdesk.PlayerRechargeMapper;
import g.model.depositdesk.po.PlayerRecharge;
import g.model.depositdesk.vo.PlayerRechargeListVo;
import g.model.depositdesk.vo.PlayerRechargeVo;


/**
 * 玩家充值表-mark服务
 *
 * @author tom
 * @time 2016-7-14 9:27:05
 */
//region your codes 1
public class PlayerRechargeService extends BaseService<PlayerRechargeMapper, PlayerRechargeListVo, PlayerRechargeVo, PlayerRecharge, Long> implements IPlayerRechargeService {
//endregion your codes 1

    //region your codes 2
    private static final Log LOG = LogFactory.getLog(PlayerRechargeService.class);

    @Autowired
    private PlayerTransactionMapper playerTransactionMapper;
    @Autowired
    private UserPlayerMapper userPlayerMapper;
    @Autowired
    private PayAccountMapper payAccountMapper;

    @Transactional
    @Override
    public void updateRechargeStatus() {
        Map<String, String> map = new HashMap<>();
        map.put(PlayerRecharge.PROP_RECHARGE_STATUS, RechargeStatusEnum.OVER_TIME.getCode());
        Date date = DateTool.addMinutes(new Date(), -10);
        Criteria criteria = Criteria.add(PlayerRecharge.PROP_CREATE_TIME, Operator.LE, date);
        criteria.addAnd(PlayerRecharge.PROP_RECHARGE_STATUS, Operator.EQ, RechargeStatusEnum.DEAL.getCode());
        int i = this.mapper.batchUpdateProperties(criteria, map);

        map.clear();
        map.put(PlayerTransaction.PROP_STATUS, CommonStatusEnum.OVER_TIME.getCode());
        criteria = Criteria.add(PlayerTransaction.PROP_CREATE_TIME, Operator.LE, date);
        criteria.addAnd(PlayerTransaction.PROP_STATUS, Operator.EQ, CommonStatusEnum.PENDING.getCode());
        criteria.addAnd(PlayerTransaction.PROP_TRANSACTION_TYPE, Operator.EQ, TransactionTypeEnum.DEPOSIT.getCode());
        int j = this.playerTransactionMapper.batchUpdateProperties(criteria, map);

        LOG.info("定时更新充值订单状态成功,playerRecharge表影响条数为{0},playerTransaction表影响条数为{1}", i, j);
    }

    @Transactional
    @Override
    public PlayerRechargeVo savePlayerRecharge(PlayerRechargeVo playerRechargeVo) {
        PlayerRecharge playerRecharge = playerRechargeVo.getResult();
        PayAccount payAccount = playerRechargeVo.getPayAccount();
        String transactionStatus = null;
        if (payAccount.getType().equals(PayAccountType.ONLINE_ACCOUNT.getCode())) {//线上支付
            playerRecharge.setRechargeStatus(RechargeStatusEnum.PENDING_PAY.getCode());
            transactionStatus = CommonStatusEnum.PENDING_PAY.getCode();
        } else if (payAccount.getType().equals(PayAccountType.COMPANY_ACCOUNT.getCode())) {//公司入款
            transactionStatus = CommonStatusEnum.PENDING.getCode();
            playerRecharge.setRechargeStatus(RechargeStatusEnum.DEAL.getCode());
        }

        Map map = new HashMap();
        double firstRechargeAmount = playerRecharge.getRechargeAmount() + playerRechargeVo.getFirstRechargeAmount();
        map.put("firstRechargeAmount", firstRechargeAmount);
        map.put("rechargeAmount", playerRecharge.getRechargeAmount());
        map.put("userId", playerRecharge.getPlayerId());
        Double rechargeAmount = this.mapper.getRechargeAmountByUserId(map);
        if (!(rechargeAmount != null && rechargeAmount != 0)) {
            rechargeAmount = this.mapper.getRechargeAmount(map);
        }
        if (rechargeAmount - playerRecharge.getRechargeAmount() >= 1) {
            rechargeAmount = firstRechargeAmount;
        }
        playerRecharge.setRechargeAmount(rechargeAmount);
        saveRechargeAndTransaction(playerRechargeVo, transactionStatus);
        return playerRechargeVo;
    }


    /**
     * 新增存款交易订单
     *
     * @param playerRechargeVo
     * @param playerRecharge
     * @param status
     * @return
     */
    protected PlayerTransaction insertRechargeTransaction(PlayerRechargeVo playerRechargeVo, PlayerRecharge playerRecharge, String status) {
        PlayerTransaction playerTransaction = new PlayerTransaction();
        playerTransaction.setPlayerId(playerRecharge.getPlayerId());
        playerTransaction.setTransactionNo(playerRecharge.getTransactionNo());
        playerTransaction.setCreateTime(playerRecharge.getCreateTime());
        playerTransaction.setTransactionType(TransactionTypeEnum.DEPOSIT.getCode());
        playerTransaction.setStatus(status);
        playerTransaction.setSourceId(playerRecharge.getId());
        playerTransaction.setTransactionMoney(playerRecharge.getRechargeAmount());
        this.playerTransactionMapper.insertPlayerTransaction(playerTransaction);
        return playerTransaction;
    }

    /**
     * 新增存款订单
     *
     * @param playerRechargeVo
     * @return
     */
    protected PlayerRecharge insertPlayerRecharge(PlayerRechargeVo playerRechargeVo) {
        PlayerRecharge playerRecharge = playerRechargeVo.getResult();
        playerRecharge.setTransactionNo(GeneratIdTool.genTransactionNo(OrderTypeEnum.RECHARGE));
        playerRecharge.setCreateTime(new Date());
        this.mapper.insert(playerRecharge);
        return playerRecharge;
    }

    /**
     * 保存存款订单、交易订单
     *
     * @param playerRechargeVo
     * @param status
     */
    protected void saveRechargeAndTransaction(PlayerRechargeVo playerRechargeVo, String status) {
        PlayerRecharge playerRecharge = insertPlayerRecharge(playerRechargeVo);
        PlayerTransaction playerTransaction = insertRechargeTransaction(playerRechargeVo, playerRecharge, status);
        playerRecharge.setPlayerTransactionId(playerTransaction.getId());
        mapper.updateOnly(playerRecharge, PlayerRecharge.PROP_PLAYER_TRANSACTION_ID);
        playerRechargeVo.setResult(playerRecharge);
    }

    @Override
    @Transactional
    public PlayerRechargeVo handleOnlineRechargeResult(PlayerRechargeVo playerRechargeVo, OnlinePayVo onlinePayVo) {
        //查询订单
        PlayerRecharge playerRecharge = this.mapper.get(playerRechargeVo.getSearch().getId());
        if (playerRecharge == null) {
            playerRechargeVo.setSuccess(false);
            playerRechargeVo.setResult(null);
            playerRechargeVo.setErrMsg(" The order does not exist.");
            LOG.debug("无该笔存款订单！");
            return playerRechargeVo;
        }
        String rechargeStatus = playerRecharge.getRechargeStatus();
        if (!(RechargeStatusEnum.DEAL.getCode().equals(rechargeStatus) || RechargeStatusEnum.OVER_TIME.getCode().equals(rechargeStatus))) {
            LOG.debug("该笔订单已经处理过了");
            playerRechargeVo.setSuccess(false);
            playerRechargeVo.setResult(playerRecharge);
            playerRechargeVo.setErrMsg("Repeat process.");
            return playerRechargeVo;
        }
        playerRechargeVo.setResult(playerRecharge);
        //更新订单
        updateRechargeByQueryOrder(onlinePayVo, playerRechargeVo);
        return playerRechargeVo;
    }

    /**
     * 根据OnlinePayVo更新存款结果
     *
     * @param onlinePayVo
     * @param playerRechargeVo
     */
    private void updateRechargeByQueryOrder(OnlinePayVo onlinePayVo, PlayerRechargeVo playerRechargeVo) {
        PlayerRecharge playerRecharge = playerRechargeVo.getResult();
        if (PayResultStatus.SUCCESS == onlinePayVo.getPayResultStatus()) {
            playerRecharge.setCheckTime(new Date());
            playerRecharge.setRechargeStatus(RechargeStatusEnum.SUCCESS.getCode());
            updateRechargeSuccess(playerRechargeVo);
        } else if (PayResultStatus.FAIL == onlinePayVo.getPayResultStatus()) {
            playerRecharge.setRechargeStatus(RechargeStatusEnum.FAIL.getCode());
            playerRecharge.setCheckTime(new Date());
            playerRecharge.setCheckRemark(onlinePayVo.getStatusCodeStr());
            updateRechargeFailure(playerRechargeVo);
        } else {
            updateRechargeOverTime(playerRecharge);
        }
    }

    /**
     * 更新存款订单为超时
     *
     * @param playerRecharge
     */
    protected void updateRechargeOverTime(PlayerRecharge playerRecharge) {
        playerRecharge.setRechargeStatus(RechargeStatusEnum.OVER_TIME.getCode());
        int count = updatePlayerRecharge(playerRecharge);
        if (count == 1) {
            PlayerTransaction playerTransaction = new PlayerTransaction();
            playerTransaction.setId(playerRecharge.getPlayerTransactionId());
            playerTransaction.setStatus(CommonStatusEnum.OVER_TIME.getCode());
            //更新存款记录状态
            this.playerTransactionMapper.updateOnly(playerTransaction, PlayerTransaction.PROP_STATUS);
        }
    }

    /**
     * 更新player_recharge
     *
     * @param playerRecharge
     * @return
     */
    private int updatePlayerRecharge(PlayerRecharge playerRecharge) {
        if (playerRecharge.getId() == null) {
            return 0;
        }
        Criteria criteria = Criteria.add(PlayerRecharge.PROP_RECHARGE_STATUS, Operator.IN, new String[]{RechargeStatusEnum.DEAL.getCode(), RechargeStatusEnum.OVER_TIME.getCode()});
        criteria.addAnd(PlayerRecharge.PROP_ID, Operator.EQ, playerRecharge.getId());
        Map<String, Object> properties = new HashMap<>(3);
        properties.put(PlayerRecharge.PROP_RECHARGE_STATUS, playerRecharge.getRechargeStatus());
        properties.put(PlayerRecharge.PROP_CHECK_TIME, playerRecharge.getCheckTime());
        properties.put(PlayerRecharge.PROP_CHECK_REMARK, playerRecharge.getCheckRemark());
        return this.mapper.batchUpdateProperties(criteria, properties);
    }

    /**
     * 更新存款交易记录
     *
     * @param playerRecharge
     * @param status
     * @return
     */
    private int updatePlayerTransaction(PlayerRecharge playerRecharge, String status) {
        PlayerTransaction playerTransaction = new PlayerTransaction();
        playerTransaction.setId(playerRecharge.getPlayerTransactionId());
        playerTransaction.setStatus(status);
        playerTransaction.setCompletionTime(new Date());
        return this.playerTransactionMapper.updateTransaction(playerTransaction);
    }

    /**
     * 存款失败
     *
     * @param playerRechargeVo
     */
    protected void updateRechargeFailure(PlayerRechargeVo playerRechargeVo) {
        PlayerRecharge playerRecharge = playerRechargeVo.getResult();
        int count = updatePlayerRecharge(playerRecharge);
        if (count == 1) {
            //更新交易订单
            updatePlayerTransaction(playerRecharge, CommonStatusEnum.FAILURE.getCode());
        }
    }

    /**
     * 更新存款成功
     *
     * @param playerRechargeVo
     */
    protected void updateRechargeSuccess(PlayerRechargeVo playerRechargeVo) {
        PlayerRecharge playerRecharge = playerRechargeVo.getResult();
        int count = updatePlayerRecharge(playerRecharge);
        if (count == 1) {
            //更新玩家金额
            this.userPlayerMapper.updatePlayerByRecharge(playerRecharge);
            //更新交易订单
            updatePlayerTransaction(playerRecharge, CommonStatusEnum.SUCCESS.getCode());
            //更新收款账号
            updatePayAccount(playerRecharge);
        }
    }

    /**
     * 更新收款账号
     *
     * @param playerRecharge
     */
    private void updatePayAccount(PlayerRecharge playerRecharge) {
        if (playerRecharge.getPayAccountId() != null) {
            //更新线上支付收款账号
            this.payAccountMapper.updatePayAccountByOnlineRecharge(playerRecharge);
        }
    }

    @Override
    public PlayerRechargeVo searchPlayerRecharge(PlayerRechargeVo playerRechargeVo) {
        PlayerRecharge playerRecharge = this.mapper.searchPlayerRecharge(playerRechargeVo.getSearch());
        playerRechargeVo.setResult(playerRecharge);
        return playerRechargeVo;
    }


    //endregion your codes 2

}