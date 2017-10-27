package g.model.admin.po;

import org.soul.commons.bean.IEntity;
import org.soul.model.common.Sortable;


/**
 * 代理单注限额视图实体
 *
 * @author tom
 * @time 2016-4-21 17:14:24
 */
//region your codes 1
public class VUserBetLimit implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = -410933074576572624L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_BET_TYPE = "betType";
	public static final String PROP_RTYPE = "rtype";
	public static final String PROP_BET_MIN = "betMin";
	public static final String PROP_BET_MAX = "betMax";
	public static final String PROP_ITEM_MAX = "itemMax";
	public static final String PROP_SYS_USER_ID = "sysUserId";
	public static final String PROP_SCHEMA_CODE = "schemaCode";
	public static final String PROP_ORDER_NUM = "orderNum";
	public static final String PROP_COLUMN_GROUP_CODE = "columnGroupCode";
	//endregion
	
	
	//region properties
	/**  */
	private Integer id;
	/** 玩法 */
	private String betType;
	/** 滚球 */
	private String rtype;
	/** 最小投注 */
	private Integer betMin;
	/** 单注最大额度 */
	private Integer betMax;
	/** 单项最大额度 */
	private Integer itemMax;
	/** 代理ID */
	private Integer sysUserId;
	/** 方案编码 */
	private String schemaCode;
	/** 排序 */
	private Integer orderNum;
	/** 列组 */
	private String columnGroupCode;
	//endregion

	
	//region constuctors
	public VUserBetLimit(){
	}

	public VUserBetLimit(Integer id){
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
	public String getBetType() {
		return this.betType;
	}

	public void setBetType(String value) {
		this.betType = value;
	}
	public String getRtype() {
		return this.rtype;
	}

	public void setRtype(String value) {
		this.rtype = value;
	}
	public Integer getBetMin() {
		return this.betMin;
	}

	public void setBetMin(Integer value) {
		this.betMin = value;
	}
	public Integer getBetMax() {
		return this.betMax;
	}

	public void setBetMax(Integer value) {
		this.betMax = value;
	}
	public Integer getItemMax() {
		return this.itemMax;
	}

	public void setItemMax(Integer value) {
		this.itemMax = value;
	}
	public Integer getSysUserId() {
		return this.sysUserId;
	}

	public void setSysUserId(Integer value) {
		this.sysUserId = value;
	}
	public String getSchemaCode() {
		return this.schemaCode;
	}

	public void setSchemaCode(String value) {
		this.schemaCode = value;
	}
	@Sortable
	public Integer getOrderNum() {
		return this.orderNum;
	}

	public void setOrderNum(Integer value) {
		this.orderNum = value;
	}

	public String getColumnGroupCode() {
		return columnGroupCode;
	}

	public void setColumnGroupCode(String columnGroupCode) {
		this.columnGroupCode = columnGroupCode;
	}
	//endregion

	//region your codes 2

	//endregion your codes 2

}