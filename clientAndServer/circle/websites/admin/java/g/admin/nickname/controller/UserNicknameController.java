package g.admin.nickname.controller;

import org.soul.web.controller.BaseCrudController;
import g.service.admin.nickname.IUserNicknameService;
import g.model.admin.nickname.po.UserNickname;
import g.model.admin.nickname.vo.UserNicknameListVo;
import g.model.admin.nickname.vo.UserNicknameVo;
import g.admin.nickname.form.UserNicknameSearchForm;
import g.admin.nickname.form.UserNicknameForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 昵称控制器
 *
 * @author black
 * @time 2017-2-8 15:41:50
 */
@Controller
//region your codes 1
@RequestMapping("/userNickname")
public class UserNicknameController extends BaseCrudController<IUserNicknameService, UserNicknameListVo, UserNicknameVo, UserNicknameSearchForm, UserNicknameForm, UserNickname, Integer> {
//endregion your codes 1

    @Override
    protected String getViewBasePath() {
        //region your codes 2
        return "/nickname/";
        //endregion your codes 2
    }

    //region your codes 3

    //endregion your codes 3

}