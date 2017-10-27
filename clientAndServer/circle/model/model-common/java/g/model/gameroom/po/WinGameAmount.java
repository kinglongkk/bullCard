package g.model.gameroom.po;

import org.soul.commons.bean.IEntity;

/**
 * Created by lenovo on 2017/3/3.
 */
public class WinGameAmount implements IEntity<Integer> {

    private static final long serialVersionUID = 1565437168568091029L;
    public static final String PROP_ID = "id";
    public static final String PROP_WINAMOUNT = "winamount";
    private Integer id;
    private Double winamount;
    public WinGameAmount(){
    }
    public WinGameAmount(Integer id){
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Double getWinamount() {
        return winamount;
    }

    public void setWinamount(Double winamount) {
        this.winamount = winamount;
    }
}
