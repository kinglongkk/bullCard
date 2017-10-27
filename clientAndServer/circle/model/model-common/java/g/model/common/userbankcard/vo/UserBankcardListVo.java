package g.model.common.userbankcard.vo;

import g.model.common.userbankcard.po.UserBankcard;
import g.model.common.userbankcard.so.UserBankcardSo;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.commons.query.sort.Order;
import org.soul.commons.query.sort.Sort;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;
import org.soul.model.common.FieldProperty;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * 列表页值对象
 *
 * Created by shisongbin using soul-code-generator on 2015-6-19 16:50:26
 */
public class UserBankcardListVo extends BaseListVo<UserBankcard, UserBankcardSo, UserBankcardListVo.UserBankcardQuery> {

    private static final long serialVersionUID = 5423342757670607872L;

    public Map<String,FieldProperty> getDefaultFields() {
        Map<String,FieldProperty> list = new LinkedHashMap<>();
//        list.add(new FieldProperty(SysUserBankcard.class, "name", 0, "名称", 0));
//        list.add(new FieldProperty(SysUserBankcard.class, "code", 1, "编码", 1));
//        list.add(new FieldProperty(SysUserBankcard.class, "status", 3, "状态", 2));
        return list;
    }
    /**
     *  列表查询逻辑
     */
    public static class UserBankcardQuery extends AbstractQuery<UserBankcardSo> {

        private static final long serialVersionUID = -9223372036854775808L;

        @Override
        public Criteria getCriteria() {
            Criteria criteria = Criteria.add("userId", Operator.EQ, ((UserBankcardSo) this.searchObject).getUserId());
            return criteria;
        }

        @Override
        public Sort getDefaultSort() {
            Sort orders = new Sort(Order.desc(UserBankcard.PROP_IS_DEFAULT));
            Sort createTimeDesc = new Sort(Order.desc(UserBankcard.PROP_CREATE_TIME));
            Sort sort = orders.and(createTimeDesc);
            return sort;
        }

        public Criteria byUserIdAndIsDefault(){
            return Criteria.and(
                    Criteria.add(UserBankcard.PROP_USER_ID,Operator.EQ,searchObject.getUserId()),
                    Criteria.add(UserBankcard.PROP_IS_DEFAULT,Operator.EQ,Boolean.TRUE)
            );
        }
    }
}