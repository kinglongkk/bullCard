package g.model.api.result;

/**
 * Created by tony on 2016/11/25.
 * 登陆返回类
 */
public class LoginResults extends SelfResults {

    /**
     * 项目域名
     */
    private String webSite;
    /**
     * 登录时间戳
     */
    private String timestamp;
    /**
     * 登录token
     */
    private String token;

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
