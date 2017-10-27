package g.model.bet.so;

import g.model.bet.po.VBetSettle;

import java.util.List;


/**
 * 注单结算查询对象
 *
 * @author longer
 * @time May 19, 2016 1:42:07 PM
 */
//region your codes 1
public class VBetSettleSo extends VBetSettle {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = 8765962129382542847L;
	//endregion your codes 3

	//region your codes 2

	private List<Long> ids;

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	//endregion your codes 2
}