package g.model.player;

import org.soul.commons.lang.DateTool;

import java.util.Date;

/**
 * Created by mark on 16-6-12.
 */
public class MatchResultData {

    /**联赛名称**/
    private String leauge;
    /**开赛时间**/
    private Date beginTime;
    /**主队名称**/
    private String host;

    /** 客队名称 */
    private String client;

    /**主队全场比分**/
    private String hostGM;
    /**客队全场比分**/
    private String clientGM;
    /**主队上半场比分**/
    private String hostHGM;
    /**客队上半场比分**/
    private String clientHGM;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getLeauge() {
        return leauge;
    }

    public void setLeauge(String leauge) {
        this.leauge = leauge;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getHostGM() {
        return hostGM;
    }

    public void setHostGM(String hostGM) {
        this.hostGM = hostGM;
    }

    public String getClientGM() {
        return clientGM;
    }

    public void setClientGM(String clientGM) {
        this.clientGM = clientGM;
    }

    public String getHostHGM() {
        return hostHGM;
    }

    public void setHostHGM(String hostHGM) {
        this.hostHGM = hostHGM;
    }

    public String getClientHGM() {
        return clientHGM;
    }

    public void setClientHGM(String clientHGM) {
        this.clientHGM = clientHGM;
    }

    public String[] getBeginTime() {
        String[] s = DateTool.formatDate(beginTime, "MM dd hh mm a").split(" ");
        if("12".equals(s[2])){
            s[2]="00";
        }
        if("上午".equals(s[4])){
            s[4]="a";
        }else{
            s[4]="p";
        }
        return s;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

}
