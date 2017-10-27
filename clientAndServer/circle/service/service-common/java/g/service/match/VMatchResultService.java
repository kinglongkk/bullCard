package g.service.match;

import g.data.match.VMatchResultMapper;
import g.model.match.po.VMatchResult;
import g.model.match.vo.VMatchResultListVo;
import g.model.match.vo.VMatchResultVo;
import org.soul.service.support.BaseService;

import java.util.Arrays;
import java.util.Map;


/**
 * 服务
 *
 * @author longer
 * @time May 10, 2016 8:14:28 PM
 */
//region your codes 1
public class VMatchResultService extends BaseService<VMatchResultMapper, VMatchResultListVo, VMatchResultVo, VMatchResult, Long> implements IVMatchResultService {
//endregion your codes 1

    //region your codes 2
    @Override
    public VMatchResult offsetMatch(Integer offSet) {
        return mapper.offsetMatch(offSet);
    }


    @Override
    public String newMatchResult() {
        return mapper.newMatchResult().getResult();
    }

    //endregion your codes 2

}