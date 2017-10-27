package g.service.common;

import g.model.setting.vo.PreferenceVo;
import org.soul.iservice.support.IBaseService;
import org.soul.model.sys.po.SysParam;
import org.soul.model.sys.vo.SysParamListVo;
import org.soul.model.sys.vo.SysParamVo;

/**
 * 偏好设置
 * @author: tom
 * @date: 15-8-13
 */
public interface IPreferenceService  extends IBaseService<SysParamListVo, SysParamVo, SysParam, Integer> {

    /**
     * 更新提示音
     * @param vo
     */
    void uploadTone(SysParamVo vo);

    /**
     * 保存偏好设置
     * @param preferenceVo
     * @return
     */
    Boolean savePreference(PreferenceVo preferenceVo);

    void resetPreference(PreferenceVo preferenceVo);

    SysParamListVo searchParamConfig(SysParamListVo listVo);

    SysParamListVo searchParamConfigs(SysParamListVo listVo);

    Boolean configEdit(SysParamVo vo);

    SysParamVo searchSysParamById(SysParamVo sysParamVo);
}
