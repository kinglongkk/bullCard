package g.model.api.param;

/**
 * Created by tony on 2016/12/3.
 * 分页参数
 */
public class PageParams extends Params{

    /**
     * 分页 每页显示条数
     */
    private Integer pageSize;
    /**
     * 分页 当前页
     */
    private Integer pageNo;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }
}
