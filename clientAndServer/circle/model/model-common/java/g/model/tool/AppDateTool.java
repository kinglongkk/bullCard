package g.model.tool;

import g.model.common.Const;
import org.soul.commons.lang.DateTool;
import org.soul.commons.lang.string.StringTool;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by longer on 6/22/16.
 */
public class AppDateTool {

    public static final String FMT_CLN_MINUTE            = "HH:mm";

    private static List<String> dates(){
        Date now = new Date();
        List<String> dates = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            Date nextDay = DateTool.addDays(now,i+1);
            String date = DateTool.formatDate(nextDay, Const.Default_Locale, Const.TimeZone_US, DateTool.FMT_HYPHEN_DAY);
            dates.add(date);
        }
        return dates;
    }

    /**
     * 获取日期列表(param)
     * @return
     */
    public static List<String> buildNextDate(){
        List<String> dates = dates();
        List<String> front = dates.subList(0,7);
        List<String> back  = dates.subList(7,14);
        String join = StringTool.join(back, "|");
        front.add(join);
        front.add("ALL");
        return front;
    }

    /**
     * 获取日期列表(param)
     * @return
     */
    public static String buildEarlyNextDate(){
        List<String> dates = dates();
        return StringTool.join(dates, ",");
    }


    public static void main(String[] args) {
        List<String> s = buildNextDate();
        for (String s1 : s) {
            System.out.println(s1);
        }
    }
}
