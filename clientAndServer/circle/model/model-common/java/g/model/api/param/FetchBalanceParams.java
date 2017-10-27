package g.model.api.param;

import java.util.List;

/**
 * 查询玩家余额参数
 * Created by black on 2016/12/2.
 */
public class FetchBalanceParams extends Params {

    /**
     * 用户列表
     */
    private List<String> accounts;

    public List<String> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<String> accounts) {
        this.accounts = accounts;
    }
}
