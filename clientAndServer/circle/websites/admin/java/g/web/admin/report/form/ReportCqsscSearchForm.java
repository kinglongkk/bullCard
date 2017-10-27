package g.web.admin.report.form;

import org.soul.web.support.IForm;

import java.util.Date;


/**
 * 查询表单验证对象
 *
 * @author tom
 * @time 2016-8-1 15:34:21
 */
//region your codes 1
public class ReportCqsscSearchForm implements IForm {
//endregion your codes 1

    //region your codes 2

    private Date startTime;
    private String username;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    //endregion your codes 2

}