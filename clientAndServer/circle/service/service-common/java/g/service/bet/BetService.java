package g.service.bet;

import g.data.bet.BetDetailMapper;
import g.data.bet.BetMapper;
import g.data.player.PlayerTransactionMapper;
import g.data.player.UserPlayerMapper;
import g.data.support.GeneratIdTool;
import g.model.TransactionStatusEnum;
import g.model.TransactionTypeEnum;
import g.model.bet.po.Bet;
import g.model.bet.po.BetDetail;
import g.model.bet.so.BetSo;
import g.model.bet.vo.BetListVo;
import g.model.bet.vo.BetVo;
import g.model.player.po.PlayerTransaction;
import g.model.player.po.UserPlayer;
import org.soul.commons.lang.DateTool;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.model.error.ErrorMessage;
import org.soul.service.support.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 服务
 *
 * @author longer
 * @time Apr 26, 2016 4:56:53 PM
 */
//region your codes 1
public class BetService extends BaseService<BetMapper, BetListVo, BetVo, Bet, Long> implements IBetService {
//endregion your codes 1

    //region your codes 2
    @Autowired
    private BetMapper betMapper;

    @Autowired
    private BetDetailMapper betDetailMapper;

    @Autowired
    private UserPlayerMapper userPlayerMapper;

    @Autowired
    private PlayerTransactionMapper playerTransactionMapper;

    /**
     * 投注
     * @param vo
     */
    @Transactional
    public ErrorMessage bet(BetVo vo){
        Bet bet = vo.getResult();
        boolean insertResult = this.mapper.insert(bet);
        if (insertResult) {
            List<BetDetail> betDetailList = vo.getResult().getBetDetailList();
            for (int i=0;i< betDetailList.size(); i++) {
                betDetailList.get(i).setBetId(bet.getId());
            }
            int insertNum = this.betDetailMapper.batchInsert(betDetailList);
            if (insertNum == betDetailList.size())
                insertResult = true;
            else
               throw new RuntimeException("bet detail save error,saved size:"+insertNum+" but must save:"+betDetailList.size());
            // 扣除余额及增加玩家交易
            executeTransactions(vo, bet);
        }
        ErrorMessage rs = new ErrorMessage();
        rs.setSuccess(insertResult);
        return rs;
    }

    @Override
    public List<Bet> getBetHistory(BetListVo listVo) {
        Date endTime = listVo.getSearch().getEndTime();
        Date date = DateTool.addDays(endTime, 1);
        listVo.getSearch().setEndTime(date);
        List<Bet> betHistory = mapper.getBetHistory(listVo.getSearch());
        listVo.getSearch().setEndTime(endTime);
        return betHistory;
    }

    @Override
    public BetListVo getWag(BetListVo listVo) {
        //总条数
        long count = mapper.count(Criteria.add(Bet.PROP_SYS_USER_ID, Operator.EQ, listVo.getSearch().getSysUserId()));
        listVo.getPaging().setTotalCount(count);
        listVo.getPaging().cal();
        List<Bet> betList = mapper.selectBetAndDetail(listVo);
        listVo.setResult(betList);


        return listVo;
    }

    /**
     * 交易扣款
     * @param vo
     * @param bet
     */
    private void executeTransactions(BetVo vo, Bet bet) {
        Integer sysUserId = vo.getSysUserId();
        UserPlayer userPlayer = this.userPlayerMapper.get(sysUserId);
        // 再次判断余额是否足够
        if (userPlayer.getWalletBalance() < vo.getResult().getSingleAmount() )
            throw new RuntimeException("walletbalance is not enough");
        userPlayer.setWalletBalance(userPlayer.getWalletBalance() - vo.getResult().getSingleAmount());
        this.userPlayerMapper.updateOnly(userPlayer, UserPlayer.PROP_WALLET_BALANCE);
        PlayerTransaction playerTransaction = new PlayerTransaction();
        playerTransaction.setTransactionNo(GeneratIdTool.genTransactionNoForBet(bet.getBetNo()));
        playerTransaction.setCreateTime(new Date());
        playerTransaction.setTransactionType(TransactionTypeEnum.BET.getCode());
        playerTransaction.setTransactionMoney(vo.getResult().getSingleAmount());
        playerTransaction.setBalance(userPlayer.getWalletBalance());
        playerTransaction.setStatus(TransactionStatusEnum.SUCCESS.getCode());
        playerTransaction.setPlayerId(sysUserId);
        playerTransaction.setSourceId(bet.getId());
        playerTransaction.setRemark(TransactionTypeEnum.BET.getDesc());
        this.playerTransactionMapper.insert(playerTransaction);
    }

    @Transactional
    @Override
    public void bets(List<BetVo> betVos) {
        for (BetVo betVo : betVos) {
            bet(betVo);
        }
    }

    @Override
    public List<Bet> oneMatchOneUserBetTypesAmountSum(BetVo betVo) {
        return mapper.oneMatchOneUserBetTypesAmountSum(betVo);
    }

    @Override
    public Long[] getBetIds(int size) {
        return betMapper.getBetIds(size).getIds();
    }

    @Override
    public Double getProfitAmount(BetSo so) {
       return this.mapper.getProfitAmount(so);
    }

    //endregion your codes 2

}