package g.model.api.param;

/**
 * 游戏充值记录查询参数
 * Created by jin on 2017/01/09.
 */
public class AccountsSelectParams extends PageParams {

    /**
     * 游戏充值记录
     */
    private String gameType;

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }
}
