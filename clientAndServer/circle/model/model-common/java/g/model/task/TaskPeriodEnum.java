package g.model.task;

import org.soul.commons.enums.EnumTool;
import org.soul.commons.enums.ICodeEnum;

/**
 * Created by longer on 5/27/16.
 */
public enum TaskPeriodEnum implements ICodeEnum{

    ONCE("ONCE","一次性"),
    SCHEDULE("SCHEDULE","周期,计划的");

    private String code;

    private String trans;

    TaskPeriodEnum(String code, String trans) {
        this.code = code;
        this.trans = trans;
    }
    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getTrans() {
        return trans;
    }

    public static TaskPeriodEnum enumOf(String code) {
        return EnumTool.enumOf(TaskPeriodEnum.class, code);
    }
}
