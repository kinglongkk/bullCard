package g.service.warning;

import g.model.warning.vo.MatchPlayerListVo;
import g.model.warning.vo.MatchPlayerVo;
import org.soul.iservice.support.IBaseService;
import g.model.warning.po.VWarningPlayerDetail;
import g.model.warning.vo.VWarningPlayerDetailListVo;
import g.model.warning.vo.VWarningPlayerDetailVo;


/**
 * 服务接口
 *
 * @author lenovo
 * @time 2017-2-28 11:03:58
 */
//region your codes 1
public interface IVWarningPlayerDetailService extends IBaseService<VWarningPlayerDetailListVo, VWarningPlayerDetailVo, VWarningPlayerDetail, Long> {
//endregion your codes 1

    //region your codes 2

    //endregion your codes 2
    public MatchPlayerListVo getMatchList(MatchPlayerListVo listVo);
}