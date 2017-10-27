package g.model.common;

import org.soul.commons.dict.IDictEnum;
import org.soul.commons.support.IModule;

/**
 * 审核状态
 * <p/>
 * Created by tom on 15-6-25.
 */
public enum CheckStatusEnum implements IDictEnum {
    WITHOUT_REVIEW("without_review", "无需审核"),
    PENDING("pending", "待审核"),
    SUCCESS("success", "审核通过"),
    FAILURE("failure","审核失败"),
    REJECT("reject", "已驳回");

    private final String code;
    private String desc;

    private CheckStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public IModule getModule() {
        return null;
    }

    @Override
    public String getType() {
        return null;
    }
}
