package g.service.common;

import g.data.agent.VSubAccountMapper;
import g.data.setting.MSysRoleMapper;
import g.data.setting.VSysRoleMapper;
import g.model.agent.po.VSubAccount;
import g.model.agent.so.VSubAccountSo;
import g.model.agent.vo.VSubAccountListVo;
import g.model.agent.vo.VSubAccountVo;
import g.model.setting.po.VSysRole;
import g.model.setting.vo.SysRoleListVo;
import org.soul.commons.bean.Pair;
import org.soul.commons.collections.CollectionTool;
import org.soul.commons.collections.ListTool;
import org.soul.commons.collections.MapTool;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.net.IpTool;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.commons.query.sort.Order;
import org.soul.data.mapper.msg.notice.NoticeContactWayMapper;
import org.soul.data.mapper.security.privilege.*;
import org.soul.data.mapper.sys.SysAuditLogMapper;
import org.soul.model.sys.po.SysAuditLog;
import org.soul.model.sys.so.SysAuditLogSo;
import org.soul.model.sys.vo.SysAuditLogListVo;
import org.soul.model.msg.notice.po.NoticeContactWay;
import org.soul.model.security.privilege.po.SysRole;
import org.soul.model.security.privilege.po.SysRoleResource;
import org.soul.model.security.privilege.po.SysUser;
import org.soul.model.security.privilege.po.SysUserRole;
import org.soul.service.support.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import g.common.security.AuthTool;
import g.model.SubSysCodeEnum;
import g.model.common.notice.enums.ContactWayType;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 子账户视图服务
 *
 * @author eagle
 * @time 2013-03-28
 */
//region your codes 1
public class VSubAccountService extends BaseService<VSubAccountMapper, VSubAccountListVo, VSubAccountVo, VSubAccount, Integer> implements IVSubAccountService {
//endregion your codes 1

    //region your codes 2
    @Autowired
    private AuthTool authTool;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private MSysRoleMapper mSysRoleMapper;
    @Autowired
    private NoticeContactWayMapper noticeContactWayMapper;
    @Autowired
    private SysUserProtectionMapper sysUserProtectionMapper;
    @Autowired
    private VSysRoleMapper vSysRoleMapper;
    @Autowired
    private SysResourceMapper sysResourceMapper;
    @Autowired
    private SysRoleResourceMapper sysRoleResourceMapper;
    @Autowired
    private SysAuditLogMapper sysAuditLogMapper;


    @Override
    public VSubAccountVo get(VSubAccountVo objectVo) {
        Integer userId = objectVo.getSearch().getId();
        String subSysCode = objectVo.getSearch().getSubSysCode();
        objectVo = super.get(objectVo);
        SysUser sysUser = sysUserMapper.get(userId);
        objectVo.setSysUser(sysUser);

        /*联系方式*/
        List list = noticeContactWayMapper.search(Criteria.add(NoticeContactWay.PROP_USER_ID, Operator.EQ, userId));
        objectVo.setNoticeContactWays(list);

        /*安全问题*/
        objectVo.setSysUserProtection(sysUserProtectionMapper.get(userId));
        return preCreateOrEdit(objectVo);
    }

    @Override
    public VSubAccountVo update(VSubAccountVo objectVo) {
        preInsertOrUpdate(objectVo,true);
        SysUser sysUser = objectVo.getSysUser();
        if(StringTool.isNotBlank(sysUser.getPassword())){
            sysUser.setPassword(authTool.md5SysUserPassword(sysUser.getPassword(), sysUser.getUsername()));
        }
        if(StringTool.isNotBlank(sysUser.getPermissionPwd())){
            sysUser.setPermissionPwd(authTool.md5SysUserPermission(sysUser.getPermissionPwd(), sysUser.getUsername()));
        }
        sysUser.setUpdateTime(new Date());
        sysUser.setUpdateUser(objectVo._getUserId());
        sysUserMapper.updateOnly(sysUser, objectVo.getUpdateProperties());
        return objectVo;
    }

    @Override
    public VSubAccountVo insert(VSubAccountVo objectVo) {
        objectVo.getSysUser().setId(objectVo.getSearch().getId());

        SysUser sysUser= objectVo.getSysUser();
        /*是否内置 false*/
        sysUser.setBuiltIn(false);
        /*创建人*/
        SysUser currentUser = objectVo.getCurrentLoginUser();

        /*创建人是子账户*/
        //站长子账号的ownerId为空
        /*if(!UserTypeEnum.MASTER_SUB.getCode().equals(sysUser.getUserType())){
            if(sysUser.getUserType().equals(currentUser.getUserType())){

            *//*owner 就是当前创建人的父级*//*
                //parentUser = sysUserMapper.get(objectVo._getSiteUserId());
                sysUser.setOwnerId(objectVo._getSiteUserId());

            }else {

            *//*创建人 不是子账户*//*
                sysUser.setOwnerId(currentUser.getId());
            }
        }*/

        if(sysUser.getSubsysCode().equals(SubSysCodeEnum.ADMIN.getCode())){
            sysUser.setOwnerId(null);
        }
        sysUser.setCity(currentUser.getCity());
        sysUser.setCountry(currentUser.getCountry());
        sysUser.setCreateUser(currentUser.getId());
        sysUser.setRegion(currentUser.getRegion());
        sysUser.setDefaultLocale(currentUser.getDefaultLocale());
        sysUser.setDefaultCurrency(currentUser.getDefaultCurrency());
        sysUser.setDefaultTimezone(currentUser.getDefaultTimezone());
        if(StringTool.isNotBlank(sysUser.getPassword())){
            sysUser.setPassword(authTool.md5SysUserPassword(sysUser.getPassword(), sysUser.getUsername()));
        }
        if(StringTool.isNotBlank(sysUser.getPermissionPwd())){
            sysUser.setPermissionPwd(authTool.md5SysUserPermission(sysUser.getPermissionPwd(), sysUser.getUsername()));
        }
        sysUserMapper.insert(sysUser);
        objectVo = preInsertOrUpdate(objectVo,false);
        return objectVo;
    }

    public VSubAccountVo preInsertOrUpdate(VSubAccountVo vSubAccountVo,Boolean isUpdate){
        SysUser sysUser = vSubAccountVo.getSysUser();
        Integer userId = sysUser.getId();
        /*密码加密*/
        sysUser.setCreateTime(new Date());
        /*编辑时删除当前用户的 密保问题 联系方式 角色关系表 信息*/
        if(isUpdate){
//          sysUserProtectionMapper.delete(userId); 删除已有的安全问题
            Criteria userIdEQ = Criteria.add(NoticeContactWay.PROP_USER_ID, Operator.EQ, userId);
            Criteria contactTypeIn = Criteria.add(NoticeContactWay.PROP_CONTACT_TYPE, Operator.IN, ListTool.newArrayList(ContactWayType.EMAIL.getCode(), ContactWayType.CELLPHONE.getCode()));
            noticeContactWayMapper.batchDeleteCriteria(Criteria.and(userIdEQ, contactTypeIn));
            sysUserRoleMapper.batchDeleteCriteria(Criteria.add(SysUserRole.PROP_USER_ID, Operator.EQ, userId));

        }
        /*联系方式*/
        noticeContactWayMapper.batchInsert(vSubAccountVo.getInsertNoticeContactWay());

        /*用户角色关联*/
        sysUserRoleMapper.batchInsert(vSubAccountVo.getCurrentRole());

/*      安全问题
        vSubAccountVo.getSysUserProtection().setCreateTime(new Date());
        vSubAccountVo.getSysUserProtection().setTotalValidateCount(1);
        vSubAccountVo.getSysUserProtection().setMatchCount(1);
        if(!StringTool.isEmpty(vSubAccountVo.getSysUserProtection().getAnswer1())){
            vSubAccountVo.getSysUserProtection().setId(userId);
            sysUserProtectionMapper.insert(vSubAccountVo.getSysUserProtection());
        }
*/



        vSubAccountVo.setSysUser(sysUser);
        return  vSubAccountVo;
    }

    @Override
    public VSubAccountVo preCreateOrEdit(VSubAccountVo vSubAccountVo) {
        String subSysCode = vSubAccountVo.getSearch().getSubSysCode();
        /*角色*/
        List<SysRole> sysRoleList = sysRoleMapper.search(Criteria.add(SysRole.PROP_SUBSYS_CODE, Operator.EQ, subSysCode));
        vSubAccountVo.setSysRoles(sysRoleList);

        return vSubAccountVo;
    }

    @Override
    public List<Map<String, Object>> getRoles(VSubAccountListVo vSubAccountListVo) {
        return mapper.getRoles(vSubAccountListVo);
    }

    @Override
    @Transactional
    public VSubAccountVo resetRole(VSubAccountVo vSubAccountVo) {
        VSubAccountSo vSubAccountSo = vSubAccountVo.getSearch();
        List<Integer> sysUserIds = vSubAccountSo.getSysUserIds();
        List<Integer> deleteRoleIds = vSubAccountSo.getDeleteRoleIds();

        if(!CollectionTool.isEmpty(sysUserIds) && !CollectionTool.isEmpty(deleteRoleIds)){
            Criteria userIdIn = Criteria.add(SysUserRole.PROP_USER_ID, Operator.IN, sysUserIds);
            Criteria deleteUserRoleIds = Criteria.add(SysUserRole.PROP_ROLE_ID, Operator.IN, deleteRoleIds);
            /*删除取消的*/
            sysUserRoleMapper.batchDeleteCriteria(Criteria.and(userIdIn, deleteUserRoleIds));
            return vSubAccountVo;
        }
        List<Integer> insertRoleIds = vSubAccountSo.getInsertRoleIds();
        if(!CollectionTool.isEmpty(insertRoleIds)){
            mapper.insertRoleNotExist(MapTool.newHashMap(new Pair<String, Object>("sysRoleIds", insertRoleIds), new Pair<String, Object>("sysUserIds", sysUserIds)));
        }

        return vSubAccountVo;
    }

    @Override
    public VSubAccountVo changeStatusByIds(VSubAccountVo vSubAccountVo) {
        List<Integer> ids = vSubAccountVo.getSearch().getSysUserIds();
        int count = mapper.changeStatusByIds(vSubAccountVo);
        vSubAccountVo.setSuccess(ids.size() == count);
        return vSubAccountVo;
    }

    @Override
    public VSubAccountVo roleResetDefault(VSubAccountVo vSubAccountVo) {
        Integer roleId = vSubAccountVo.getSearch().getRoleId();
        SysRole sysRole= sysRoleMapper.get(roleId);
        if(sysRole.getBuiltIn()){
            /*是系统内置*/
            mapper.resetRole2Default(MapTool.newHashMap(new Pair<String, Object>(SysRoleResource.PROP_ROLE_ID, roleId)));
//            mapper.
            List<Integer> userId = sysUserRoleMapper.searchProperty(Criteria.add(SysUserRole.PROP_ROLE_ID, Operator.EQ, roleId), SysUserRole.PROP_USER_ID);
        }
        return vSubAccountVo;
    }


    @Override
    public SysRoleListVo settingRole(SysRoleListVo sysRoleListVo) {
        List<VSysRole> vSysRoles = vSysRoleMapper.search(Criteria.add(VSysRole.PROP_SUBSYS_CODE, Operator.EQ, sysRoleListVo.getSearch().getSubsysCode()), Order.desc(VSysRole.PROP_ID));
        sysRoleListVo.setvSysRoles(vSysRoles);
        return sysRoleListVo;
    }

    @Override
    public int deleteSubAccounts(VSubAccountVo vSubAccountVo) {
        noticeContactWayMapper.batchDeleteCriteria(Criteria.add(NoticeContactWay.PROP_USER_ID, Operator.IN, vSubAccountVo.getSearch().getSysUserIds()));
        sysUserRoleMapper.batchDeleteCriteria(Criteria.add(SysUserRole.PROP_USER_ID, Operator.IN, vSubAccountVo.getSearch().getSysUserIds()));
        return sysUserMapper.batchDelete(vSubAccountVo.getSearch().getSysUserIds());
    }

    @Override
    public List<Integer> searchRoleIds(SysRoleListVo sysRoleListVo) {
        return sysUserRoleMapper.searchProperty(Criteria.add(SysUserRole.PROP_USER_ID, Operator.EQ, sysRoleListVo.getUserId()), SysUserRole.PROP_ROLE_ID);
    }

    @Override
    public SysAuditLogListVo queryLogs(SysAuditLogListVo listVo) {
        SysAuditLogSo logSo = listVo.getSearch();
        Criteria time = Criteria.and(
                Criteria.add(SysAuditLog.PROP_OPERATE_TIME, Operator.GE, logSo.getOperatorBegin()),
                Criteria.add(SysAuditLog.PROP_OPERATE_TIME, Operator.LT, logSo.getOperatorEnd())
        );
        Criteria entityUserId = Criteria.add(SysAuditLog.PROP_ENTITY_USER_ID, Operator.EQ, logSo.getEntityUserId());
        Criteria moduleType = Criteria.add(SysAuditLog.PROP_MODULE_TYPE, Operator.EQ, logSo.getModuleType());
        Criteria operateType = Criteria.add(SysAuditLog.PROP_OPERATE_TYPE, Operator.EQ, logSo.getOperateType());
        Criteria operateId = Criteria.add(SysAuditLog.PROP_OPERATOR_ID, Operator.EQ, logSo.getOperatorId());
        Criteria operator = Criteria.add(SysAuditLog.PROP_OPERATOR, Operator.EQ, logSo.getOperator());
        Criteria operatorUserType = Criteria.add(SysAuditLog.PROP_OPERATOR_USER_TYPE,Operator.EQ,logSo.getOperatorUserType());
        long ip = IpTool.ipv4StringToLong(logSo.getIp());
        Criteria operateIp = Criteria.add(SysAuditLog.PROP_OPERATE_IP, Operator.EQ, ip == -1 ? null : ip);
        Criteria criteria = Criteria.and(
                time,
                entityUserId,
                moduleType,
                operateId,
                operator,
                operateIp,
                operateType,
                operatorUserType
        );
        List<SysAuditLog> logs = this.sysAuditLogMapper.pagingSearch(criteria, listVo.getPaging().getPageNumber(), listVo.getPaging().getPageSize(), Order.desc(SysAuditLog.PROP_OPERATE_TIME));
        listVo.setResult(logs);
        listVo.getPaging().setTotalCount(this.sysAuditLogMapper.count(criteria));
        listVo.getPaging().cal();
        return listVo;
    }
//endregion your codes 2

}