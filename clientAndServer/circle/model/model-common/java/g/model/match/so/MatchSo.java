package g.model.match.so;

import g.model.match.po.Match;


/**
 * 查询对象
 *
 * @author longer
 * @time May 10, 2016 8:14:28 PM
 */
//region your codes 1
public class MatchSo extends Match {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = -9002084271235194434L;
	//endregion your codes 3

	//region your codes 2

	private Integer gids;

	public Integer getGids() {
		return gids;
	}

	public void setGids(Integer gids) {
		this.gids = gids;
	}

	//endregion your codes 2
}