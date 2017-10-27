package g.web.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 管理首页
 * <p/>
 * Created by cheery on 15-8-27.
 */
@Controller
@RequestMapping("/home")
public class HomeController{
    //首页
    public static final String INDEX_URI = "/home/Index";

    /**
     * 管理首页
     *
     * @return
     */
    @RequestMapping("/homeIndex")
    public String homeIndex() {
        //左侧菜单栏
        return INDEX_URI;
    }

}
