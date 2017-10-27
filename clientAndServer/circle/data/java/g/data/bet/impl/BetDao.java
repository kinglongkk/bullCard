package g.data.bet.impl;

import g.data.bet.IBetDao;
import g.data.support.GeneratIdTool;
import g.model.TransactionStatusEnum;
import g.model.TransactionTypeEnum;
import g.model.bet.BetSettleStatus;
import g.model.bet.BetStatus;
import g.model.bet.po.Bet;
import g.model.bet.po.VBetSettle;
import g.model.bet.vo.BetVo;
import g.model.gameroom.JackpotAdd;
import org.soul.commons.collections.CollectionTool;
import org.soul.commons.lang.ArrayTool;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.soul.data.datasource.DatasourceTool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by longer on 5/26/16.
 */
public class BetDao implements IBetDao {

    private static Log log = LogFactory.getLog(BetDao.class);

    //settle begin
    private String sqlUpdateBet =
            "update bet set profit_amount=?,water_amount=?,effective_amount=?,settle_status=?,result=?,settle_time=now(),update_time=now()" +
            "where id=? and status=? and settle_status=?";

    private String sqlUpdatePlayer_memory = "update user_player set wallet_balance=wallet_balance+? where id=? ";
    private String sqlUpdateRoomJackpot = "update game_room set jackpot=jackpot+?,jackpot_overflow=jackpot_overflow+? where id=?";

    private String sqlUpdatePlayer =
            "update user_player set wallet_balance=wallet_balance+? where id=? " +
            "and EXISTS (SELECT id from bet where id=? and status=? and settle_status=?)";

    private String sqlInsertTransaction_memory =
            "insert into player_transaction(transaction_no,transaction_type,transaction_money," +
            "balance,status,player_id,source_id,create_time,completion_time) " +
            "select ?,?,?,wallet_balance,?,?,?,now(),now() " +
            "from user_player where id=? " ;

    private String sqlInsertTransaction =
            "insert into player_transaction(transaction_no,transaction_type,transaction_money," +
            "balance,status,player_id,source_id,create_time,completion_time) " +
            "select ?,?,?,wallet_balance,?,?,?,now(),now() " +
            "from user_player where id=? " +
            "and EXISTS (SELECT id from bet where id=? and status=? and settle_status=?)";
    //settle end

    //bet cancel begin
    private String sqlCancelBet = "update bet set status=?, update_time=now()" +
            "where id=? and status=? and settle_status=?";
    //bet cancel end

    /**
     * 批量结算注单
     * @param bets
     * @return
     */
    @Override
    public int[] batchSettle(List<BetVo> bets,boolean isReSettle, JackpotAdd jackpotAdd) {
        Connection connection = null;
        try {
            connection = DatasourceTool.getBaseDatasource().getConnection();
            connection.setAutoCommit(false);
            if (isReSettle){
                preSettle(connection,bets);
            }
            int[] rs = settle(connection,bets,isReSettle ? BetSettleStatus.SETTLED : BetSettleStatus.UN_SETTLE, jackpotAdd);
            connection.commit();
            return  rs;
        } catch (SQLException e) {
            LogFactory.getLog(BetDao.class).error(e);
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    log.error(e);
                }
            }
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                log.error(e);
            }
        }
        return new int[0];
    }

    @Override
    public int[] betCancel(List<VBetSettle> bets) {
        if (CollectionTool.isEmpty(bets)){
            log.warn("批量取消注单:待取消的注单号为空,无需操作.");
            return new int[0];
        }
        Connection connection = null;
        try {
            connection = DatasourceTool.getBaseDatasource().getConnection();
            connection.setAutoCommit(false);
            doCancel(connection,bets);
            connection.commit();
        } catch (SQLException e) {
            LogFactory.getLog(BetDao.class).error(e);
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    log.error(e);
                }
            }
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                log.error(e);
            }
        }
        return new int[0];
    }

    private int[] doCancel(Connection connection,List<VBetSettle> bets) throws SQLException {
        PreparedStatement pstmtCancelBet = connection.prepareStatement(sqlCancelBet);
        PreparedStatement pstmtUpdatePlayer = connection.prepareStatement(sqlUpdatePlayer);
        PreparedStatement pstmtInsertTransaction = connection.prepareStatement(sqlInsertTransaction);
        try {
            int index = 1;
            for (VBetSettle bet : bets) {
                //更新bet表
                pstmtCancelBet.setObject(index++, BetStatus.CANCLED.getCode());
                pstmtCancelBet.setObject(index++, bet.getBetId());
                pstmtCancelBet.setObject(index++, bet.getStatus());
                pstmtCancelBet.setObject(index++, bet.getSettleStatus());
                pstmtCancelBet.addBatch();
                index = 1;

                Double fee = 0.0;
                if (BetSettleStatus.SETTLED.getCode().equals(bet.getSettleStatus())){
                    fee = -bet.getProfitAmount();
                } else {
                    fee = bet.getSingleAmount();
                }
                //已結算,收回
                pstmtUpdatePlayer.setObject(index++, fee);
                pstmtUpdatePlayer.setObject(index++, bet.getSysUserId());
                pstmtUpdatePlayer.setObject(index++, bet.getBetId());
                pstmtUpdatePlayer.setObject(index++, bet.getStatus());
                pstmtUpdatePlayer.setObject(index++, bet.getSettleStatus());
                pstmtUpdatePlayer.addBatch();
                index = 1;

                //新增交易记录
                pstmtInsertTransaction.setObject(index++, GeneratIdTool.genTransactionNoForBet(bet.getBetNo()));
                pstmtInsertTransaction.setObject(index++, TransactionTypeEnum.CANCEL_BET.getCode());
                pstmtInsertTransaction.setObject(index++, fee);
                pstmtInsertTransaction.setObject(index++, TransactionStatusEnum.SUCCESS.getCode());
                pstmtInsertTransaction.setObject(index++, bet.getSysUserId());
                pstmtInsertTransaction.setObject(index++, bet.getBetId());
                pstmtInsertTransaction.setObject(index++, bet.getSysUserId());
                pstmtInsertTransaction.setObject(index++, bet.getBetId());
                pstmtInsertTransaction.setObject(index++, bet.getStatus());
                pstmtInsertTransaction.setObject(index++, bet.getSettleStatus());
                pstmtInsertTransaction.addBatch();
                index = 1;
            }
            int[] updatePlayerRs = pstmtUpdatePlayer.executeBatch();
            int[] insertTransactionRs = pstmtInsertTransaction.executeBatch();
            int[] updateBets =pstmtCancelBet.executeBatch();

            if (log.isDebugEnabled()){
                log.debug("批量结算(取消):更新bet           表,影响记录数:[{0}]", ArrayTool.toString(updateBets));
                log.debug("批量结算(取消):更新player        表,影响记录数:[{0}]", ArrayTool.toString(updatePlayerRs));
                log.debug("批量结算(取消):更新Transaction   表,影响记录数:[{0}]", ArrayTool.toString(insertTransactionRs));
            }
            return updateBets;
        } finally {
            if (pstmtCancelBet != null) {
                pstmtCancelBet.close();
            }
            if (pstmtUpdatePlayer != null) {
                pstmtUpdatePlayer.close();
            }
            if (pstmtInsertTransaction != null) {
                pstmtInsertTransaction.close();
            }
        }
    }

    /**
     * 重結前
     * @param connection
     * @param bets
     */
    private int[] preSettle(Connection connection, List<BetVo> bets) throws SQLException{
        PreparedStatement pstmtUpdatePlayer = connection.prepareStatement(sqlUpdatePlayer);
        PreparedStatement pstmtInsertTransaction = connection.prepareStatement(sqlInsertTransaction);
        try {
            int index = 1;
            for (BetVo vo : bets) {
                //更新bet表
                Bet bet = vo.getResult();
                Double fee = -vo.getSingleAmountOld() - vo.getProfitAmountOld();

                //更新余額表
                pstmtUpdatePlayer.setObject(index++, fee);
                pstmtUpdatePlayer.setObject(index++, bet.getSysUserId());
                pstmtUpdatePlayer.setObject(index++, bet.getId());
                pstmtUpdatePlayer.setObject(index++, bet.getStatus());
                pstmtUpdatePlayer.setObject(index++, bet.getSettleStatus());
                pstmtUpdatePlayer.addBatch();
                index = 1;

                //新增交易记录
                pstmtInsertTransaction.setObject(index++, GeneratIdTool.genTransactionNoForBet(bet.getBetNo()));
                pstmtInsertTransaction.setObject(index++, TransactionTypeEnum.RE_PROFIT.getCode());
                pstmtInsertTransaction.setObject(index++, fee);
                pstmtInsertTransaction.setObject(index++, TransactionStatusEnum.SUCCESS.getCode());
                pstmtInsertTransaction.setObject(index++, bet.getSysUserId());
                pstmtInsertTransaction.setObject(index++, bet.getId());
                pstmtInsertTransaction.setObject(index++, bet.getSysUserId());
                pstmtInsertTransaction.setObject(index++, bet.getId());
                pstmtInsertTransaction.setObject(index++, bet.getStatus());
                pstmtInsertTransaction.setObject(index++, bet.getSettleStatus());
                pstmtInsertTransaction.addBatch();
                index = 1;
            }

            int[] updatePlayerRs =      pstmtUpdatePlayer.executeBatch();
            int[] insertTransaction =   pstmtInsertTransaction.executeBatch();
            if (log.isDebugEnabled()){
                log.debug("批量结算:更新player        表,影响记录数:[{0}]", ArrayTool.toString(updatePlayerRs));
                log.debug("批量结算:更新Transaction   表,影响记录数:[{0}]", ArrayTool.toString(insertTransaction));
            }
            return updatePlayerRs;
        } finally {
            if (pstmtUpdatePlayer != null) {
                pstmtUpdatePlayer.close();
            }
            if (pstmtInsertTransaction != null) {
                pstmtInsertTransaction.close();
            }
        }

    }

    /**
     * 結算
     * @param connection
     * @param bets
     * @return
     * @throws SQLException
     */
    private int[] settle(Connection connection,List<BetVo> bets,BetSettleStatus betSettleStatus, JackpotAdd jackpotAdd) throws SQLException {
        PreparedStatement pstmtUpdateBet = connection.prepareStatement(sqlUpdateBet);
        PreparedStatement pstmtUpdatePlayer = connection.prepareStatement(sqlUpdatePlayer);
        PreparedStatement pstmtUpdatePlayerMemory = connection.prepareStatement(sqlUpdatePlayer_memory);
        PreparedStatement pstmtUpdateJackpot = connection.prepareStatement(sqlUpdateRoomJackpot);
        PreparedStatement pstmtInsertTransaction = connection.prepareStatement(sqlInsertTransaction);
        PreparedStatement pstmtInsertTransactionMemory = connection.prepareStatement(sqlInsertTransaction_memory);
        List<PreparedStatement> preparedStatements = genInsertPstmt(connection);

        try {
            int index = 1;
            for (BetVo vo : bets) {
                Bet bet = vo.getResult();

                if (isMemory()) {
                    if (bet.getIncBalance() != null && bet.getIncBalance() != 0.0) {
                        //更新余額表
                        pstmtUpdatePlayerMemory.setObject(index++, bet.getIncBalance());
                        pstmtUpdatePlayerMemory.setObject(index++, bet.getSysUserId());
                        pstmtUpdatePlayerMemory.addBatch();
                        index = 1;
                        if(bet.isSave()){
                            //新增交易记录
                            pstmtInsertTransactionMemory.setObject(index++, GeneratIdTool.genTransactionNoForBet(bet.getBetNo()));
                            pstmtInsertTransactionMemory.setObject(index++, TransactionTypeEnum.PROFIT.getCode());
                            pstmtInsertTransactionMemory.setObject(index++, bet.getIncBalance());
                            pstmtInsertTransactionMemory.setObject(index++, TransactionStatusEnum.SUCCESS.getCode());
                            pstmtInsertTransactionMemory.setObject(index++, bet.getSysUserId());
                            pstmtInsertTransactionMemory.setObject(index++, bet.getId());
                            pstmtInsertTransactionMemory.setObject(index++, bet.getSysUserId());
                            pstmtInsertTransactionMemory.addBatch();
                            index = 1;
                        }
                    }
                    if(bet.isSave()){
                        //使用插入,而非更新
                        paramInsertPstmt(preparedStatements,bet);
                    }
                } else {
                    if (bet.getIncBalance() != null && bet.getIncBalance() != 0.0) {
                        //更新余額表
                        pstmtUpdatePlayer.setObject(index++, bet.getIncBalance());
                        pstmtUpdatePlayer.setObject(index++, bet.getSysUserId());
                        pstmtUpdatePlayer.setObject(index++, bet.getId());
                        pstmtUpdatePlayer.setObject(index++, bet.getStatus());
                        pstmtUpdatePlayer.setObject(index++, betSettleStatus.getCode());
                        pstmtUpdatePlayer.addBatch();
                        index = 1;

                        //新增交易记录
                        pstmtInsertTransaction.setObject(index++, GeneratIdTool.genTransactionNoForBet(bet.getBetNo()));
                        pstmtInsertTransaction.setObject(index++, TransactionTypeEnum.PROFIT.getCode());
                        pstmtInsertTransaction.setObject(index++, bet.getIncBalance());
                        pstmtInsertTransaction.setObject(index++, TransactionStatusEnum.SUCCESS.getCode());
                        pstmtInsertTransaction.setObject(index++, bet.getSysUserId());
                        pstmtInsertTransaction.setObject(index++, bet.getId());
                        pstmtInsertTransaction.setObject(index++, bet.getSysUserId());
                        pstmtInsertTransaction.setObject(index++, bet.getId());
                        pstmtInsertTransaction.setObject(index++, bet.getStatus());
                        pstmtInsertTransaction.setObject(index++, betSettleStatus.getCode());
                        pstmtInsertTransaction.addBatch();
                        index = 1;
                    }

                    //更新bet表
                    pstmtUpdateBet.setObject(index++, bet.getProfitAmount());
                    pstmtUpdateBet.setObject(index++, bet.getWaterAmount());
                    pstmtUpdateBet.setObject(index++, bet.getEffectiveAmount());
                    pstmtUpdateBet.setObject(index++, bet.getSettleStatus());
                    pstmtUpdateBet.setObject(index++, bet.getResult());
                    pstmtUpdateBet.setObject(index++, bet.getId());
                    pstmtUpdateBet.setObject(index++, bet.getStatus());
                    pstmtUpdateBet.setObject(index++, betSettleStatus.getCode());
                    pstmtUpdateBet.addBatch();
                    index = 1;
                }
            }
            int[] updateBetRs;
            int[] updatePlayerRs;
            int[] insertTransaction;
            int jackpotRs = 0;
            if(jackpotAdd !=null && (jackpotAdd.getJackpotAdd() != 0 || jackpotAdd.getJackpotOverflowAdd() != 0)){
                pstmtUpdateJackpot.setObject(index++, jackpotAdd.getJackpotAdd());
                pstmtUpdateJackpot.setObject(index++, jackpotAdd.getJackpotOverflowAdd());
                pstmtUpdateJackpot.setObject(index++, jackpotAdd.getRoomId());
                index = 1;
                jackpotRs = pstmtUpdateJackpot.executeUpdate();
            }
            if (isMemory()) {
                updatePlayerRs = pstmtUpdatePlayerMemory.executeBatch();
                insertTransaction =  pstmtInsertTransactionMemory.executeBatch();
                updateBetRs = executeInsertPstmt(preparedStatements);
            } else {
                updatePlayerRs =      pstmtUpdatePlayer.executeBatch();
                insertTransaction =   pstmtInsertTransaction.executeBatch();
                updateBetRs = pstmtUpdateBet.executeBatch();
            }
            if (log.isDebugEnabled()){
                log.debug("批量结算:{0}bet           表,影响记录数:[{1}]", isMemory() ? "插入":"更新",ArrayTool.toString(updateBetRs));
                log.debug("批量结算:更新player        表,影响记录数:[{0}]", ArrayTool.toString(updatePlayerRs));
                log.debug("批量结算:更新Transaction   表,影响记录数:[{0}]", ArrayTool.toString(insertTransaction));
                log.debug("更新GameRoom表的池金额,影响记录数:[{0}]", Integer.valueOf(jackpotRs));
            }
            return updateBetRs;
        } catch (SQLException e) {
            log.error(e,"结算注单信息异常.");
            connection.rollback();
        } finally {
            if (pstmtUpdateBet != null) {
                pstmtUpdateBet.close();
            }
            if (pstmtUpdatePlayer != null) {
                pstmtUpdatePlayer.close();
            }
            if (pstmtInsertTransaction != null) {
                pstmtInsertTransaction.close();
            }
            closeInsertPstmt(preparedStatements);
        }
        return new int[0];
    }

    //----------------以下方法,为插入注单子类准备-------------
    protected boolean isMemory(){
        return false;
    }

    protected List<PreparedStatement> genInsertPstmt(Connection connection) throws SQLException{
        return null;
    }


    protected void paramInsertPstmt(List<PreparedStatement> preparedStatements , Bet bet) throws SQLException {

    }

    protected int[] executeInsertPstmt(List<PreparedStatement> preparedStatements) throws SQLException {
        return new int[0];
    }

    protected void closeInsertPstmt(List<PreparedStatement> preparedStatements) throws SQLException {

    }
    //----------------以下方法,为插入注单子类准备-------------

}
