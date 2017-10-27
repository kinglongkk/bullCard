package g.web.player.model;


public class ResultData {

    //消息
    private String msg;
    //状态 0 失败 1 成功
    private int code;
    //数据
    private Object data;
    //游戏登陆标识
    private String token;
    //用户id
    private int uid;

    public ResultData() {
    }

    public ResultData(Object data) {
        this.msg = "";
        this.code = 1;
        this.token = "";
        this.data = data;
    }

    public ResultData(String token){
        this.msg = "";
        this.code = 1;
        this.token = token;
        this.data = null;
    }

    public ResultData(String msg, int code){
        this.msg = msg;
        this.code = code;
        this.token = "";
        this.data = null;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
