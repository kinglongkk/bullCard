package g.service.match;


import g.model.match.po.VMatchResult;
import g.model.match.vo.VMatchResultListVo;
import g.model.match.vo.VMatchResultVo;
import org.soul.iservice.support.IBaseService;

/**
 * 服务接口
 *
 * @author longer
 * @time May 10, 2016 8:14:28 PM
 */
//region your codes 1
public interface IVMatchResultService extends IBaseService<VMatchResultListVo, VMatchResultVo, VMatchResult, Long> {
//endregion your codes 1

    //region your codes 2
    /**
     * 获取上一期结果
     * @return
     */
    VMatchResult offsetMatch(Integer offSet);

    String newMatchResult();
    //endregion your codes 2

}