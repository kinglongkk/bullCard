package g.model.api.param;

/**
 * 确认转账参数
 * Created by black on 2016/12/2.
 */
public class ConfirmTransactionParams extends Params {

    /**
     * 内部交易号
     */
    private String transactionNo;

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }
}
