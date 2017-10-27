package g.service.common;

import org.soul.iservice.support.IBaseService;
import org.soul.model.sys.po.SysAuditLog;
import org.soul.model.sys.vo.SysAuditLogListVo;
import org.soul.model.sys.vo.SysAuditLogVo;

/**
 * 审计日志业务接口
 * @author fly
 * @time 2015-11-06 14:09
 */
public interface IAuditLogService extends IBaseService<SysAuditLogListVo, SysAuditLogVo, SysAuditLog, String> {
    /**
     * 日志查询
     * @param listVo
     * @return
     */
    SysAuditLogListVo queryLogs(SysAuditLogListVo listVo);
}
