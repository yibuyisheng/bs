package controllers;

import database.UserDB;
import models.User;
import play.mvc.Controller;
import scala.Option;
import scala.Some;

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
}
