package g.service.ai.bull.manager;

import g.model.player.po.UserPlayer;
import g.service.chesscard.RedisKeyConst;
import org.soul.commons.cache.CacheKey;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.soul.data.datasource.DatasourceTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springside.modules.nosql.redis.JedisTemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by MK on 2017/2/18.
 */
@Service
public class PlayerMng {
    private static Log log = LogFactory.getLog(PlayerMng.class);
    @Autowired
    private JedisTemplate jedisTemplateGame;

    public int addCoin(int userId, long addCoin){
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = DatasourceTool.getBaseDatasource().getConnection();
            pstmt = connection.prepareStatement("update user_player set wallet_balance=wallet_balance+?,recharge_total=recharge_total+? where id=?");
            //更新余額表
            int index = 1;
            pstmt.setObject(index++, addCoin);
            pstmt.setObject(index++, addCoin);
            pstmt.setObject(index++, userId);
            int rs = pstmt.executeUpdate();
            jedisTemplateGame.hincrBy(CacheKey.getCacheKey(RedisKeyConst.USERS, RedisKeyConst.U, userId),
                    UserPlayer.PROP_COIN, addCoin);
            return rs;
        } catch (SQLException e) {
            log.error(e);
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    log.error(e);
                }
            }
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                log.error(e);
            }
            return -1;
        }
    }
}
