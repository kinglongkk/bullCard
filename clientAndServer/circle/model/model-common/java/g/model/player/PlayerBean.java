package g.model.player;

import org.soul.model.passport.vo.PassportVo;

import java.io.Serializable;

/**
 * Created by longer on 6/13/16.
 */
public class PlayerBean implements Serializable {

    private static final long serialVersionUID = 3221514750872038505L;

    private PassportVo passportVo;
    private long timestamp;

    public PassportVo getPassportVo() {
        return passportVo;
    }

    public void setPassportVo(PassportVo passportVo) {
        this.passportVo = passportVo;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
