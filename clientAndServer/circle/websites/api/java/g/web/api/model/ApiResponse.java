package g.web.api.model;

import org.soul.model.gameapi.base.ContextEnvelope;

import java.util.Map;

/**
 * Created by tony on 2016/11/24.
 * 公共返回数据类
 */
public class ApiResponse extends ContextEnvelope {

    /**
     * 请求结果
     */
    private Object result;

    /**
     * 结果代码
     */
    private String code;

    /**
     * 结果代码信息
     */
    private String codeDesc;

    /**
     * 错误提示信息
     */
    private String errorDesc;

    /**
     * 时间戳
     */
    private String timestamp = String.valueOf(System.currentTimeMillis());

    /**
     * 附加结果
     */
    private Map<String, ?> additionalResult;
    /**
     * 附加的参数，此参数由调用者放入，然后在返回时会原样返回。
     */
    private Map<String,Object> attachParams;


    public String getCodeDesc() {
        return codeDesc;
    }

    public void setCodeDesc(String codeDesc) {
        this.codeDesc = codeDesc;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    public Map<String, ?> getAdditionalResult() {
        return additionalResult;
    }

    public void setAdditionalResult(Map<String, ?> additionalResult) {
        this.additionalResult = additionalResult;
    }

    public Map<String, Object> getAttachParams() {
        return attachParams;
    }

    public void setAttachParams(Map<String, Object> attachParams) {
        this.attachParams = attachParams;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
