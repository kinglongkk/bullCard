package g.model.poker;

import org.soul.commons.enums.EnumTool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 2016-08-25.
 */
public enum PokerEnum implements IPokerEnum {

    DIAMOND_A(0,"0","方片A"),
    DIAMOND_2(4,"4","方片2"),
    DIAMOND_3(8,"8","方片3"),
    DIAMOND_4(12,"12","方片4"),
    DIAMOND_5(16,"16","方片5"),
    DIAMOND_6(20,"20","方片6"),
    DIAMOND_7(24,"24","方片7"),
    DIAMOND_8(28,"28","方片8"),
    DIAMOND_9(32,"32","方片9"),
    DIAMOND_10(36,"36","方片10"),
    DIAMOND_J(40,"40","方片J"),
    DIAMOND_Q(44,"44","方片Q"),
    DIAMOND_K(48,"48","方片K"),
    CLUB_A(1,"1","梅花A"),
    CLUB_2(5,"5","梅花2"),
    CLUB_3(9,"9","梅花3"),
    CLUB_4(13,"13","梅花4"),
    CLUB_5(17,"17","梅花5"),
    CLUB_6(21,"21","梅花6"),
    CLUB_7(25,"25","梅花7"),
    CLUB_8(29,"29","梅花8"),
    CLUB_9(33,"33","梅花9"),
    CLUB_10(37,"37","梅花10"),
    CLUB_J(41,"41","梅花J"),
    CLUB_Q(45,"45","梅花Q"),
    CLUB_K(49,"49","梅花K"),
    HEART_A(2,"2","红桃A"),
    HEART_2(6,"6","红桃2"),
    HEART_3(10,"10","红桃3"),
    HEART_4(14,"14","红桃4"),
    HEART_5(18,"18","红桃5"),
    HEART_6(22,"22","红桃6"),
    HEART_7(26,"26","红桃7"),
    HEART_8(30,"30","红桃8"),
    HEART_9(34,"34","红桃9"),
    HEART_10(38,"38","红桃10"),
    HEART_J(42,"42","红桃J"),
    HEART_Q(46,"46","红桃Q"),
    HEART_K(50,"50","红桃K"),
    SPADE_A(3,"3","黑桃A"),
    SPADE_2(7,"7","黑桃2"),
    SPADE_3(11,"11","黑桃3"),
    SPADE_4(15,"15","黑桃4"),
    SPADE_5(19,"19","黑桃5"),
    SPADE_6(23,"23","黑桃6"),
    SPADE_7(27,"27","黑桃7"),
    SPADE_8(31,"31","黑桃8"),
    SPADE_9(35,"35","黑桃9"),
    SPADE_10(39,"39","黑桃10"),
    SPADE_J(43,"43","黑桃J"),
    SPADE_Q(47,"47","黑桃Q"),
    SPADE_K(51,"51","黑桃K"),
    ;


    private Integer num;
    private String code;
    private String trans;

    PokerEnum(Integer num,String code, String trans) {
        this.num = num;
        this.code = code;
        this.trans = trans;
    }

    @Override
    public Integer getNum() {
        return num;
    }

    public String getTrans() {
        return trans;
    }

    public String getCode() {
        return code;
    }

    public static PokerEnum enumOf(String code) {
        return EnumTool.enumOf(PokerEnum.class, code);
    }
    public static PokerEnum enumOf(Integer num) {
        return EnumTool.enumOf(PokerEnum.class, ""+num);
    }

}
