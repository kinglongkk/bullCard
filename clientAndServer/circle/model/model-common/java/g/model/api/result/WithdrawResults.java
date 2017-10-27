package g.model.api.result;

/**
 * 玩家取款返回结果
 * Created by black on 2016/12/2.
 */
public class WithdrawResults extends SelfResults {

    /**
     * 交易金额
     */
    private Double transactionAmount;
    /**
     * 内部交易号
     */
    private String transactionNo;

    public Double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }

}
