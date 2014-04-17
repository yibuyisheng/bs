package utils;

import java.util.List;
/**
 * Created by zhangli on 14-4-17.
 */
public class ListUtil {
    public static String mkString(List<?> list, String spliter) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0, il = list.size(); i < il; i ++) {
            sb.append(list.get(i));
            if (i < il - 1) sb.append(spliter);
        }
        return sb.toString();
    }
}
