package controllers;

import play.mvc.Controller;
import java.util.Map;

/**
 * Created by zhangli on 14-3-29.
 */
public class Base extends Controller {
  public static String getString(String key, String dftStr, Map<String, String[]> data) {
    String[] value = data.get(key);
    if (value == null || value.length == 0) return dftStr;

    return value[0];
  }
}
