package g.model.admin.po;

import org.soul.commons.bean.IEntity;
import org.soul.commons.bean.Pair;
import org.soul.commons.cache.CacheKey;
import org.soul.commons.collections.CollectionTool;
import org.soul.commons.support.Nonpersistent;
import org.soul.model.common.Sortable;

import java.util.*;


/**
 * 综合投注限额实体
 *
 * @author tom
 * @time 2016-4-20 11:37:43
 */
//region your codes 1
public class BetLimitMultiple implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = -783183485432824654L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_BET_NUM = "betNum";
	public static final String PROP_ODDS_MIN = "oddsMin";
	public static final String PROP_ODDS_MAX = "oddsMax";
	public static final String PROP_BET_MIN = "betMin";
	public static final String PROP_BET_MAX = "betMax";
	public static final String PROP_ORDER_NUM = "orderNum";
	public static final String PROP_CREATE_USER = "createUser";
	public static final String PROP_CREATE_TIME = "createTime";
	public static final String PROP_UPDATE_USER = "updateUser";
	public static final String PROP_UPDATE_TIME = "updateTime";
	public static final String PROP_SCHEMA_CODE = "schemaCode";
	//endregion
	
	
	//region properties
	/** 主键 */
	private Integer id;
	/** 综合过关投注方式 */
	private Integer betNum;
	/** 最低赔率 */
	private Integer oddsMin;
	/** 最高赔率 */
	private Integer oddsMax;
	/** 单注最低限额 */
	private Integer betMin;
	/** 单注上限 */
	private Integer betMax;
	/** 序号 */
	private Integer orderNum;
	/** 创建用户 */
	private Integer createUser;
	/** 创建时间 */
	private java.util.Date createTime;
	/** 更新用户 */
	private Integer updateUser;
	/** 更新时间 */
	private java.util.Date updateTime;
	/** 方案分组 */
	private String schemaCode;
	//endregion

	
	//region constuctors
	public BetLimitMultiple(){
	}

	public BetLimitMultiple(Integer id){
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
	public Integer getBetNum() {
		return this.betNum;
	}

	public void setBetNum(Integer value) {
		this.betNum = value;
	}
	public Integer getOddsMin() {
		return this.oddsMin;
	}

	public void setOddsMin(Integer value) {
		this.oddsMin = value;
	}
	public Integer getOddsMax() {
		return this.oddsMax;
	}

	public void setOddsMax(Integer value) {
		this.oddsMax = value;
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
	@Sortable
	public Integer getOrderNum() {
		return this.orderNum;
	}

	public void setOrderNum(Integer value) {
		this.orderNum = value;
	}
	public Integer getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(Integer value) {
		this.createUser = value;
	}
	@Sortable
	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	public Integer getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(Integer value) {
		this.updateUser = value;
	}
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(java.util.Date value) {
		this.updateTime = value;
	}

	public String getSchemaCode() {
		return schemaCode;
	}

	public void setSchemaCode(String schemaCode) {
		this.schemaCode = schemaCode;
	}
	//endregion

	//region your codes 2

	/**
	 * 通过区域重新构建数据
	 */
	public void rebuildByRange(List<BetLimitMultiple> sources,List<BetLimitMultiple> parents){
		List<BetLimitMultiple> news = new ArrayList<>();
		List<Integer> parentBetNums = CollectionTool.extractToList(parents,BetLimitMultiple.PROP_BET_NUM);
		Map<Integer, Map<Integer, BetLimitMultiple>> sourceMap = toBetNumMap(sources);
		for (BetLimitMultiple parent : parents) {
			Integer betNum = parent.getBetNum();
			Map<Integer,BetLimitMultiple> rangePairMap = sourceMap.get(betNum);
			if (rangePairMap == null) {
				//子对象无此数据
				news.add(parent);
				continue;
			}
			//获取子区间
			Pair<Integer,Integer> parentPair = new Pair<>(parent.getOddsMin(),parent.getOddsMax());
			BetLimitMultiple source = rangePairMap.get(parentPair);
			if (source != null) {
				//子区间存在,只判断上限
				if (source.getOddsMax() > parent.getOddsMax()) {
					//以父为准
					source.setOddsMax(parent.getOddsMax());
				}
				continue;
			}
			//TODO:Longer 不在区间内的处理
		}

		//删除父级减少的项
		for (Iterator<BetLimitMultiple> iterator = sources.iterator();iterator.hasNext();) {
			BetLimitMultiple source = iterator.next();
			if (!parentBetNums.contains(source.getBetNum())){
				iterator.remove();
			}
		}

		//补充父级新增加的项
		if (CollectionTool.isNotEmpty(news)){
			sources.addAll(news);
		}

	}

	/**
	 *
	 * @param sources
	 * @return 	kye: betNum  Value:[Map]{Key:odd_min odd_max,Value:BetLimitMultiple}
	 */
	private Map<Integer, Map<Integer, BetLimitMultiple>> toBetNumMap(List<BetLimitMultiple> sources) {
		Map<Integer,Map<Integer,BetLimitMultiple>> sourceMap = new HashMap<>();
		for (BetLimitMultiple source : sources) {
			Map<Integer,BetLimitMultiple> rangePairMap = sourceMap.get(source.getBetNum());
			if (rangePairMap == null){
				rangePairMap = new HashMap();
				sourceMap.put(source.getBetNum(),rangePairMap);
			}
			rangePairMap.put(source.getOddsMax(),source);

		}
		return sourceMap;
	}

	@Nonpersistent
	public String getRange(){
		return CacheKey.getCacheKey(getBetNum().toString(),getOddsMin().toString(),getOddsMax().toString());

	}

	//endregion your codes 2

}