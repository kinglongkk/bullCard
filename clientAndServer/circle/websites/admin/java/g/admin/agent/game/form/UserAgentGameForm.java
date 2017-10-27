package g.admin.agent.game.form;

import g.web.admin.consts.FormValidRegExps;
import org.soul.web.support.IForm;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


/**
 * 代理游戏表单验证对象
 *
 * @author black
 * @time 2016-12-5 12:01:04
 */
public class UserAgentGameForm implements IForm {


    /**
     * 游戏返回链接
     */
    private String result_gameLink;
    /**
     * 游戏id
     */
    private Integer result_gameId;

    @NotNull
    @Pattern(regexp = FormValidRegExps.URL_LASTPART, message = "common.URL_LASTPART")
    public String getResult_gameLink() {
        return result_gameLink;
    }
    public void setResult_gameLink(String result_gameLink) {
        this.result_gameLink = result_gameLink;
    }

    @NotNull
    public Integer getResult_gameId() {
        return result_gameId;
    }
    public void setResult_gameId(Integer result_gameId) {
        this.result_gameId = result_gameId;
    }
}