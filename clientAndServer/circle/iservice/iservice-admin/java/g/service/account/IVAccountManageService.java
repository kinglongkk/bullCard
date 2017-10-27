package g.service.account;

import g.model.account.po.VAccountManage;
import g.model.account.vo.ResetPwdVo;
import g.model.account.vo.VAccountManageListVo;
import g.model.account.vo.VAccountManageVo;
import org.soul.iservice.support.IBaseService;


/**
 * 账号管理服务接口
 *
 * @author tom
 * @time 2015-12-28 10:35:08
 */
//region your codes 1
public interface IVAccountManageService extends IBaseService<VAccountManageListVo, VAccountManageVo, VAccountManage, Integer> {
//endregion your codes 1

    //region your codes 2

    /**
     * 保存账号冻结
     * @param vo
     * @return
     */
    VAccountManageVo saveFreezeAccount(VAccountManageVo vo);

    /**
     * 取消冻结
     * @param vo
     * @return
     */
    VAccountManageVo cancelFreezeAccount(VAccountManageVo vo);

    /**
     * 保存账号停用
     * @param vo
     * @return
     */
    VAccountManageVo saveAccountStop(VAccountManageVo vo);

    /**
     * 重置口令
     * @param vo
     * @return
     */
    boolean resetSysUserPwd(ResetPwdVo vo);
    //endregion your codes 2

}