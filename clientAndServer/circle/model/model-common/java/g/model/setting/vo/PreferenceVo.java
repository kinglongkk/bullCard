package g.model.setting.vo;

import org.soul.commons.init.context.AbstractBaseVo;
import org.soul.model.sys.po.SysParam;
import org.soul.model.sys.vo.SysParamVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: tom
 * @date: 15-8-13
 */
public class PreferenceVo extends SysParamVo {
    private static final long serialVersionUID = -4148092115284749406L;

    /** 权限密码 */
    private SysParam sysParam;

    /** 提示音设置 */
    private List<SysParam> toneSysParamList;
    /** 保存需要查询固定的参数配置 */
    private List parameterConfigList;

    public List getParameterConfigList() {
        return parameterConfigList;
    }

    public void setParameterConfigList(List parameterConfigList) {
        this.parameterConfigList = parameterConfigList;
    }

    public SysParam getSysParam() {
        return sysParam;
    }

    public void setSysParam(SysParam sysParam) {
        this.sysParam = sysParam;
    }

    public List<SysParam> getToneSysParamList() {
        return toneSysParamList;
    }

    public void setToneSysParamList(List<SysParam> toneSysParamList) {
        this.toneSysParamList = toneSysParamList;
    }

}
