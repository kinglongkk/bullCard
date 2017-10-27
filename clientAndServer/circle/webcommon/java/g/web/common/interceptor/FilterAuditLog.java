package g.web.common.interceptor;

import g.model.Module;
import org.soul.commons.support.IModuleTool;
import org.soul.model.log.audit.enums.OpType;
import org.soul.model.sys.po.SysAuditLog;
import org.soul.web.filter.log.AbstractFilterAuditLog;

/**
 * Created by longer on 9/21/15.
 */
public class FilterAuditLog extends AbstractFilterAuditLog {

    @Override
    protected void getModule(SysAuditLog sysAuditLog) {
        sysAuditLog.setModuleType(String.valueOf(0));
        sysAuditLog.setModuleName(IModuleTool.getModuleRecursive(Module.Log_Request));
        sysAuditLog.setModuleObj(Module.Log_Request.getCode());

        sysAuditLog.setOperateTypeId(Integer.valueOf(OpType.AUDIT.getCode()));
        sysAuditLog.setOperateType(OpType.AUDIT.getTrans());
    }
}
