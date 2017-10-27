package g.data.match;

import g.model.match.po.MatchResult;
import g.model.match.po.VMatchResult;
import org.soul.data.rdb.mybatis.IBaseMapper;


/**
 * 数据访问对象
 *
 * @author longer
 * @time May 10, 2016 8:14:28 PM
 */
//region your codes 1
public interface VMatchResultMapper extends IBaseMapper<VMatchResult, Long> {
//endregion your codes 1

    //region your codes 2
    VMatchResult offsetMatch(Integer offSet);


    //获取最新的赛果
    MatchResult newMatchResult();
    //endregion your codes 2

}