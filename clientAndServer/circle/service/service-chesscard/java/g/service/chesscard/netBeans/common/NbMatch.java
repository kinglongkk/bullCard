package g.service.chesscard.netBeans.common;

import g.model.match.po.Match;
import g.service.chesscard.engine.EngineConst;
import g.service.webSocket.codeCreator.NetBeanField;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.soul.commons.lang.DateTool;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;

/**
 * Created by lenovo on 2016/9/21.
 * 游戏局的网络协议
 */
public class NbMatch {

    private static Log log = LogFactory.getLog(NbMatch.class);

    @Override
    public String toString() {
        return "\n" + ToStringBuilder.reflectionToString(this);
    }

    /**
     * 游戏ID
     */
    @NetBeanField
    public long matchId;

    /**
     * 下一场开始时间
     */
    @NetBeanField
    public long beginTimeNext;

    /**
     * 本次结算时间
     */
    @NetBeanField
    public long settleTime;

    /**
     * 庄家信息
     */
    @NetBeanField
    public NbDealer dealer = new NbDealer();

    public static NbMatch fromMatch(Match match) {
        NbMatch nbMatch = new NbMatch();
        if (match == null || match.getId() == null) {
            log.error("", match);
        }
        nbMatch.matchId = match.getId();
        nbMatch.settleTime = match.getEndTime().getTime();
        nbMatch.beginTimeNext = match.getBeginTimeNext().getTime();
        return nbMatch;
    }
}
