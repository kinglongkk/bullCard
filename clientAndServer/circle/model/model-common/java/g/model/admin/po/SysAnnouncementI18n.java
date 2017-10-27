package g.model.admin.po;

import org.soul.commons.bean.IEntity;
import org.soul.model.common.Sortable;


/**
 * 实体
 *
 * @author orange
 * @time 2015-11-17 14:59:10
 */
//region your codes 1
public class SysAnnouncementI18n implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = -3626803358045045112L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_SYS_ANNOUNCEMENT_ID = "sysAnnouncementId";
	public static final String PROP_LOCAL = "local";
	public static final String PROP_TITLE = "title";
	public static final String PROP_CONTENT = "content";
	//endregion
	
	
	//region properties
	/** 主键 */
	private Integer id;
	/** 系统公告表ID */
	private Integer sysAnnouncementId;
	/** 语言版本(site_language) */
	private String local;
	/** 标题 */
	private String title;
	/** 内容 */
	private String content;
	//endregion

	
	//region constuctors
	public SysAnnouncementI18n(){
	}

	public SysAnnouncementI18n(Integer id){
		this.id = id;
	}
	//endregion


	//region getters and setters
	@Sortable
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer value) {
		this.id = value;
	}
	public Integer getSysAnnouncementId() {
		return this.sysAnnouncementId;
	}

	public void setSysAnnouncementId(Integer value) {
		this.sysAnnouncementId = value;
	}
	public String getLocal() {
		return this.local;
	}

	public void setLocal(String value) {
		this.local = value;
	}
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String value) {
		this.title = value;
	}
	public String getContent() {
		return this.content;
	}

	public void setContent(String value) {
		this.content = value;
	}
	//endregion

	//region your codes 2

	//endregion your codes 2

}