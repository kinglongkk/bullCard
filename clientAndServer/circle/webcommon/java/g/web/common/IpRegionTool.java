package g.web.common;

import org.soul.commons.lang.string.I18nTool;
import org.soul.commons.lang.string.StringTool;
import org.soul.web.session.SessionManagerBase;

import java.text.MessageFormat;
import java.util.Map;

/**
 * Created by tony on 16-1-5.
 */
public class IpRegionTool {
    /**
     * 将IpRegionCode转换成国际化的字符
     * @param ipRegionCode
     * @return 将IpRegionCode转换成国际化的字符
     */
    public static String getIpRegion(String ipRegionCode) {
        if(StringTool.isNotBlank(ipRegionCode)){
            String[] arr=ipRegionCode.split("_",-1);
            if(arr.length>=4){
                Map<String, Map<String,Map<String,String>>> map=I18nTool.getDictsMap(SessionManagerBase.getLocale().toString());
                String state="";
                String city="";
                String delta= map.get("region").get("delta").get(arr[0]);
                String region= map.get("region").get("region").get(arr[1]);
                if(StringTool.isNotBlank(arr[1]) && map.get("state").containsKey(arr[1])) {
                    state = map.get("state").get(arr[1]).get(arr[2]);
                }
                if(StringTool.isNotBlank(arr[1]) && StringTool.isNotBlank(arr[2])) {
                    city = map.get("city").get(arr[1] + "_" + arr[2]).get(arr[3]);
                }

                return MessageFormat.format("{0}{1}{2}{3}{4}",
                        StringTool.isBlank(delta)?"":delta,
                        StringTool.isBlank(region)?"":region,
                        StringTool.isBlank(state)?"":state,
                        StringTool.isBlank(city)?"":city,
                        arr.length==4?"":arr[4]);
            }
        }
        return "";
    }
}
