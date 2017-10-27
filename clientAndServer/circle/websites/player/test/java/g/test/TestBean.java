package g.test;

import org.soul.commons.bean.IEntity;

import java.util.Date;

/**
 * Created by Kevice on 2015/1/30.
 */
public class TestBean implements IEntity<Integer> {

	private Integer id;
	private String loginName;
	private Date birthday;
	private Boolean activeStatus;
	private double weight;
	private Integer height;

	@Override public Integer getId() {
		return id;
	}

	@Override public void setId(Integer id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Boolean getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(Boolean activeStatus) {
		this.activeStatus = activeStatus;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}
}
