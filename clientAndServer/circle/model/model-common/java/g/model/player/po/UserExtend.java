package g.model.player.po;

import org.soul.commons.bean.IEntity;

/**
 * Created by mark on 16-4-1.
 */
public class UserExtend implements IEntity<Integer> {

    public static final String PROP_ID = "id";
    public static final String PROP_THEME_ID = "theme_id";
    public static final String PROP_REFERRALS = "referrals";
    public static final String PROP_LUCENCY = "lucency";
    public static final String PROP_WALLET_BALANCE = "wallet_balance";



    private Integer id;
    /**
     * 主题id
     */
    private Integer themeId;
    /**
     * 介绍人
     */
    private String referrals;
    private Integer lucency;
    /**
     * 钱包余额
     */
    private Double walletBalance;
    @Override
    public Integer getId() {
        return null;
    }

    @Override
    public void setId(Integer id) {

    }

    public Integer getThemeId() {
        return themeId;
    }

    public void setThemeId(Integer themeId) {
        this.themeId = themeId;
    }

    public String getReferrals() {
        return referrals;
    }

    public void setReferrals(String referrals) {
        this.referrals = referrals;
    }

    public Integer getLucency() {
        return lucency;
    }

    public void setLucency(Integer lucency) {
        this.lucency = lucency;
    }

    public Double getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(Double walletBalance) {
        this.walletBalance = walletBalance;
    }
}
