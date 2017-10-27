package g.model.player.param;

import java.io.Serializable;
import java.util.List;

/**
 * 抽象的钱包接口参数对象
 *
 * Created by jerry on 16-04-05.
 */
public class WalletParam implements Serializable {

    private static final long serialVersionUID = 19819127879880123L;

    /**
     * 上级ID
     */
    protected Integer ownerId;

    /**
     * 账户集合
     */

    protected List<String> accountList;
    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public List<String> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<String> accountList) {
        this.accountList = accountList;
    }
}
