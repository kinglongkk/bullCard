package g.web.common.sysresource.controller;

import org.soul.commons.data.json.JsonTool;
import org.soul.commons.init.context.CommonContext;
import org.soul.commons.tree.TreeNode;
import org.soul.model.security.privilege.po.VSysUserResource;
import org.soul.model.security.privilege.vo.SysResourceVo;
import org.springframework.stereotype.Controller;
import g.model.UserTypeEnum;
import g.web.common.SessionManagerCommon;

import java.text.MessageFormat;
import java.util.List;


/**
 * Created by tony on 15-4-29.
 */
@Controller
public class SysResourceController extends org.soul.web.security.privilege.controller.SysResourceController {

    @Override
    public String fetchRootMenus() {
        SysResourceVo o = new SysResourceVo();
        o._setSiteId(CommonContext.get().getSiteId());
        UserTypeEnum userType = UserTypeEnum.enumOf(SessionManagerCommon.getUser().getUserType());
        if (userType == null) {
            throw new NullPointerException(MessageFormat.format("站点ID:[{0}],用户ID:[{0}]的用户类型不能为空!",SessionManagerCommon.getSiteId(),SessionManagerCommon.getUserId()));
        }
        if (!UserTypeEnum.isWholePerimssion(userType)) {
            o.getSearch().setUserId(SessionManagerCommon.getUserId());
        }
        o.getSearch().setSubsysCode(baseWebConf.getSubsysCode());
        List<TreeNode<VSysUserResource>> menuNodeList = getService().getRootMenus(o);
        loadLocal(menuNodeList);
        return JsonTool.toJson(menuNodeList);
    }
}
