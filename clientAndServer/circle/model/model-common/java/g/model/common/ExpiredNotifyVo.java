package g.model.common;

import org.soul.commons.init.context.AbstractBaseVo;

/**
 * Created by lishengchao on 15-10-13.
 */
public class ExpiredNotifyVo extends AbstractBaseVo {
    private String sessionId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
