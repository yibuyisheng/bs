package controllers;

import database.ClassifyDB;
import database.UserDB;
import models.Classify;
import models.User;
import play.mvc.Controller;
import scala.Option;

import java.util.*;

/**
 * Created by zhangli on 14-3-29.
 */
public class Base extends Controller {
  public static String getString(String key, String dftStr, Map<String, String[]> data) {
    String[] value = data.get(key);
    if (value == null || value.length == 0) return dftStr;

    return value[0];
  }

  public static String getString(String key, String dftStr) {
    return getString(key, dftStr, requestData());
  }

  public static Map<String, String[]> requestData() {
    return request().body().asFormUrlEncoded();
  }

  public static Option<User> self() {
    User user = null;
    try {
      String email = session("email");
      if (email != null) user = UserDB.getByAccount(email);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return user == null ? Option.<User>empty() : Option.apply(user);
  }

  protected static Map<String, Object> menuClassify() throws Exception {
    List<Classify> list = ClassifyDB.getAll();

    Map<Classify, List<Classify>> map = new HashMap<Classify, List<Classify>>();
    ArrayList<Classify> parentList = new ArrayList<Classify>();
    for (Classify c: list) {
      if (c.parent != 0) continue;
      map.put(c, new ArrayList<Classify>());
      parentList.add(c);
    }
    Collections.sort(parentList, new Comparator<Classify>() {
      @Override
      public int compare(Classify classify, Classify classify2) {
        return classify.order - classify2.order;
      }
    });
    for (Classify c: list) {
      if (c.parent == 0) continue;
      for (Map.Entry<Classify, List<Classify>> entry: map.entrySet()) {
        if (entry.getKey().id != c.parent) continue;
        entry.getValue().add(c);
      }
    }

    Map<String, Object> ret =  new HashMap<String, Object>();
    ret.put("parents", parentList);
    ret.put("parentChildMap", map);
    return ret;
  }
}
