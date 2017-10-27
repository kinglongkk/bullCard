package g.model.api.param;

/**
 * 玩家预存款参数
 * Created by black on 2016/12/2
 */
public class DepositParams extends Params {

    /**
     * 存款金额
     */
    private double amount;
    /**
     * API交易号
     */
    private String apiTranId;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getApiTranId() {
        return apiTranId;
    }

    public void setApiTranId(String apiTranId) {
        this.apiTranId = apiTranId;
    }
}
