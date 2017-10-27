package g.service.admin;

import g.common.security.AuthTool;
import g.data.agent.VAgentManageMapper;
import g.model.agent.po.VAgentManage;
import g.model.agent.vo.VAgentManageListVo;
import g.model.agent.vo.VAgentManageVo;
import g.model.common.notice.enums.ContactWayStatus;
import g.model.common.notice.enums.ContactWayType;
import org.soul.commons.collections.CollectionTool;
import org.soul.commons.collections.ListTool;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.commons.query.sort.Order;
import org.soul.data.mapper.msg.notice.NoticeContactWayMapper;
import org.soul.data.mapper.security.privilege.SysUserMapper;
import org.soul.data.mapper.sys.SysAuditLogMapper;
import org.soul.model.msg.notice.po.NoticeContactWay;
import org.soul.model.security.privilege.po.SysUser;
import org.soul.model.sys.po.SysAuditLog;
import org.soul.model.sys.vo.SysAuditLogListVo;
import org.soul.service.support.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: tom
 * @date: 16-4-12
 */
@Service
public class VAgentManageService extends BaseService<VAgentManageMapper, VAgentManageListVo, VAgentManageVo, VAgentManage, Integer> implements IVAgentManageService {
    @Autowired
    private AuthTool authTool;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private NoticeContactWayMapper noticeContactWayMapper;

    @Autowired
    private SysAuditLogMapper sysAuditLogMapper;

    @Override
    @Transactional
    public VAgentManageVo insert(VAgentManageVo objectVo) {
        SysUser sysUser= objectVo.getSysUser();
        sysUser.setPassword(authTool.md5SysUserPassword(sysUser.getPassword(), sysUser.getUsername()));
        boolean insertSuccess = this.sysUserMapper.insert(sysUser);
        if (insertSuccess)
            insertSuccess = saveNoticeContractWays(objectVo,sysUser.getId());
        if (insertSuccess) {
        }
        objectVo.setSuccess(insertSuccess);
        return objectVo;
    }

    @Override
    @Transactional
    public VAgentManageVo updateAgent(VAgentManageVo objectVo) {
        SysUser sysUser = objectVo.getSysUser();
        this.sysUserMapper.updateOnly(sysUser, new String[]{
            SysUser.PROP_REAL_NAME,
            SysUser.PROP_NICKNAME,
            SysUser.PROP_STATUS,
            SysUser.PROP_USER_TYPE,
            SysUser.PROP_SEX,
            SysUser.PROP_UPDATE_USER,
            SysUser.PROP_UPDATE_TIME,
            SysUser.PROP_COUNTRY,
            SysUser.PROP_NATION
        });

        Criteria userIdEQ = Criteria.add(NoticeContactWay.PROP_USER_ID, Operator.EQ, sysUser.getId());
        Criteria contactTypeIn = Criteria.add(
                NoticeContactWay.PROP_CONTACT_TYPE, Operator.IN,
                ListTool.newArrayList(ContactWayType.EMAIL.getCode(), ContactWayType.CELLPHONE.getCode(), ContactWayType.SKYPE.getCode()));
        this.noticeContactWayMapper.batchDeleteCriteria(Criteria.and(userIdEQ, contactTypeIn));
        saveNoticeContractWays(objectVo, sysUser.getId());
//        editBetLimit(objectVo);

        objectVo.setSuccess(true);
        return objectVo;
    }

    @Override
    public VAgentManageVo editAgent(VAgentManageVo vo) {
        SysUser sysUser = this.sysUserMapper.get(vo.getSearch().getId());
        vo.setSysUser(sysUser);
        List<NoticeContactWay> emailList = this.noticeContactWayMapper.search(Criteria.add(NoticeContactWay.PROP_USER_ID, Operator.EQ, vo.getSearch().getId())
                .addAnd(NoticeContactWay.PROP_CONTACT_TYPE, Operator.EQ, ContactWayType.EMAIL.getCode()));
        if (CollectionTool.isNotEmpty(emailList))
            vo.setEmail(emailList.get(0));
        List<NoticeContactWay> phoneList = this.noticeContactWayMapper.search(Criteria.add(NoticeContactWay.PROP_USER_ID, Operator.EQ, vo.getSearch().getId())
                .addAnd(NoticeContactWay.PROP_CONTACT_TYPE, Operator.EQ, ContactWayType.CELLPHONE.getCode()));
        if (CollectionTool.isNotEmpty(phoneList))
            vo.setMobilePhone(phoneList.get(0));
        List<NoticeContactWay> skypeList = this.noticeContactWayMapper.search(Criteria.add(NoticeContactWay.PROP_USER_ID, Operator.EQ, vo.getSearch().getId())
                .addAnd(NoticeContactWay.PROP_CONTACT_TYPE, Operator.EQ, ContactWayType.SKYPE.getCode()));
        if (CollectionTool.isNotEmpty(skypeList))
            vo.setSkype(skypeList.get(0));
        return vo;
    }

    @Override
    public SysAuditLogListVo searchLoginLog(SysAuditLogListVo vo) {
        List<SysAuditLog> result = this.sysAuditLogMapper.pagingSearch(vo.getQuery().byModuleTypeAndOperatorId(), vo.getPaging().getPageNumber(), vo.getPaging().getPageSize(), new Order[]{Order.desc("operateTime")});

        long count = this.sysAuditLogMapper.count(vo.getQuery().byModuleTypeAndOperatorId());
        vo.getPaging().setTotalCount((int) count);
        vo.getPaging().cal();
        vo.setResult(result);
        return vo;
    }


    private boolean saveNoticeContractWays(VAgentManageVo objectVo,Integer sysUserId) {
        List<NoticeContactWay> noticeContactWays = new ArrayList<>();
        NoticeContactWay email = objectVo.getEmail();
        if (email != null) {
            email.setStatus(ContactWayStatus.NORMAL.getCode());
            email.setContactType(ContactWayType.EMAIL.getCode());
            email.setUserId(sysUserId);
            noticeContactWays.add(email);
        }

        NoticeContactWay phone = objectVo.getMobilePhone();
        if (phone != null) {
            phone.setStatus(ContactWayStatus.NORMAL.getCode());
            phone.setContactType(ContactWayType.CELLPHONE.getCode());
            phone.setUserId(sysUserId);
            noticeContactWays.add(phone);
        }

        NoticeContactWay skype = objectVo.getSkype();
        if (skype != null) {
            skype.setStatus(ContactWayStatus.NORMAL.getCode());
            skype.setContactType(ContactWayType.SKYPE.getCode());
            skype.setUserId(sysUserId);
            noticeContactWays.add(skype);
        }

        if (noticeContactWays.size() > 0)
            return this.noticeContactWayMapper.batchInsert(noticeContactWays)==noticeContactWays.size();
        else
            return true;
    }
}
