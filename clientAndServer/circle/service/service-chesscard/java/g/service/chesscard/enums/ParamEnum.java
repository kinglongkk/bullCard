package g.service.chesscard.enums;


import g.model.Module;
import org.soul.commons.param.IParamEnum;
import org.soul.commons.support.IModule;

/**
 * Created by longer on 2017/1/10.
 */
public enum ParamEnum implements IParamEnum {

    BULL_100_BEI(Module.BULL, "100_bei", ""), //牛牛 百人 倍数
    BULL_BAO_BEI(Module.BULL, "bao_bei", ""), //牛牛 押宝 倍数
    ;

    private IModule module;
    private String type;
    private String code;

    ParamEnum(IModule module, String type, String code) {
        this.module = module;
        this.type = type;
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public IModule getModule() {
        return module;
    }

    @Override
    public String getType() {
        return type;
    }

}
