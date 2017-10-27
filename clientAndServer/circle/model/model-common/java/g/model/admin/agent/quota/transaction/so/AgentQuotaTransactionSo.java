package g.model.admin.agent.quota.transaction.so;

import g.model.admin.agent.quota.transaction.po.AgentQuotaTransaction;

import java.util.Date;


/**
 * 额度日志查询对象
 *
 * @author black
 * @time 2016-12-3 10:47:43
 */
public class AgentQuotaTransactionSo extends AgentQuotaTransaction {

	private static final long serialVersionUID = -3006781631478961988L;

	/**
	 * 开始时间
	 */
	private Date startTime;
	/**
	 * 结束时间
	 */
	private Date endTime;

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
}