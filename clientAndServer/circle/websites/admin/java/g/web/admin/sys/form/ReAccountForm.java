package g.web.admin.sys.form;

import org.hibernate.validator.constraints.Range;
import org.soul.web.support.IForm;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

/**
 * @author: tom
 * @date: 16-6-7
 */
public class ReAccountForm implements IForm {

    private String resultInfo_hhostNew;
    private String resultInfo_hclientNew;
    private String resultInfo_fhostNew;
    private String resultInfo_fclientNew;

    @NotNull
    @Range(max = 200,min = 0)
    @Digits(integer = 3,fraction = 0)
    public String getResultInfo_hhostNew() {
        return resultInfo_hhostNew;
    }

    public void setResultInfo_hhostNew(String resultInfo_hhostNew) {
        this.resultInfo_hhostNew = resultInfo_hhostNew;
    }

    @NotNull
    @Range(max = 200,min = 0)
    @Digits(integer = 3,fraction = 0)
    public String getResultInfo_hclientNew() {
        return resultInfo_hclientNew;
    }

    public void setResultInfo_hclientNew(String resultInfo_hclientNew) {
        this.resultInfo_hclientNew = resultInfo_hclientNew;
    }

    @NotNull
    @Range(max = 200,min = 0)
    @Digits(integer = 3,fraction = 0)
    public String getResultInfo_fhostNew() {
        return resultInfo_fhostNew;
    }

    public void setResultInfo_fhostNew(String resultInfo_fhostNew) {
        this.resultInfo_fhostNew = resultInfo_fhostNew;
    }

    @NotNull
    @Range(max = 200,min = 0)
    @Digits(integer = 3,fraction = 0)
    public String getResultInfo_fclientNew() {
        return resultInfo_fclientNew;
    }

    public void setResultInfo_fclientNew(String resultInfo_fclientNew) {
        this.resultInfo_fclientNew = resultInfo_fclientNew;
    }
}
