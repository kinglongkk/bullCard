package g.model.player;

import java.io.Serializable;

/**
 * Created by longer on 4/18/16.
 */
public class Page implements Serializable{

    /**
     * 总页数
     */
    private Integer totalPage;

    /**
     * 当前页
     */
    private int pageNo;

    public Page(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
}
