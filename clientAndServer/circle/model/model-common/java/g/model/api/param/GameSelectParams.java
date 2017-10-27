package g.model.api.param;

/**
 * 游戏列表查询参数
 * Created by black on 2016/12/19.
 */
public class GameSelectParams extends PageParams {

    /**
     * 游戏类型
     */
    private String gameType;

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }
}
