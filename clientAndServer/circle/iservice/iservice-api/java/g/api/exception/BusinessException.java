package g.api.exception;

/**
 * Created by tony on 2016/11/25.
 * api 业务异常
 */
public class BusinessException extends RuntimeException{

    private String coce;

    private String desc;

    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getCoce() {
        return coce;
    }

    public void setCoce(String coce) {
        this.coce = coce;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public BusinessException() {
    }

    public BusinessException(String coce, String desc, String error) {
        super(error);
        this.coce = coce;
        this.desc = desc;
        this.error = error;
    }

}
