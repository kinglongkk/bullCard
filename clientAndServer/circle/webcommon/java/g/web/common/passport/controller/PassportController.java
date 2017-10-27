package g.web.common.passport.controller;

import g.web.common.shiro.common.delegate.IPassportDelegate;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.soul.model.passport.vo.PassportVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by longer on 12/18/15.
 */
/**
 * Created by longer on 43015.
 * 通行证
 */
@Controller
@RequestMapping("/**/passport")
public class PassportController {

    @Value("${subsys.code}")
    private String              subsysCode;
    private static final String LOGIN_URI = "/passport/login";
    private static final String LOGOUT_URI = "/passport/logout";

    @Autowired(required = false)
    private IPassportDelegate passportDelegate;

    /**
     * 登录
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request,HttpServletResponse response){
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return "redirect:/";
        }
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        Integer entranceId = passportDelegate.getEntranceType(contextPath,uri);
        if (entranceId == null) {
            entranceId = 0;
        }
        String loginUrl = passportDelegate.getLoginUrl(entranceId);
        String entranceDesc = entranceDesc(entranceId);
        request.setAttribute("loginUrl",loginUrl);
        request.setAttribute("subsysCode",subsysCode);
        request.setAttribute("entranceDesc",entranceDesc);
        return LOGIN_URI;
    }

    /**
     * 登录
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login_post(){
        return "/index";
    }

    /**
     * 退出
     * @return
     */
    @RequestMapping(value = "/logout")
    public String logout(){
        return LOGOUT_URI;
    }


    /**
     * 开发临时显示使用
     * @param entranceId
     * @return
     */
    private String entranceDesc(Integer entranceId) {
        String type = "";
        if (entranceId == PassportVo.NORMAL) {
            type = "管理后台";
        } else if (entranceId == PassportVo.AGENT) {
            type = "代理后台";
        } else if (entranceId == PassportVo.PLAYER) {
            type = "玩家中心";
        } else {
            type = "未知登录页";
        }
        return type;
    }

    /**
     * 通行证回调
     * @return
     */
    @RequestMapping(value = "/callback")
    public String callback() {
        return "passport/callback";
    }
}
