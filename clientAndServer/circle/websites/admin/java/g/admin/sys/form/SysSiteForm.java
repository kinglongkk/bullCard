package g.admin.sys.form;

import org.soul.web.support.IForm;

import javax.validation.constraints.NotNull;

/**
 * 我的站点表单验证对象
 * Created by black on 2016/9/12.
 */
public class SysSiteForm implements IForm {

    private String result_name;
    private String result_status;
    private String result_shortName;
    private String result_webSite;
    private String result_maintainStartTime;
    private String result_maintainEndTime;
    private String result_maintainReason;

    @NotNull
    public String getResult_name() {
        return result_name;
    }

    public void setResult_name(String result_name) {
        this.result_name = result_name;
    }

    @NotNull
    public String getResult_status() {
        return result_status;
    }

    public void setResult_status(String result_status) {
        this.result_status = result_status;
    }

    @NotNull
    public String getResult_shortName() {
        return result_shortName;
    }

    public void setResult_shortName(String result_shortName) {
        this.result_shortName = result_shortName;
    }

    @NotNull
    public String getResult_webSite() {
        return result_webSite;
    }

    public void setResult_webSite(String result_webSite) {
        this.result_webSite = result_webSite;
    }


    public String getResult_maintainStartTime() {
        return result_maintainStartTime;
    }

    public void setResult_maintainStartTime(String result_maintainStartTime) {
        this.result_maintainStartTime = result_maintainStartTime;
    }


    public String getResult_maintainEndTime() {
        return result_maintainEndTime;
    }

    public void setResult_maintainEndTime(String result_maintainEndTime) {
        this.result_maintainEndTime = result_maintainEndTime;
    }


    public String getResult_maintainReason() {
        return result_maintainReason;
    }

    public void setResult_maintainReason(String result_maintainReason) {
        this.result_maintainReason = result_maintainReason;
    }

}

