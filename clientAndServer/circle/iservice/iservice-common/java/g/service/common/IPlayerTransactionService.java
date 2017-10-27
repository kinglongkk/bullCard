package g.service.common;


import g.model.player.po.PlayerTransaction;
import g.model.player.vo.PlayerTransactionListVo;
import g.model.player.vo.PlayerTransactionVo;
import org.soul.iservice.support.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * 玩家交易表服务接口
 * <p/>
 * Created by cheery using soul-code-generator on 2015-6-23 11:41:42
 */
public interface IPlayerTransactionService extends IBaseService<PlayerTransactionListVo, PlayerTransactionVo, PlayerTransaction, Long> {

    //region your codes
    /**
     * 存款生成待确认转账订单(预存款)
     * 存款参数：PlayerTransactionVo.depositParam
     * @param playerTransactionVo
     * @return
     */
    PlayerTransactionVo depositTransaction(PlayerTransactionVo playerTransactionVo);

    /**
     * 取款生成待确认转账订单（预取款）
     * 存款参数：PlayerTransactionVo.depositParam
     * @param playerTransactionVo
     * @return
     */
    PlayerTransactionVo withdrawTransaction(PlayerTransactionVo playerTransactionVo);

    /**
     * 确认转账
     * 订单号：search.transactionNo
     * @param playerTransactionVo
     * @return 账户余额：playerTransactionVo.amount
     */
    PlayerTransactionVo confirmTransaction(PlayerTransactionVo playerTransactionVo);

    /**
     * 返回转账状态
     * 订单号：search.transactionNo
     * @param playerTransactionVo
     * @return
     */
    PlayerTransactionVo returnTransactionStatus(PlayerTransactionVo playerTransactionVo);

    /**
     * 获取用户钱包余额
     * @return
     */
    Map<String, Double> queryWalletBalance(PlayerTransactionVo playerTransactionVo, String agentPrefix);

    /**
     * 活取转账结果
     *
     * 订单号：search.transactionNo
     * @param playerTransactionVo
     * @return
     */
    String transactionStatus(PlayerTransactionVo playerTransactionVo);


    /**
     * 玩家账目记录
     * @param listVo
     * @return
     */
    List<Map<String, Object>> selectPlayerAccountRecord(PlayerTransactionListVo listVo);

    /**
     * 修改玩家交易状态
     * 服务端拒绝操作
     * @param playerTransaction
     * @return
     */
    boolean updatePlayerTransactionStatus(PlayerTransaction playerTransaction);

    /**
     * 通过交易号查询交易信息
     * @param transactionNo
     * @return
     */
    List<PlayerTransaction> selectByTransactionNo(String transactionNo);
    //endregion your codes

}