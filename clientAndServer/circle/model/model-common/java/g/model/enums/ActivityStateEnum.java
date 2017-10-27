package g.model.enums;

import org.soul.commons.enums.ICodeEnum;

/**
 * 优惠活动活动状态
 */
public enum ActivityStateEnum implements ICodeEnum {
    PROCESSING("processing","进行中"),
    NOTSTARTED("notStarted","未开始"),
    FINISHED("finished", "已过期"),
    DRAFT("draft","草稿"),
    RELEASE("release","发布"),
    PENDING("1","待处理"),
    SUCCESS("2","成功"),
    FAIL("3","失败");


    private String code;
    private String trans;

    ActivityStateEnum(String code,String trans) {
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
}
