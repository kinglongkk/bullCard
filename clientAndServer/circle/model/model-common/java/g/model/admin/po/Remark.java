package g.model.admin.po;

import org.soul.commons.bean.IEntity;

import java.util.Date;


/**
 * 玩家备注表实体
 *
 * @author cogo
 * @tableAuthor orange
 * @time 2016-1-31 15:25:46
 */
//region your codes 1
public class Remark implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = 1119634190429108275L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_REMARK_CONTENT = "remarkContent";
	public static final String PROP_REMARK_TIME = "remarkTime";
	public static final String PROP_MODEL = "model";
	public static final String PROP_REMARK_TITLE = "remarkTitle";
	public static final String PROP_REMARK_TYPE = "remarkType";
	public static final String PROP_ENTITY_USER_ID = "entityUserId";
	public static final String PROP_OPERATOR_ID = "operatorId";
	public static final String PROP_ENTITY_ID = "entityId";
	public static final String PROP_OPERATOR = "operator";
	//endregion
	
	
	//region properties
	/** 主键 */
	private Integer id;
	/** 备注内容 */
	private String remarkContent;
	/** 创建时间 */
	private Date remarkTime;
	/** 模块名 */
	private String model;
	/** 备注标题 */
	private String remarkTitle;
	/** 备注类型 sys_dict Model：common,Dict_type：remark_type */
	private String remarkType;
	/** 被操作实体的所属用户ID */
	private Integer entityUserId;
	/** 操作员id */
	private Integer operatorId;
	/** 业务实体id */
	private Integer entityId;
	/** 操作员 */
	private String operator;
	//endregion


	//region constuctors
	public Remark(){
	}

	public Remark(Integer id){
		this.id = id;
	}
	//endregion


	//region getters and setters
	@org.soul.model.common.Sortable
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer value) {
		this.id = value;
	}
	public String getRemarkContent() {
		return this.remarkContent;
	}

	public void setRemarkContent(String value) {
		this.remarkContent = value;
	}
	@org.soul.model.common.Sortable
	public Date getRemarkTime() {
		return this.remarkTime;
	}

	public void setRemarkTime(Date value) {
		this.remarkTime = value;
	}
	public String getModel() {
		return this.model;
	}

	public void setModel(String value) {
		this.model = value;
	}
	public String getRemarkTitle() {
		return this.remarkTitle;
	}

	public void setRemarkTitle(String value) {
		this.remarkTitle = value;
	}
	public String getRemarkType() {
		return this.remarkType;
	}

	public void setRemarkType(String value) {
		this.remarkType = value;
	}
	public Integer getEntityUserId() {
		return this.entityUserId;
	}

	public void setEntityUserId(Integer value) {
		this.entityUserId = value;
	}
	public Integer getOperatorId() {
		return this.operatorId;
	}

	public void setOperatorId(Integer value) {
		this.operatorId = value;
	}
	public Integer getEntityId() {
		return this.entityId;
	}

	public void setEntityId(Integer value) {
		this.entityId = value;
	}
	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String value) {
		this.operator = value;
	}
	//endregion

	//region your codes 2

	//endregion your codes 2

}