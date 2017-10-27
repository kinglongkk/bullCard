package g.model.enums;

/**
 * Created by lenovo on 2017/3/24.
 *
 * 发送消息类别的先后顺序
 * 1：第一个
 * 2：第二个
 * 以此类推
 */
public enum  SendMsgOrderEnum {
    BACKSTAGE(1,"WINGAME");

    private int order;
    private String typeName;

    SendMsgOrderEnum(int order,String typeName) {
        this.order = order;
        this.typeName = typeName;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }





}
