package g.web.api.model;

/**
 * Created by lenovo on 2016/11/28.
 */
public class ApiListResponse extends ApiResponse {

    private int pageTotalSize;

    private int pageNo;

    private int pageSize;

    public int getPageTotalSize() {
        return pageTotalSize;
    }

    public void setPageTotalSize(int pageTotalSize) {
        this.pageTotalSize = pageTotalSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
