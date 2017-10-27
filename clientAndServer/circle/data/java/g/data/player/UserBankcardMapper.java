package g.data.player;


import g.model.common.userbankcard.po.UserBankcard;
import g.model.common.userbankcard.so.UserBankcardSo;
import org.soul.data.rdb.mybatis.IBaseMapper;

import java.util.List;

/**
 * 数据访问对象
 *
 * Created by shisongbin using soul-code-generator on 2015-6-19 16:50:26
 */
//region your codes 1
public interface UserBankcardMapper extends IBaseMapper<UserBankcard, Integer> {
//endregion your codes 1

    //region your codes 2

    /**
     * 根据银行卡查找卡号信息
     * @param so
     * @return
     */
    UserBankcard findCardByCardNumber(UserBankcardSo so);

    /**
     * 根据玩家Id获取绑定的银行卡信息
     * @param userBankcardSo
     * @return
     */
    List<UserBankcard> findUserBankCardByUserId(UserBankcardSo userBankcardSo);

    /**
     * 保存取款资料,当指定类型未存在的情况下
     * @param userBankcard
     * @return
     */
    int saveWhenNotExist(UserBankcard userBankcard);
    //endregion your codes 2

}