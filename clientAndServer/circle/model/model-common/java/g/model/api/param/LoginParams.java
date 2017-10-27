package g.model.api.param;

/**
 * 玩家登陆参数
 * Created by tony on 2016/11/10.
 */
public class LoginParams extends Params {

    /**
     * 游戏一级分类
     * 返回大厅链接，用于第三方游戏界面上的返回大厅按钮点击调用
     */
    private String lobbyUrl;
    /**
     * 充值链接，用于第三方游戏界面上充值按钮跳转到gameBox平台进行充值
     */
    private String bankingUrl;

    public String getLobbyUrl() {
        return lobbyUrl;
    }

    public void setLobbyUrl(String lobbyUrl) {
        this.lobbyUrl = lobbyUrl;
    }

    public String getBankingUrl() {
        return bankingUrl;
    }

    public void setBankingUrl(String bankingUrl) {
        this.bankingUrl = bankingUrl;
    }
}
