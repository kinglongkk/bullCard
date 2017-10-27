package g.model.common;

import org.soul.commons.enums.EnumTool;
import org.soul.commons.enums.ICodeEnum;

/**订单类型
 * Created by mark on 9/9/15.
 */
public enum OrderTypeEnum implements ICodeEnum {

	/**
	 * 充值订单
	 */
	RECHARGE("01","order_id_recharge_seq"),
	/**
	 * 提现订单
	 */
	WITHDRAW("02","order_id_withdraw_seq"),
	/**
	 * 转入订单
	 */
	TRANSFERS_INTO("03", "order_id_transfersInto_seq"),
	/**
	 * 转出订单
	 */
	TRANSFERS_OUT("04", "order_id_transfersOut_seq"),
	/**
	 * 派彩订单
	 */
	PROFIT("05","order_id_profit_seq");


	/**
	 *订单类型
	 */
	private String code;
	/**
	 * 订单类型对应的数据库序列名称
	 */
	private String trans;

	OrderTypeEnum(String code, String trans){
		this.code = code;
		this.trans = trans;
	}

	@Override
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String getTrans() {
		return trans;
	}

	public void setTrans(String trans) {
		this.trans = trans;
	}

	public static OrderTypeEnum enumOf(String code) {
		return EnumTool.enumOf(OrderTypeEnum.class, code);
	}
}