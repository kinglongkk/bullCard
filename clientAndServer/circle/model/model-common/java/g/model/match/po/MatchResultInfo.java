package g.model.match.po;

import java.io.Serializable;

/**
 * @author: tom
 * @date: 16-6-6
 */
public class MatchResultInfo implements Serializable {

    // 赛事ID
    private Long matchId;

    // hmr.host as hhost,hmr.client as hclient,mr.host as fhost,mr.client as fclient
    // 半场主队旧值
    private Integer hhost;
    // 半场客队旧值
    private Integer hclient;
    // 全场主队旧值
    private Integer fhost;
    // 全场客队旧值
    private Integer fclient;

    // 半场主队新值
    private Integer hhostNew;
    // 半场客队新值
    private Integer hclientNew;
    // 全场主队新值
    private Integer fhostNew;
    // 全场客队新值
    private Integer fclientNew;

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    public Integer getHhost() {
        return hhost;
    }

    public void setHhost(Integer hhost) {
        this.hhost = hhost;
    }

    public Integer getHclient() {
        return hclient;
    }

    public void setHclient(Integer hclient) {
        this.hclient = hclient;
    }

    public Integer getFhost() {
        return fhost;
    }

    public void setFhost(Integer fhost) {
        this.fhost = fhost;
    }

    public Integer getFclient() {
        return fclient;
    }

    public void setFclient(Integer fclient) {
        this.fclient = fclient;
    }

    public Integer getHhostNew() {
        return hhostNew;
    }

    public void setHhostNew(Integer hhostNew) {
        this.hhostNew = hhostNew;
    }

    public Integer getHclientNew() {
        return hclientNew;
    }

    public void setHclientNew(Integer hclientNew) {
        this.hclientNew = hclientNew;
    }

    public Integer getFhostNew() {
        return fhostNew;
    }

    public void setFhostNew(Integer fhostNew) {
        this.fhostNew = fhostNew;
    }

    public Integer getFclientNew() {
        return fclientNew;
    }

    public void setFclientNew(Integer fclientNew) {
        this.fclientNew = fclientNew;
    }
}
