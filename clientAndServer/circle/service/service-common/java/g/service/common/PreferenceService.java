package g.service.common;

import g.data.admin.SysAnnouncementMapper;
import g.model.Module;
import g.model.setting.vo.PreferenceVo;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.commons.query.sort.Order;
import org.soul.data.mapper.sys.SysParamMapper;
import org.soul.model.sys.po.SysParam;
import org.soul.model.sys.vo.SysParamListVo;
import org.soul.model.sys.vo.SysParamVo;
import org.soul.service.support.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import g.model.SiteParamEnum;

import java.util.*;

/**
 * 偏好设置
 *
 * @author:
 * @date: 15-8-13
 */
public class PreferenceService extends BaseService<SysParamMapper, SysParamListVo, SysParamVo, SysParam, Integer> implements IPreferenceService {

    private static final Log log = LogFactory.getLog(PreferenceService.class);

    //查询固定参数配置
    SiteParamEnum[] TO_BE_EDITOR_PARAMS = {
             SiteParamEnum.SYS_BET_MIN,SiteParamEnum.YELLOW};


    SiteParamEnum[] TO_BE_EDITOR_PARAM = {
            SiteParamEnum.TODO_DEPOSIT_SIZE};



    @Autowired
    private SysParamMapper sysParamMapper;
    @Autowired
    private SysAnnouncementMapper sysAnnouncementMapper;

    @Override
    @Transactional
    public void uploadTone(SysParamVo vo) {
        Map<String, Object> updatePorperties = new HashMap<>();
        updatePorperties.put(SysParam.PROP_PARAM_VALUE, vo.getResult().getParamValue());
        sysParamMapper.updateProperties(vo.getResult().getId(), updatePorperties);
    }

    @Override
    @Transactional
    public Boolean savePreference(PreferenceVo preferenceVo) {
        try {
            sysParamMapper.updateOnly(preferenceVo.getSysParam(), SysParam.PROP_PARAM_VALUE);
            List<SysParam> sysParamList = preferenceVo.getToneSysParamList();
            sysParamMapper.batchUpdateOnly(sysParamList, SysParam.PROP_ACTIVE);
        } catch (Exception e) {
            log.error(e);
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public void resetPreference(PreferenceVo preferenceVo) {
        sysAnnouncementMapper.resetPreference();
    }

    @Override
    public SysParamListVo searchParamConfig(SysParamListVo listVo) {
        Set<String> modules = new HashSet(TO_BE_EDITOR_PARAMS.length);
        String[] moduleTypes = new String[TO_BE_EDITOR_PARAMS.length];
        for (int i = 0; i < TO_BE_EDITOR_PARAMS.length; i++) {
            modules.add(TO_BE_EDITOR_PARAMS[i].getModule().getCode());
            moduleTypes[i] = TO_BE_EDITOR_PARAMS[i].getType();
        }
        Criteria criteria = Criteria.add(SysParam.PROP_MODULE, Operator.IN, modules.toArray());
        criteria.addAnd(SysParam.PROP_PARAM_TYPE, Operator.IN, moduleTypes);
        listVo.getPaging().setTotalCount(sysParamMapper.count(criteria));
        List<SysParam> list = sysParamMapper.pagingSearch(criteria, listVo.getPaging().getPageNumber(), listVo.getPaging().getPageSize(), Order.asc(SysParam.PROP_ID));
        listVo.setResult(list);
        listVo.getPaging().cal();
        return listVo;
    }

    public SysParamListVo searchParamConfigs(SysParamListVo listVo){
        Criteria criteria = Criteria.add(SysParam.PROP_MODULE, Operator.EQ, Module.FUND.getCode());
        listVo.getPaging().setTotalCount(sysParamMapper.count(criteria));
        List<SysParam> list = sysParamMapper.pagingSearch(criteria, listVo.getPaging().getPageNumber(), listVo.getPaging().getPageSize(), Order.asc(SysParam.PROP_ID));
        listVo.setResult(list);
        listVo.getPaging().cal();
        return listVo;
    }

    @Override
    public Boolean configEdit(SysParamVo vo) {
        Boolean bool = sysParamMapper.updateOnly(vo.getResult(), SysParam.PROP_PARAM_VALUE, SysParam.PROP_DEFAULT_VALUE, SysParam.PROP_REMARK);
        return bool;
    }

    @Override
    public SysParamVo searchSysParamById(SysParamVo sysParamVo) {
        Criteria criteria = Criteria.add(SysParam.PROP_ID, Operator.EQ, sysParamVo.getSearch().getId());
        List<SysParam> sysParamList = sysParamMapper.search(criteria);
        sysParamVo.setResult(sysParamList.get(0));
        return sysParamVo;
    }

}
