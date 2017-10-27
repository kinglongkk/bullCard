package g.model.sys.po;

import org.soul.commons.bean.IEntity;
import org.soul.model.common.Sortable;


/**
 * 站点表实体
 *
 * @author longer
 * @tableAuthor Longer
 * @time Jul 23, 2016 9:50:20 AM
 */
//region your codes 1
public class SysSite implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = -8486641783026104740L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_SYS_USER_ID = "sysUserId";
	public static final String PROP_NAME = "name";
	public static final String PROP_THEME = "theme";
	public static final String PROP_SSO_THEME = "ssoTheme";
	public static final String PROP_STATUS = "status";
	public static final String PROP_IS_BUILDIN = "isBuildin";
	public static final String PROP_POSTFIX = "postfix";
	public static final String PROP_SHORT_NAME = "shortName";
	public static final String PROP_MAIN_CURRENCY = "mainCurrency";
	public static final String PROP_MAIN_LANGUAGE = "mainLanguage";
	public static final String PROP_WEB_SITE = "webSite";
	public static final String PROP_OPENING_TIME = "openingTime";
	public static final String PROP_TIMEZONE = "timezone";
	public static final String PROP_TRAFFIC_STATISTICS = "trafficStatistics";
	public static final String PROP_CODE = "code";
	public static final String PROP_LOGO_PATH = "logoPath";
	public static final String PROP_PARENT_ID = "parentId";
	public static final String PROP_SITE_CLASSIFY_KEY = "siteClassifyKey";
	public static final String PROP_SITE_NET_SCHEME_ID = "siteNetSchemeId";
	public static final String PROP_MAX_PROFIT = "maxProfit";
	public static final String PROP_DEPOSIT = "deposit";
	public static final String PROP_TEMPLATE_CODE = "templateCode";
	public static final String PROP_MAINTAIN_START_TIME = "maintainStartTime";
	public static final String PROP_MAINTAIN_END_TIME = "maintainEndTime";
	public static final String PROP_MAINTAIN_REASON = "maintainReason";
	public static final String PROP_MAINTAIN_OPERATE_ID = "maintainOperateId";
	public static final String PROP_MAINTAIN_OPERATE_TIME = "maintainOperateTime";
	public static final String PROP_IMPORT_PLAYERS_TIME = "importPlayersTime";
	//endregion
	
	
	//region properties
	/**  */
	private Integer id;
	/** 用户ID */
	private Integer sysUserId;
	/** 站点名称 */
	private String name;
	/** 主题 */
	private String theme;
	/** 单点主题 */
	private String ssoTheme;
	/** 状态,1:正常，2:停用，3:维护中, 4:未建库 */
	private String status;
	/** 是否内置 */
	private Boolean isBuildin;
	/** 帐号后缀 */
	private String postfix;
	/** 简称 */
	private String shortName;
	/** 主货币 */
	private String mainCurrency;
	/** 主语言 */
	private String mainLanguage;
	/** 站点网站 */
	private String webSite;
	/** 开站时间 */
	private java.util.Date openingTime;
	/** 站点时区 */
	private String timezone;
	/** 流量统计代码 */
	private String trafficStatistics;
	/** 站点代码 */
	private String code;
	/** Logo路径 */
	private String logoPath;
	/** 站点父站 */
	private Integer parentId;
	/** 站点类型 */
	private String siteClassifyKey;
	/** 包网方案ID */
	private Integer siteNetSchemeId;
	/** 盈利上限 */
	private Double maxProfit;
	/** 押金 */
	private Double deposit;
	/** 模板code */
	private String templateCode;
	/** 维护开始时间 */
	private java.util.Date maintainStartTime;
	/** 维护结束时间 */
	private java.util.Date maintainEndTime;
	/** 维护原因 */
	private String maintainReason;
	/** 维护者 */
	private Integer maintainOperateId;
	/** 维护操作时间 */
	private java.util.Date maintainOperateTime;
	/** 导入玩家截止日期 */
	private java.util.Date importPlayersTime;
	//endregion

	
	//region constuctors
	public SysSite(){
	}

	public SysSite(Integer id){
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
	@org.soul.model.common.Sortable
	public Integer getSysUserId() {
		return this.sysUserId;
	}

	public void setSysUserId(Integer value) {
		this.sysUserId = value;
	}
	public String getName() {
		return this.name;
	}

	public void setName(String value) {
		this.name = value;
	}
	public String getTheme() {
		return this.theme;
	}

	public void setTheme(String value) {
		this.theme = value;
	}
	public String getSsoTheme() {
		return this.ssoTheme;
	}

	public void setSsoTheme(String value) {
		this.ssoTheme = value;
	}
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String value) {
		this.status = value;
	}
	public Boolean getIsBuildin() {
		return this.isBuildin;
	}

	public void setIsBuildin(Boolean value) {
		this.isBuildin = value;
	}
	public String getPostfix() {
		return this.postfix;
	}

	public void setPostfix(String value) {
		this.postfix = value;
	}
	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String value) {
		this.shortName = value;
	}
	public String getMainCurrency() {
		return this.mainCurrency;
	}

	public void setMainCurrency(String value) {
		this.mainCurrency = value;
	}
	public String getMainLanguage() {
		return this.mainLanguage;
	}

	public void setMainLanguage(String value) {
		this.mainLanguage = value;
	}
	public String getWebSite() {
		return this.webSite;
	}

	public void setWebSite(String value) {
		this.webSite = value;
	}
	public java.util.Date getOpeningTime() {
		return this.openingTime;
	}

	public void setOpeningTime(java.util.Date value) {
		this.openingTime = value;
	}
	public String getTimezone() {
		return this.timezone;
	}

	public void setTimezone(String value) {
		this.timezone = value;
	}
	public String getTrafficStatistics() {
		return this.trafficStatistics;
	}

	public void setTrafficStatistics(String value) {
		this.trafficStatistics = value;
	}
	public String getCode() {
		return this.code;
	}

	public void setCode(String value) {
		this.code = value;
	}
	public String getLogoPath() {
		return this.logoPath;
	}

	public void setLogoPath(String value) {
		this.logoPath = value;
	}
	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer value) {
		this.parentId = value;
	}
	public String getSiteClassifyKey() {
		return this.siteClassifyKey;
	}

	public void setSiteClassifyKey(String value) {
		this.siteClassifyKey = value;
	}
	public Integer getSiteNetSchemeId() {
		return this.siteNetSchemeId;
	}

	public void setSiteNetSchemeId(Integer value) {
		this.siteNetSchemeId = value;
	}
	public Double getMaxProfit() {
		return this.maxProfit;
	}

	public void setMaxProfit(Double value) {
		this.maxProfit = value;
	}
	public Double getDeposit() {
		return this.deposit;
	}

	public void setDeposit(Double value) {
		this.deposit = value;
	}
	public String getTemplateCode() {
		return this.templateCode;
	}

	public void setTemplateCode(String value) {
		this.templateCode = value;
	}
	public java.util.Date getMaintainStartTime() {
		return this.maintainStartTime;
	}

	public void setMaintainStartTime(java.util.Date value) {
		this.maintainStartTime = value;
	}
	public java.util.Date getMaintainEndTime() {
		return this.maintainEndTime;
	}

	public void setMaintainEndTime(java.util.Date value) {
		this.maintainEndTime = value;
	}
	public String getMaintainReason() {
		return this.maintainReason;
	}

	public void setMaintainReason(String value) {
		this.maintainReason = value;
	}
	public Integer getMaintainOperateId() {
		return this.maintainOperateId;
	}

	public void setMaintainOperateId(Integer value) {
		this.maintainOperateId = value;
	}
	public java.util.Date getMaintainOperateTime() {
		return this.maintainOperateTime;
	}

	public void setMaintainOperateTime(java.util.Date value) {
		this.maintainOperateTime = value;
	}
	public java.util.Date getImportPlayersTime() {
		return this.importPlayersTime;
	}

	public void setImportPlayersTime(java.util.Date value) {
		this.importPlayersTime = value;
	}
	//endregion

	//region your codes 2

	//endregion your codes 2

}