package g.service.player;


import g.model.common.userbankcard.po.UserBankcard;
import g.model.common.userbankcard.vo.UserBankcardListVo;
import g.model.common.userbankcard.vo.UserBankcardVo;
import org.soul.iservice.support.IBaseService;

import java.util.List;

/**
 * 服务接口
 *
 * Created by  using soul-code-generator on 2015-6-19 16:50:26
 */
//region your codes 1
public interface IUserBankcardService extends IBaseService<UserBankcardListVo, UserBankcardVo, UserBankcard, Integer> {
//endregion your codes 1

    //region your codes 2

    /**
     * 获取用户正在使用的银行卡（只有一张）
     * @param vo
     * @return
     */
    public UserBankcard findByUserId(UserBankcardVo vo);

    /**
     * 修改银行卡
     * @param vo
     * @return
     */
    UserBankcardVo updateBank(UserBankcardVo vo);

    /**
     * 绑定玩家银行卡
     * @param userBankcardVo
     * @return
     */
    boolean updatePlayerBank(UserBankcardVo userBankcardVo);

    /**
     * 检测卡号是否存在
     * @param vo
     * @return
     */
    UserBankcard cardIsExists(UserBankcardVo vo);

    List<UserBankcard> findUserBankCardByUserId(UserBankcardListVo userBankcardListVo);

    /**
     * 获取玩家默认的取款方式
     * @param userBankcardListVo
     * @return
     */
    List<UserBankcard> byUserIdAndIsDefault(UserBankcardListVo userBankcardListVo);

    int saveWhenNotExist(UserBankcardVo userBankcardVo);

    //endregion your codes 2

}