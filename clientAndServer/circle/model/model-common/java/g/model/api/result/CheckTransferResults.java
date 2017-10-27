package g.model.api.result;

/**
 * 检查转账状态返回结果
 * Created by black on 2016/12/2.
 */
public class CheckTransferResults extends SelfResults {

    /**
     * 订单状态
     */
    private String orderStatus;

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

}
