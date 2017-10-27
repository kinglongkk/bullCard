package g.web.common.filter;

import org.soul.commons.lang.string.StringTool;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by tony on 15-8-11.
 */
public class PrivilegeThread extends Thread{
    private AsyncContext syncContext;

    public PrivilegeThread(AsyncContext syncContext){
        this.syncContext = syncContext;
    }

    public void run(){
        HttpServletResponse resp = (HttpServletResponse) syncContext.getResponse();
        PrintWriter out = null;
        try {
            out = resp.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //resp.setHeader("Transfer-Encoding", "chunked");
        //火狐下flush的问题
        out.write("<script>window.top.topPage.checkPrivilege({owner:window,type:1});</script>");
        out.flush();
        out.write(StringTool.leftPad("", 1024, " "));
        out.flush();
        syncContext.complete();
    }
}