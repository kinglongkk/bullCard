package g.web.agent.controller.player.form;

import org.soul.web.support.IForm;

import javax.validation.constraints.NotNull;

/**
 * Created by lenovo on 2017/1/13.
 */
public class VSysUserSearchForm  implements IForm {

    /**
     * 玩家用户名
     */
    private String search_username;
    /**
     * 玩家昵称
     */
    private String search_nickname;


    @NotNull
//    @Pattern(regexp = FormValidRegExps.URL_LASTPART, message = "common.URL_LASTPART")
    public String getSearch_username() {
        return search_username;
    }

    public void setSearch_username(String search_username) {
        this.search_username = search_username;
    }
    @NotNull
    public String getSearch_nickname() {
        return search_nickname;
    }

    public void setSearch_nickname(String search_nickname) {
        this.search_nickname = search_nickname;
    }
}
