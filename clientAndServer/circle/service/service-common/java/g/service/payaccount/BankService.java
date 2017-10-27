package g.service.payaccount;

import g.data.payaccount.BankMapper;
import g.model.payaccount.po.Bank;
import g.model.payaccount.vo.BankListVo;
import g.model.payaccount.vo.BankVo;
import org.soul.commons.collections.CollectionTool;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.commons.query.sort.Direction;
import org.soul.commons.query.sort.Order;
import org.soul.service.support.BaseService;

import java.util.List;
import java.util.Map;


/**
 * 银行表服务
 *
 * @author mark
 * @time 2016-7-13 19:21:26
 */
//region your codes 1
public class BankService extends BaseService<BankMapper, BankListVo, BankVo, Bank, Integer> implements IBankService {
//endregion your codes 1

    //region your codes 2
    @Override
    public Map<String, Bank> load(BankVo bankVo) {
        Criteria criteria = Criteria.add(Bank.PROP_IS_USE, Operator.EQ,true);
        Order order = new Order(Bank.PROP_ORDER_NUM, Direction.ASC);
        List<Bank> list = mapper.search(criteria,order);
        Map<String, Bank> cacheMap = CollectionTool.toEntityMap(list, Bank.PROP_BANK_NAME, String.class);
        return cacheMap;
    }
    //endregion your codes 2

}