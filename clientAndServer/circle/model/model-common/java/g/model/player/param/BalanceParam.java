package g.model.player.param;

import java.util.List;

/**
 * Created by jerry on 16-4-5.
 */
public class BalanceParam extends WalletParam {

    private String subsysCode;

    public BalanceParam(List<String> accountList,Integer ownerId) {
        super.accountList = accountList;
        super.ownerId=ownerId;
    }

    public BalanceParam(List<String> accountList,Integer ownerId,String subsysCode) {
        super.accountList = accountList;
        super.ownerId=ownerId;
        this.subsysCode = subsysCode;
    }

    public String getSubsysCode() {
        return subsysCode;
    }

    public void setSubsysCode(String subsysCode) {
        this.subsysCode = subsysCode;
    }
}
