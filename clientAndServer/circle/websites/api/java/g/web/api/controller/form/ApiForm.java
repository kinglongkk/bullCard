package g.web.api.controller.form;

import g.model.api.param.Params;
import g.web.api.validator.ApiLegal;
import org.soul.commons.data.json.JsonTool;

/**
 * api 入口参数
 * Created by tony on 2016/11/7.
 */
@ApiLegal
public class ApiForm<T extends Params> {

    /**
     * 代理商账号
     */
    private String merchantNo;
    /**
     * 游戏id
     */
    private String merchantGameNo;
    /**
     * 签名
     */
    private String signature;
    /**
     * 时间戳
     */
    private String timestamp;
    /**
     * 具体功能的参数
     */
    private String params;

    public String getMerchantGameNo() {
        return merchantGameNo;
    }

    public void setMerchantGameNo(String merchantGameNo) {
        this.merchantGameNo = merchantGameNo;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public T getParams(Class<T> c){
        T t = JsonTool.fromJson(params,c);
        t.setMerchantNo(merchantNo);
        t.setMerchantGameNo(merchantGameNo);
        return t;
    }


}
