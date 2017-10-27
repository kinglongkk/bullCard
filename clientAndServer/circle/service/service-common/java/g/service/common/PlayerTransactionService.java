package g.service.common;

import g.data.player.PlayerTransactionMapper;
import g.data.player.VSysUserMapper;
import g.data.support.GeneratIdTool;
import g.model.TransactionStatusEnum;
import g.model.TransactionTypeEnum;
import g.model.common.OrderTypeEnum;
import g.model.player.po.PlayerTransaction;
import g.model.player.po.VSysUser;
import g.model.player.vo.PlayerTransactionListVo;
import g.model.player.vo.PlayerTransactionVo;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.Paging;
import org.soul.commons.query.enums.Operator;
import org.soul.service.support.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 玩家交易表服务
 * <p>
 * Created by cheery using soul-code-generator on 2015-6-23 11:41:43
 */
public class PlayerTransactionService extends BaseService<PlayerTransactionMapper, PlayerTransactionListVo, PlayerTransactionVo, PlayerTransaction, Long> implements IPlayerTransactionService {

    @Autowired
    PlayerTransactionMapper playerTransactionMapper;

    @Autowired
    VSysUserMapper vSysUserMapper;

    //确认生成存取款预转账
    private PlayerTransactionVo transaction(PlayerTransactionVo playerTransactionVo,TransactionTypeEnum transactionTypeEnum){

        playerTransactionVo.setSuccess(false);
        if (playerTransactionVo.getSysUser() == null) {

            return playerTransactionVo;
        }
        VSysUser vSysUser = vSysUserMapper.get(playerTransactionVo.getSysUser().getId());
        if (vSysUser != null) {

            PlayerTransaction playerTransaction = new PlayerTransaction();
            if (transactionTypeEnum.getCode().equals(TransactionTypeEnum.WITHDRAWALS.getCode())) {
                playerTransaction.setStatus(TransactionStatusEnum.WAITING.getCode());
            } else {
                playerTransaction.setStatus(TransactionStatusEnum.PENDING.getCode());
            }
            playerTransaction.setCreateTime(new Date());
            playerTransaction.setPlayerId(vSysUser.getId());
            playerTransaction.setBalance(vSysUser.getWalletBalance() == null ? 0 : vSysUser.getWalletBalance());
            //内部订单号
            String transactionNo = GeneratIdTool.genTransactionNo(OrderTypeEnum.TRANSFERS_INTO);
            playerTransaction.setTransactionNo(transactionNo);
            //外部订单号
            playerTransaction.setTransactionType(transactionTypeEnum.getCode());
            playerTransaction.setTransactionMoney(playerTransactionVo.getAmount());
            boolean insert = playerTransactionMapper.insert(playerTransaction);
            boolean update = playerTransactionMapper.updateFreezingFundsBalance(playerTransaction);
            playerTransactionVo.setResult(playerTransaction);
            playerTransactionVo.setSuccess(insert && update);
        }

        return playerTransactionVo;
    }
    @Override
    public PlayerTransactionVo depositTransaction(PlayerTransactionVo playerTransactionVo) {

        return this.transaction(playerTransactionVo,TransactionTypeEnum.DEPOSIT);
    }

    @Override
    public PlayerTransactionVo withdrawTransaction(PlayerTransactionVo playerTransactionVo) {

        return this.transaction(playerTransactionVo,TransactionTypeEnum.WITHDRAWALS);
    }

    @Transactional
    @Override
    public PlayerTransactionVo confirmTransaction(PlayerTransactionVo playerTransactionVo) {
        //TODO:建议加上哟规划
        String transactionNo = playerTransactionVo.getSourceOrderNo();
        playerTransactionVo.setSuccess(false);
        //订单号不符合
        if (StringTool.isBlank(transactionNo)) {
            return playerTransactionVo;
        }
        List<PlayerTransaction> search = playerTransactionMapper.search(

                Criteria.add(PlayerTransaction.PROP_TRANSACTION_NO, Operator.EQ, transactionNo)
        );
        if (search.size() > 0) {
            PlayerTransaction playerTransaction = search.get(0);
            //是预处理状态的时候才要更新
            if (TransactionStatusEnum.PENDING.getCode().equals(playerTransaction.getStatus())) {
                //更新玩家账户余额
                boolean a = playerTransactionMapper.entWalletBalance(playerTransaction);
                //更新玩家的最大充值金额
                Map map = new HashMap();
                map.put("playerId", playerTransaction.getPlayerId());
                map.put("transactionMoney", playerTransaction.getTransactionMoney());
                playerTransactionMapper.updateRechargeMaxAmount(map);
                //更新交易表处理状态
                playerTransaction.setStatus(TransactionStatusEnum.SUCCESS.getCode());
                playerTransaction.setCompletionTime(new Date());
                int updateTransaction = playerTransactionMapper.updateTransaction(playerTransaction);
                boolean isSuccess = false;
                if (updateTransaction > 0) {
                    isSuccess = true;
                }
                playerTransactionVo.setSuccess(a && isSuccess);
                VSysUser vSysUser = vSysUserMapper.get(playerTransaction.getPlayerId());
                playerTransactionVo.setAmount(vSysUser.getWalletBalance() == null ? 0 : vSysUser.getWalletBalance());
            }
        }
        return playerTransactionVo;
    }

    @Override
    public PlayerTransactionVo returnTransactionStatus(PlayerTransactionVo playerTransactionVo) {

        String transactionNo = playerTransactionVo.getSearch().getTransactionNo();
        playerTransactionVo.setSuccess(false);
        //订单号不符合
        if (StringTool.isBlank(transactionNo)) {
            return playerTransactionVo;
        }
        List<PlayerTransaction> search = playerTransactionMapper.search(

                Criteria.add(PlayerTransaction.PROP_TRANSACTION_NO, Operator.EQ, transactionNo)
        );
        if (search.size() > 0) {
            playerTransactionVo.setSuccess(true);
            playerTransactionVo.setResult(search.get(0));
        }
        return playerTransactionVo;
    }

    @Override
    public Map<String, Double> queryWalletBalance(PlayerTransactionVo playerTransactionVo, String agentPrefix) {

        Map<String, Double> stringSetHashMap = new HashMap<>();
        for (String account : playerTransactionVo.getBalanceParam().getAccountList()) {
            //根据账号获取钱包余额
            List<VSysUser> search = vSysUserMapper.search(
                    Criteria.add(VSysUser.PROP_USERNAME, Operator.EQ, agentPrefix + account)
                    .addAnd(Criteria.add(VSysUser.PROP_OWNER_ID, Operator.EQ, playerTransactionVo.getBalanceParam().getOwnerId()))
                    .addAnd(VSysUser.PROP_SUBSYS_CODE, Operator.EQ, playerTransactionVo.getBalanceParam().getSubsysCode())
            );
            if (search.size() > 0) {
                stringSetHashMap.put(account, search.get(0).getWalletBalance() == null ? 0 : search.get(0).getWalletBalance());
            }
        }
        return stringSetHashMap;
    }

    @Override
    public String transactionStatus(PlayerTransactionVo playerTransactionVo) {

        List<PlayerTransaction> search = mapper.search(
                Criteria.add(PlayerTransaction.PROP_TRANSACTION_NO, Operator.EQ, playerTransactionVo.getSearch().getTransactionNo())
        );
        if (search.size() > 0) {

            return search.get(0).getStatus();
        }
        return null;
    }

    //分页查询玩家充值提现账目
    public List<Map<String, Object>> selectPlayerAccountRecord(PlayerTransactionListVo listVo) {

        Map<String, Object> queryParams = listVo.getQueryParams();
        //分页操作
        Paging paging = listVo.getPaging();
        if (paging != null){
            paging.setTotalCount(this.mapper.selectPlayerAccountRecordNumber(queryParams));
            paging.cal();
        }
        return this.mapper.selectPlayerAccountRecord(queryParams);
    }

    public boolean updatePlayerTransactionStatus(PlayerTransaction playerTransaction) {

        return this.mapper.updatePlayerTransactionStatus(playerTransaction);
    }

    public List<PlayerTransaction> selectByTransactionNo(String transactionNo) {

        return this.mapper.selectByTransactionNo(transactionNo);
    }
}