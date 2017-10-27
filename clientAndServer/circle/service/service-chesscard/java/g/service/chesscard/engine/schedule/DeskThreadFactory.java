package g.service.chesscard.engine.schedule;

/**
 * Created by Double on 2016/11/29.
 * 桌子线程生成器,以桌号为标识
 */
public class DeskThreadFactory extends NamedThreadFactory {

    private int deskId;

    public DeskThreadFactory(int deskId) {
        super(THREAD_DESK);
        this.deskId = deskId;
    }

    @Override
    protected String getPrefix() {
        return super.getPrefix() + "-" + deskId;
    }
}
