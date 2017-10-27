package g.model.api.result;

import java.util.List;

/**
 * 返回list的结果集
 * Created by tony on 2016/11/25.
 */
public class ListResults extends Results {

    /**
     * 总条数
     */
    private int pageTotalSize;
    /**
     * 当前页
     */
    private int pageNo;
    /**
     * 每页显示条数
     */
    private int pageSize;
    /**
     * list数据
     */
    private List datas;

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

    public List getDatas() {
        return datas;
    }

    public void setDatas(List datas) {
        this.datas = datas;
    }

    @Override
    public Object getFinalResults() {
        return datas;
    }
}
