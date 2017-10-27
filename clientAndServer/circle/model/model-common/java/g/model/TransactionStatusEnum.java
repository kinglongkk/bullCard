package g.model;

import org.soul.commons.dict.IDictEnum;
import org.soul.commons.support.IModule;

/**
 * 审核状态
 * <p/>
 * Created by jerry on 16-04-05.
 */
public enum TransactionStatusEnum implements IDictEnum {
    WAITING("waiting", "等待处理"),
    PENDING("pending", "预处理"),
    SUCCESS("success", "处理成功"),
    FAILURE("failure","处理失败");

    private final String code;
    private String desc;

    TransactionStatusEnum(String code, String desc) {
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
