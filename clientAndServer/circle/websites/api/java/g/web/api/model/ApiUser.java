package g.web.api.model;

/**
 * api User参数
 * Created by black on 2016/11/15.
 */
public class ApiUser {

    /**
     * 在游戏登录帐号,只能由5-16个英文、数字组成
     */
    private String account;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
