package g.model.api.param;

/**
 * 玩家注册参数
 * Created by black on 2016/12/1.
 */
public class RegisterParams extends Params {

    /**
     * 注册昵称
     */
    private String nickname;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

}

