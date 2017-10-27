package g.data.bet.impl;

import g.model.bet.po.Bet;
import g.model.bet.po.BetDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Double on 5/26/16.
 *
 * 基于内存的注单投交
 */
public class MemoryBetDao extends BetDao{

    /**
     *插入注单表
     */
    static String sqlBet = "INSERT INTO bet (id,sys_user_id, r_type, bet_no, order_type, single_amount, " +
                    "profit_amount, effective_amount, can_win, status, settle_status, result, is_deleted, strong, bet_time, " +
                    "confirm_time, settle_time, update_user, update_time, delete_user, delete_time, ball_type, phase,water_amount) " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    /**
     * 插入注单详细表
     */
    static String sqlBetDetail = "INSERT INTO bet_detail (match_id, bet_id, bet_team, point, bet_type, " +
            "bet_team_type, ior_field, ratio, host_score, client_score) " +
            "VALUES (?,?,?,?,?,?,?,?,?,?)";

    @Override
    protected boolean isMemory(){
        return true;
    }

    @Override
    protected List<PreparedStatement> genInsertPstmt(Connection connection) throws SQLException {
        PreparedStatement pstmtSqlBet = connection.prepareStatement(sqlBet);
        PreparedStatement pstmtSqlBetDetail = connection.prepareStatement(sqlBetDetail);
        List list = new ArrayList(2);
        list.add(pstmtSqlBet);
        list.add(pstmtSqlBetDetail);
        return list;
    }

    @Override
    protected void paramInsertPstmt(List<PreparedStatement> preparedStatements ,Bet bet) throws SQLException {
        int index = 1;

//        sys_user_id, r_type, bet_no, order_type, single_amount, " +
//        "profit_amount, effective_amount, can_win, status, settle_status, result, is_deleted, strong, bet_time, " +
//                "confirm_time, settle_time, update_user, update_time, delete_user, delete_time, ball_type, phase

        PreparedStatement pstmtSqlBet = preparedStatements.get(0);
        pstmtSqlBet.setObject(index++, bet.getId());
        pstmtSqlBet.setObject(index++, bet.getSysUserId());
        pstmtSqlBet.setObject(index++, bet.getRtype());
        pstmtSqlBet.setObject(index++, bet.getBetNo());
        pstmtSqlBet.setObject(index++, bet.getOrderType());
        pstmtSqlBet.setObject(index++, bet.getSingleAmount());
        pstmtSqlBet.setObject(index++, bet.getProfitAmount());
        pstmtSqlBet.setObject(index++, bet.getEffectiveAmount());
        pstmtSqlBet.setObject(index++, bet.getCanWin());
        pstmtSqlBet.setObject(index++, bet.getStatus());
        pstmtSqlBet.setObject(index++, bet.getSettleStatus());
        pstmtSqlBet.setObject(index++, bet.getResult());
        pstmtSqlBet.setObject(index++, bet.getIsDeleted());
        pstmtSqlBet.setObject(index++, bet.getStrong());
        pstmtSqlBet.setObject(index++, new Timestamp(bet.getBetTime().getTime()));
        pstmtSqlBet.setObject(index++, new Timestamp(bet.getConfirmTime().getTime()));
        pstmtSqlBet.setObject(index++, new Timestamp(bet.getSettleTime().getTime()));
        pstmtSqlBet.setObject(index++, bet.getUpdateUser());
        pstmtSqlBet.setObject(index++, bet.getUpdateTime());
        pstmtSqlBet.setObject(index++, bet.getDeleteUser());
        pstmtSqlBet.setObject(index++, bet.getDeleteTime());
        pstmtSqlBet.setObject(index++, bet.getBallType());
        pstmtSqlBet.setObject(index++, bet.getPhase());
        pstmtSqlBet.setObject(index++, bet.getWaterAmount());
        pstmtSqlBet.addBatch();

        index = 1;
        PreparedStatement pstmtSqlBetDetail = preparedStatements.get(1);
        for (BetDetail betDetail : bet.getBetDetailList()) {
//            match_id, bet_id, bet_team, point, bet_type, " +
//            "bet_team_type, ior_field, ratio, host_score, client_score, player_result
            pstmtSqlBetDetail.setObject(index++, betDetail.getMatchId());
            //TODO: jason 此处的betId需要预先批量获取
            pstmtSqlBetDetail.setObject(index++, betDetail.getBetId());
            pstmtSqlBetDetail.setObject(index++, betDetail.getBetTeam());
            pstmtSqlBetDetail.setObject(index++, betDetail.getPoint());
            pstmtSqlBetDetail.setObject(index++, betDetail.getBetType());
            pstmtSqlBetDetail.setObject(index++, betDetail.getBetTeamType());
            pstmtSqlBetDetail.setObject(index++, betDetail.getIorField());
            pstmtSqlBetDetail.setObject(index++, betDetail.getRatio());
            pstmtSqlBetDetail.setObject(index++, betDetail.getHostScore());
            pstmtSqlBetDetail.setObject(index++, betDetail.getClientScore());
            pstmtSqlBetDetail.addBatch();
            index = 1;
        }
    }

    @Override
    protected int[] executeInsertPstmt(List<PreparedStatement> preparedStatements) throws SQLException {
        int[] rs = null;
        for (int i = 0; i < preparedStatements.size(); i++) {
            PreparedStatement preparedStatement = preparedStatements.get(i);
            if (i == 0) {
                //只需要主表的返回值
                rs = preparedStatement.executeBatch();
            } else {
                preparedStatement.executeBatch();
            }

        }
        return rs;
    }

    @Override
    protected void closeInsertPstmt(List<PreparedStatement> preparedStatements) throws SQLException {
        for (PreparedStatement preparedStatement : preparedStatements) {
            if (preparedStatement != null) {
                preparedStatement.close();
            }

        }
    }
}
