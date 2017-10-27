package g.web.common.passport;

import org.soul.commons.enums.YesNot;
import org.soul.commons.init.context.CommonContext;
import org.soul.commons.init.context.Const;
import org.soul.commons.init.context.Operator;
import org.soul.commons.lang.DateTool;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.soul.commons.support.IModuleTool;
import org.soul.iservice.log.audit.IAuditService;
import org.soul.iservice.security.privilege.ISysUserService;
import org.soul.iservice.sys.ISysAuditLogService;
import org.soul.model.log.audit.enums.OpType;
import org.soul.model.log.audit.po.IAuditEntity;
import org.soul.model.security.privilege.po.SysUser;
import org.soul.model.security.privilege.vo.SysUserVo;
import org.soul.model.sys.po.SysAuditLog;
import org.soul.model.sys.vo.SysAuditLogVo;
import org.soul.web.log.audit.AuditLogTool;
import org.soul.web.session.SessionManagerBase;
import org.soul.web.shiro.common.delegate.IPassportListener;
import org.soul.web.shiro.common.delegate.PassportEvent;
import g.model.Module;
import g.model.ModuleType;
import g.model.common.FreezeType;

import java.util.Date;

/**
 * Created by longer on 9/1/15.
 * 通行证监听
 */
public class DefaultPassportListener implements IPassportListener {

	private static final Log log = LogFactory.getLog(DefaultPassportListener.class);

	private IAuditService auditService;

	private ISysUserService sysUserService;

	private ISysAuditLogService sysAuditLogService;


    @Override
    public void onLoginSuccess(PassportEvent passportEvent) {
		try {
			//i18n
			doSysAuditLog(ModuleType.PASSPORT_LOGIN,
					passportEvent.getOperator(),
					Module.Passport_Login,
					OpType.LOGIN,
					PassportConst.I18N_LOGIN_SUCCESS,passportEvent.isMaster());
		} catch (Exception e) {
			log.error(e,"登录成功的错误日志记录异常!");
		}

    }

	@Override
    public void onLoginFailure(PassportEvent passportEvent) {
        if (passportEvent.isNeedFreeze()){
			doFreezon(passportEvent);
        }
		try {
			//i18n
			doSysAuditLog(ModuleType.PASSPORT_LOGIN_FAIL,
					passportEvent.getOperator(),
					Module.Passport_Login,
					OpType.LOGIN,
					PassportConst.I18N_LOGIN_FAIL, passportEvent.isMaster());
		} catch (Exception e) {
			log.error(e,"登录失败的错误日志记录异常!");
		}

    }

	@Override
	public void onLogoutSuccess(PassportEvent passportEvent) {
		try {
			//i18n
			doSysAuditLog(ModuleType.PASSPORT_LOGOUT,
					passportEvent.getOperator(),
					Module.Passport_Logout,
					OpType.LOGOUT,
					PassportConst.I18N_LOGOUT_SUCCESS,passportEvent.isMaster());
		} catch (Exception e) {
			log.error(e,"登录成功的错误日志记录异常!");
		}
	}

	/**
	 * 账号自动冻结
	 * @param passportEvent
	 */
	private void doFreezon(PassportEvent passportEvent) {
		Operator operator =passportEvent.getOperator();
		Integer operatorId = operator.getOperatorId();
		if (operatorId == null) {
			log.error("单点登录:需要冻结用户名:[{0}],但用户id为空",operatorId);
			return;
		}
		Date now = new Date();
		SysUserVo sysUserVo = new SysUserVo();
		if(passportEvent.isMaster()){//站长账号保存在运营商库
			sysUserVo._setDataSourceId(SessionManagerBase.getSiteParentId());
		}
		SysUser sysUser = new SysUser();
		sysUserVo.setResult(sysUser);
		sysUser.setId(operatorId);
		sysUser.setFreezeCode("2b801fbd304e4b518393bdb9bf1d6c24");//默认值
		sysUser.setFreezeStartTime(now);
		sysUser.setFreezeEndTime(DateTool.addHours(now,PassportConst.AUTO_FREEZE_HOURS));
		sysUser.setFreezeType(FreezeType.AUTO.getCode());
		sysUserVo.setProperties(SysUser.PROP_FREEZE_CODE,SysUser.PROP_FREEZE_TYPE,SysUser.PROP_FREEZE_START_TIME,SysUser.PROP_FREEZE_END_TIME);
		getSysUserService().updateOnly(sysUserVo);
	}

	/**
	 * 审计日志:错误日志
	 * 		日志先统一走MQ,再存储到业务表
	 * @param operator
	 * @param isMaster
	 */
    private void doSysAuditLog(ModuleType moduleType,
							   final Operator operator,
							   Module module,
							   OpType opType,
							   String message,
							   boolean isMaster) {
		IAuditEntity entity = new IAuditEntity() {
			@Override
			public Long getEntityId() {
				return operator.getOperatorId() == null ? null :new Long(operator.getOperatorId());
			}
		};
		SysAuditLog sysAuditLog = AuditLogTool.buildAuditLog(entity, operator, YesNot.YES);
		sysAuditLog.setEntityId(operator.getOperatorId() == null ? null : Long.valueOf(operator.getOperatorId()));
		sysAuditLog.setEntityUserId(operator.getOperatorId());
		sysAuditLog.setEntityUsername(operator.getOperator());
		sysAuditLog.setModuleType(moduleType.getCode());
		sysAuditLog.setOperateType(opType.getTrans());
		sysAuditLog.setOperateTypeId(Integer.valueOf(opType.getCode()));
		sysAuditLog.setModuleName(IModuleTool.getModuleRecursive(module));
		sysAuditLog.setModuleObj(module.getCode());
		sysAuditLog.setDescription(message);
		auditService.submit(sysAuditLog);

		try {
			SysAuditLogVo vo = new SysAuditLogVo();
			vo.setResult(sysAuditLog);
			if (isMaster) {
				//站长:存储在mainsite
				vo._setDataSourceId(CommonContext.get().getSiteParentId());
				sysAuditLogService.insert(vo);
				vo._setDataSourceId(null);
				vo.getResult().setEntityId(Long.valueOf(Const.MASTER_BUILT_IN_ID));
				vo.getResult().setOperatorId(Const.MASTER_BUILT_IN_ID);
			}
			sysAuditLogService.insert(vo);

		} catch (Exception e) {
			log.error(e,"保存登录业务日志异常!");
		}
    }

	private ISysUserService getSysUserService(){
		return sysUserService;
	}

	public IAuditService getAuditService() {
		return auditService;
	}

	public void setAuditService(IAuditService auditService) {
		this.auditService = auditService;
	}

	public void setSysUserService(ISysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	public ISysAuditLogService getSysAuditLogService() {
		return sysAuditLogService;
	}

	public void setSysAuditLogService(ISysAuditLogService sysAuditLogService) {
		this.sysAuditLogService = sysAuditLogService;
	}
}
