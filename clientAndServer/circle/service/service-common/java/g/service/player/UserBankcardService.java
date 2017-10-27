package g.service.player;


import g.data.player.UserBankcardMapper;
import g.model.common.userbankcard.po.UserBankcard;
import g.model.common.userbankcard.vo.UserBankcardListVo;
import g.model.common.userbankcard.vo.UserBankcardVo;
import org.soul.commons.collections.CollectionTool;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.service.support.BaseService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务
 * <p>
 * Created by shisongbin using soul-code-generator on 2015-6-19 16:50:26
 */
//region your codes 1
public class UserBankcardService extends BaseService<UserBankcardMapper, UserBankcardListVo, UserBankcardVo, UserBankcard, Integer> implements IUserBankcardService {
//endregion your codes 1

    //region your codes 2
    @Override
    public UserBankcard findByUserId(UserBankcardVo vo) {
        Criteria criteria = Criteria.add(UserBankcard.PROP_USER_ID, Operator.EQ, vo.getSearch().getUserId()).addAnd(Criteria.add(UserBankcard.PROP_IS_DEFAULT, Operator.EQ, true));
        List<UserBankcard> search = mapper.search(criteria);
        UserBankcard bankcard = null;
        if (CollectionTool.isNotEmpty(search)) {
            bankcard = search.get(0);
        }
        return bankcard;
    }

    @Override
    @Transactional
    public UserBankcardVo updateBank(UserBankcardVo vo) {
        Integer id = vo.getSearch().getId();
        UserBankcard _userBankcard = this.mapper.get(id);

        //银行卡修改即是将原默认银行卡信息修改为历史使用，新增一条默认使用银行卡信息
        Map<String, Object> properties = new HashMap<>();
        properties.put(UserBankcard.PROP_IS_DEFAULT, false);
        this.mapper.batchUpdateProperties(Criteria.add(UserBankcard.PROP_USER_ID, Operator.EQ, _userBankcard.getUserId()), properties);

        UserBankcard userBankcard = vo.getResult();
        userBankcard.setBankcardMasterName(_userBankcard.getBankcardMasterName());
        userBankcard.setIsDefault(true);
        userBankcard.setUseStauts(false);
        userBankcard.setCreateTime(new Date());
        userBankcard.setUseCount(0);
        userBankcard.setUserId(_userBankcard.getUserId());
        this.mapper.insert(userBankcard);
        return vo;
    }

    @Override
    @Transactional
    public boolean updatePlayerBank(UserBankcardVo userBankcardVo) {

        boolean flag = false;
        flag = mapper.insert(userBankcardVo.getResult());
        if (flag) {
            if (StringTool.isNotBlank(userBankcardVo.getBankId())) {
                UserBankcard userBankcard = new UserBankcard();
                userBankcard.setId(Integer.valueOf(userBankcardVo.getBankId()));
                userBankcard.setIsDefault(Boolean.FALSE);
                flag = mapper.updateOnly(userBankcard,UserBankcard.PROP_IS_DEFAULT);
            }
        }
        return flag;
    }

    @Override
    public UserBankcard cardIsExists(UserBankcardVo vo) {
        return mapper.findCardByCardNumber(vo.getSearch());
    }

    @Override
    public List<UserBankcard> findUserBankCardByUserId(UserBankcardListVo userBankcardListVo) {
        return mapper.findUserBankCardByUserId(userBankcardListVo.getSearch());
    }

    @Override
    public List<UserBankcard> byUserIdAndIsDefault(UserBankcardListVo userBankcardListVo) {
        return mapper.search(userBankcardListVo.getQuery().byUserIdAndIsDefault());
    }

    @Override
    public int saveWhenNotExist(UserBankcardVo userBankcardVo) {
        return mapper.saveWhenNotExist(userBankcardVo.getResult());
    }


    //endregion your codes 2

}