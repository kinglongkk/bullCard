package g.service.chesscard.netBeans.common;

import g.service.webSocket.codeCreator.NetBean;
import g.service.webSocket.codeCreator.NetBeanField;

/**
 * 游戏公告
 * Created by black on 2017/2/25.
 */
@NetBean(sendBy = NetBean.SENDBY_SERVER)
public class NbGameAnnouncement extends NbOut {

    /** 通知方式 */
    @NetBeanField
    public String announcementType;

    /** 标题 */
    @NetBeanField
    public String title;

    /** 内容 */
    @NetBeanField
    public String content;

    public String getAnnouncementType() {
        return announcementType;
    }

    public void setAnnouncementType(String announcementType) {
        this.announcementType = announcementType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
