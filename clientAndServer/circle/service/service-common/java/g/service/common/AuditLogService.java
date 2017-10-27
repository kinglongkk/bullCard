package g.service.common;

import g.model.SubSysCodeEnum;
import g.model.UserTypeEnum;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.net.IpTool;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.commons.query.sort.Order;
import org.soul.data.mapper.sys.SysAuditLogMapper;
import org.soul.model.sys.po.SysAuditLog;
import org.soul.model.sys.vo.SysAuditLogListVo;
import org.soul.model.sys.vo.SysAuditLogVo;
import org.soul.service.support.BaseService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author fly
 * @time 2015-11-06 16:02
 */
public class AuditLogService extends BaseService<SysAuditLogMapper, SysAuditLogListVo, SysAuditLogVo, SysAuditLog, String> implements IAuditLogService {
    @Override
    public SysAuditLogListVo queryLogs(SysAuditLogListVo listVo) {
        Criteria time = Criteria.and(
                Criteria.add(SysAuditLog.PROP_OPERATE_TIME, Operator.GE, listVo.getSearch().getOperatorBegin()),
                Criteria.add(SysAuditLog.PROP_OPERATE_TIME, Operator.LT, listVo.getSearch().getOperatorEnd())
        );
        Criteria moduleType = Criteria.add(SysAuditLog.PROP_MODULE_TYPE, Operator.EQ, listVo.getSearch().getModuleType());
        Criteria operateType = Criteria.add(SysAuditLog.PROP_OPERATE_TYPE, Operator.EQ, listVo.getSearch().getOperateType());
        Criteria entityUserId = Criteria.add(SysAuditLog.PROP_ENTITY_USER_ID, Operator.EQ, listVo.getSearch().getEntityUserId());
        Criteria entityId = Criteria.add(SysAuditLog.PROP_ENTITY_ID, Operator.EQ, listVo.getSearch().getEntityId());
        Criteria operator = Criteria.add(SysAuditLog.PROP_OPERATOR, Operator.EQ, listVo.getSearch().getOperator());
        Criteria operateUserType = new Criteria();
        if (StringTool.equals(listVo.getSearch().getRoleType(), SubSysCodeEnum.AGENT.getCode()) && listVo.getSearch().getOperatorUserType()==null){
            operateUserType.addAnd(SysAuditLog.PROP_OPERATOR_USER_TYPE, Operator.IN,new ArrayList(Arrays.asList(UserTypeEnum.AGENT.getCode(), UserTypeEnum.AGENT_SUB.getCode())));
        }else if (StringTool.equals(listVo.getSearch().getRoleType(), "player")  && listVo.getSearch().getOperatorUserType()==null ){
            operateUserType.addAnd(SysAuditLog.PROP_OPERATOR_USER_TYPE, Operator.IN,new ArrayList(Arrays.asList(UserTypeEnum.ADMIN.getCode(), UserTypeEnum.ADMIN_SUB.getCode())));
        }else {
            operateUserType.addAnd(SysAuditLog.PROP_OPERATOR_USER_TYPE, Operator.EQ, listVo.getSearch().getOperatorUserType());
        }
        long ip = IpTool.ipv4StringToLong(listVo.getSearch().getIp());
        Criteria operateIp = Criteria.add(SysAuditLog.PROP_OPERATE_IP, Operator.EQ, ip == -1 ? null : ip);
        Criteria criteria = Criteria.and(
                time,
                moduleType,
                entityUserId,
                entityId,
                operator,
                operateIp,
                operateType, operateUserType
        );
        List<SysAuditLog> logs = this.mapper.pagingSearch(criteria, listVo.getPaging().getPageNumber(), listVo.getPaging().getPageSize(), Order.desc(SysAuditLog.PROP_OPERATE_TIME));
        listVo.setResult(logs);
        listVo.getPaging().setTotalCount(this.mapper.count(criteria));
        listVo.getPaging().cal();
        return listVo;
    }
}
