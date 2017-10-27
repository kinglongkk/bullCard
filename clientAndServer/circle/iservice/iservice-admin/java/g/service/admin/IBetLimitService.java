package g.service.admin;

import org.soul.iservice.support.IBaseService;
import g.model.admin.po.BetLimit;
import g.model.admin.vo.BetLimitListVo;
import g.model.admin.vo.BetLimitVo;

import java.util.List;


/**
 * 投注限额服务接口
 *
 * @author tom
 * @time 2016-4-20 11:35:40
 */
//region your codes 1
public interface IBetLimitService extends IBaseService<BetLimitListVo, BetLimitVo, BetLimit, Integer> {
//endregion your codes 1

    //region your codes 2

    /**
     * 获得系统投注限额，包括单注单项限额、综合过关单注限额
     * @param vo
     * @return
     */
    BetLimitListVo getSysBetLimitIncMulti(BetLimitListVo vo);

    /**
     * 获得系统单注单项限额
     * @param vo
     * @return
     */
    BetLimitListVo getSysBetLimit(BetLimitListVo vo);



    /**
     * 保存系统投注限额，包括单注单项限额、综合过关单注限额
     * @param vo
     * @return
     */
    BetLimitVo editBetLimits(BetLimitVo vo);

    /**
     * 查出所有默认投注限额
     * @return
     */
    List<BetLimit> listDefault();

    /**
     * 更新一个po
     * @param betLimit
     */
    void updateByEntity(BetLimit betLimit);

    /**
     * 通过id查找一个po
     * @param id
     * @return
     */
    BetLimit getById(Integer id);

    /**
     * 保存一个ListPo到数据库
     * @param list
     */
    void saveByList(List<BetLimit> list);
    //endregion your codes 2

}