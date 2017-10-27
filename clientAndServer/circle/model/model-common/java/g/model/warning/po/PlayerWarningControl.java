package g.model.warning.po;

import org.soul.commons.bean.IEntity;
import org.soul.model.common.Sortable;


/**
 * 玩家实时监控控制表实体
 *
 * @author lenovo
 * @time 2017-2-21 14:12:56
 */
//region your codes 1
public class PlayerWarningControl implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = 2732028252752696633L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_STATUS = "status";
	public static final String PROP_WIN_RATE1 = "winRate1";
	public static final String PROP_WIN_RATE2 = "winRate2";
	public static final String PROP_WIN_GOLD_RATE1 = "winGoldRate1";
	public static final String PROP_WIN_GOLD_RATE2 = "winGoldRate2";
	public static final String PROP_ENCHASHMENT_SUM1 = "enchashmentSum1";
	public static final String PROP_ENCHASHMENT_SUM2 = "enchashmentSum2";
	public static final String PROP_PAYOUT_TIMES1 = "payoutTimes1";
	public static final String PROP_PAYOUT_TIMES2 = "payoutTimes2";
	public static final String PROP_ENCHASHMENT_TIMES1 = "enchashmentTimes1";
	public static final String PROP_ENCHASHMENT_TIMES2 = "enchashmentTimes2";
	public static final String PROP_CREATE_USER = "createUser";
	public static final String PROP_CREATE_TIME = "createTime";
	public static final String PROP_UPDATE_USER = "updateUser";
	public static final String PROP_UPDATE_TIME = "updateTime";
	//endregion
	
	
	//region properties
	/** 主键 */
	private Integer id;
	/** 状态（10：正常生效 20：已失效） */
	private String status;
	/** 赢得金币与投注本金倍数比黄色预警 */
	private Double winRate1;
	/** 赢得金币与投注本金倍数比红色预警 */
	private Double winRate2;
	/** 赢得金币与本金的倍数比黄色预警 */
	private Double winGoldRate1;
	/** 赢得金币与本金的倍数比红色预警 */
	private Double winGoldRate2;
	/** 取现总额黄色预警 */
	private Double enchashmentSum1;
	/** 取现总额红色预警 */
	private Double enchashmentSum2;
	/** 联系派彩次数黄色预警 */
	private Double payoutTimes1;
	/** 联系派彩次数红色色预警 */
	private Double payoutTimes2;
	/** 连续取现黄色预警 */
	private Double enchashmentTimes1;
	/** 连续取现红色预警 */
	private Double enchashmentTimes2;
	/** 记录追加人 */
	private String createUser;
	/** 记录追加时间 */
	private java.util.Date createTime;
	/** 更新者 */
	private String updateUser;
	/** 更新时间 */
	private java.util.Date updateTime;
	//endregion

	
	//region constuctors
	public PlayerWarningControl(){
	}

	public PlayerWarningControl(Integer id){
		this.id = id;
	}
	//endregion


	//region getters and setters
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer value) {
		this.id = value;
	}
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String value) {
		this.status = value;
	}
	public Double getWinRate1() {
		return this.winRate1;
	}

	public void setWinRate1(Double value) {
		this.winRate1 = value;
	}
	public Double getWinRate2() {
		return this.winRate2;
	}

	public void setWinRate2(Double value) {
		this.winRate2 = value;
	}
	public Double getWinGoldRate1() {
		return this.winGoldRate1;
	}

	public void setWinGoldRate1(Double value) {
		this.winGoldRate1 = value;
	}
	public Double getWinGoldRate2() {
		return this.winGoldRate2;
	}

	public void setWinGoldRate2(Double value) {
		this.winGoldRate2 = value;
	}
	public Double getEnchashmentSum1() {
		return this.enchashmentSum1;
	}

	public void setEnchashmentSum1(Double value) {
		this.enchashmentSum1 = value;
	}
	public Double getEnchashmentSum2() {
		return this.enchashmentSum2;
	}

	public void setEnchashmentSum2(Double value) {
		this.enchashmentSum2 = value;
	}
	public Double getPayoutTimes1() {
		return this.payoutTimes1;
	}

	public void setPayoutTimes1(Double value) {
		this.payoutTimes1 = value;
	}
	public Double getPayoutTimes2() {
		return this.payoutTimes2;
	}

	public void setPayoutTimes2(Double value) {
		this.payoutTimes2 = value;
	}
	public Double getEnchashmentTimes1() {
		return this.enchashmentTimes1;
	}

	public void setEnchashmentTimes1(Double value) {
		this.enchashmentTimes1 = value;
	}
	public Double getEnchashmentTimes2() {
		return this.enchashmentTimes2;
	}

	public void setEnchashmentTimes2(Double value) {
		this.enchashmentTimes2 = value;
	}
	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String value) {
		this.createUser = value;
	}
	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String value) {
		this.updateUser = value;
	}
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(java.util.Date value) {
		this.updateTime = value;
	}
	//endregion

	//region your codes 2

	//endregion your codes 2

}