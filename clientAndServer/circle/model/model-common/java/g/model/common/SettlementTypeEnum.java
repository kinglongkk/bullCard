package g.model.common;

import org.soul.commons.enums.EnumTool;
import org.soul.commons.enums.ICodeEnum;
/**结算账单类型
 * Created by mark on 9/9/15.
 */
public enum SettlementTypeEnum implements ICodeEnum {

	/**
	 * 游戏API与总控的结算
	 */
	API_AND_BOSS("01","settlement_id_api_seq"),

	/**
	 * 总控与运营商的结算
	 */
	BOSS_AND_COMPANY("02","settlement_id_boss_seq"),

	/**
	 * 运营商与站长的结算
	 */
	COMPANY_AND_MASTER("03","settlement_id_company_seq"),

	/**
	 * 站长与总代的结算
	 */
	MASTER_AND_GENERALAGENT("04","settlement_id_generalagent_seq"),

	/**
	 * 站长与代理的结算
	 */
	MASTER_AND_AGENT("05","settlement_id_agent_seq"),
	;

	/**
	 *结算账单类型
	 */
	private String code;
	/**
	 * 结算账单类型对应的数据库序列名称
	 */
	private String trans;

	SettlementTypeEnum(String code, String trans){
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

	public static SettlementTypeEnum enumOf(String code) {
		return EnumTool.enumOf(SettlementTypeEnum.class, code);
	}
}