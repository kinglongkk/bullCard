package g.service.common;

import g.data.admin.SysAnnouncementMapper;
import g.model.ModuleType;
import org.soul.commons.bean.Pair;
import org.soul.commons.collections.CollectionTool;
import org.soul.commons.collections.ListTool;
import org.soul.commons.collections.MapTool;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.commons.query.sort.Order;
import org.soul.data.mapper.msg.notice.NoticeContactWayMapper;
import org.soul.data.mapper.security.privilege.SysUserMapper;
import org.soul.data.mapper.security.privilege.SysUserProtectionMapper;
import org.soul.data.mapper.sys.SysAuditLogMapper;
import org.soul.model.msg.notice.po.NoticeContactWay;
import org.soul.model.security.privilege.po.SysUser;
import org.soul.model.security.privilege.po.SysUserProtection;
import org.soul.model.security.privilege.vo.SysUserVo;
import org.soul.model.sys.po.SysAuditLog;
import org.soul.service.security.privilege.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import g.model.UserTypeEnum;
import g.model.common.notice.enums.ContactWayStatus;
import g.model.admin.vo.MyAccountVo;
import g.model.player.vo.VSysUserVo;

import java.lang.reflect.Array;
import java.util.*;

/**
 * 运营商我的账号视图服务
 *
 * @author jeff
 * @time 2015-11-20 10:49:52
 */
//region your codes 1
public class MyAccountService extends SysUserService implements IMyAccountService {
//endregion your codes 1

    //region your codes 2
    @Autowired
    private NoticeContactWayMapper noticeContactWayMapper;
    @Autowired
    private SysUserProtectionMapper sysUserProtectionMapper;
    @Autowired
    private SysAuditLogMapper sysAuditLogMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysAnnouncementMapper sysAnnouncementMapper;


    @Override
    public MyAccountVo getMyAccount(MyAccountVo myAccountVo) {

        /**
         * 用户id
         */
        Integer userId = null;
        userId = myAccountVo._getUserId();

        /**
         * 用户信息
         */
        myAccountVo.setResult(this.mapper.get(userId));

        /**
         * 联系方式信息
         */
        myAccountVo.setNoticeContactWays(noticeContactWayMapper.search(Criteria.add(NoticeContactWay.PROP_USER_ID, Operator.EQ, userId)));

        /**
         * 安全问题信息
         */
        myAccountVo.setSysUserProtection(sysUserProtectionMapper.get(userId));

        /**
         * 登录日志
         */
        Criteria logCriteria = Criteria.add(SysAuditLog.PROP_ENTITY_USER_ID, Operator.EQ, userId).addAnd(Criteria.add(SysAuditLog.PROP_MODULE_TYPE, Operator.EQ, ModuleType.PASSPORT_LOGIN.getCode()));
        myAccountVo.setSysAuditLogs(sysAuditLogMapper.pagingSearch(logCriteria, 1, 10, Order.desc(SysAuditLog.PROP_OPERATE_TIME)));
        myAccountVo.setCountLogNumber(sysAuditLogMapper.count(logCriteria));

        return myAccountVo;
    }

    @Override
    public MyAccountVo edit(MyAccountVo myAccountVo){

        SysUser sysUser = myAccountVo.getResult();
        Integer userId = myAccountVo.getResult().getId();

        //新增 编辑 用户信息
        if(userId == null){

            mapper.insert(sysUser);
        }else{

            boolean update = this.mapper.updateOnly(sysUser, myAccountVo._getSysUserUpdateProperties());
            noticeContactWayMapper.batchDeleteCriteria(Criteria.add(NoticeContactWay.PROP_USER_ID,Operator.EQ,userId));
            sysUserProtectionMapper.delete(userId);
        }

        //删掉旧的联系方式
        //编辑/新增的 联系方式
        List<NoticeContactWay> noticeContactWays = ListTool.newArrayList(myAccountVo.getEmail(), myAccountVo.getQq(), myAccountVo.getMsn(), myAccountVo.getMobilePhone(), myAccountVo.getSkype());
        List<NoticeContactWay> inserNoticeContactWays = new ArrayList(5);

        for (int i = 0;i<noticeContactWays.size();i++){

            if(noticeContactWays.get(i)!=null && noticeContactWays.get(i).getContactValue() !=null){

                inserNoticeContactWays.add(noticeContactWays.get(i));
            }
        }
        if(!inserNoticeContactWays.isEmpty()){

            CollectionTool.batchUpdate(inserNoticeContactWays, MapTool.newHashMap(new Pair<String, Object>(NoticeContactWay.PROP_USER_ID, userId), new Pair<String, Object>(NoticeContactWay.PROP_STATUS, ContactWayStatus.NORMAL.getCode())));
            noticeContactWayMapper.batchInsert(inserNoticeContactWays);
        }
        SysUserProtection p = myAccountVo.getSysUserProtection();
        if(StringTool.isNotBlank(p.getAnswer1()) && StringTool.isNotBlank(p.getQuestion1())){

            p.setId(userId);
            p.setCreateTime(new Date());
            p.setTotalValidateCount(1);
            p.setMatchCount(1);
            sysUserProtectionMapper.insert(myAccountVo.getSysUserProtection());

        }

        return myAccountVo;
    }


    @Override
    public List<Integer> searchAddPlayer(SysUserVo vo) {

        Criteria criteria = Criteria.add(SysUser.PROP_CREATE_TIME, Operator.GE, vo.getSearch().getCreateTime());
        criteria.addAnd(SysUser.PROP_USER_TYPE, Operator.EQ, UserTypeEnum.PLAYER.getCode());

        return sysUserMapper.searchProperty(criteria, "id");
    }


    public List searchPlayerCountBalance(VSysUserVo vSysUserVo) {

        Map map = new HashMap(1);
        map.put("playerId",vSysUserVo.getSearch().getId());

        return sysAnnouncementMapper.searchPlayerCountBalance(map);
    }


    public Double selectPreOrderMoney(Integer ownerId){

        return sysAnnouncementMapper.selectPreOrderMoney(ownerId);
    }
    //endregion your codes 2

}