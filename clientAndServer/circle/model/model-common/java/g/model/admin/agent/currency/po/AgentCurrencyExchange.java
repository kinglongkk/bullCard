package g.model.admin.agent.currency.po;

import org.soul.commons.bean.IEntity;
import org.soul.model.common.Sortable;


/**
 * 货币汇率实体
 *
 * @author black
 * @time 2016-11-28 11:26:51
 */
//region your codes 1
public class AgentCurrencyExchange implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = 9109490310098701006L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_CODE = "code";
	public static final String PROP_NAME = "name";
	public static final String PROP_EXCHANGE = "exchange";
	//endregion
	
	
	//region properties
	/** 主键 */
	private Integer id;
	/** 货币编码 */
	private String code;
	/** 货币名称 */
	private String name;
	/** 货币汇率 */
	private Double exchange;
	//endregion

	
	//region constuctors
	public AgentCurrencyExchange(){
	}

	public AgentCurrencyExchange(Integer id){
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
	public String getCode() {
		return this.code;
	}

	public void setCode(String value) {
		this.code = value;
	}
	public String getName() {
		return this.name;
	}

	public void setName(String value) {
		this.name = value;
	}
	public Double getExchange() {
		return this.exchange;
	}

	public void setExchange(Double value) {
		this.exchange = value;
	}
	//endregion

	//region your codes 2

	//endregion your codes 2

}