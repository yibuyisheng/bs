package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import database.UserDB;
import play.*;
import play.api.mvc.*;
import play.mvc.*;

import play.mvc.Result;
import views.html.*;
import play.data.*;
import models.*;

import play.libs.Json;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class UserController extends Base {

  public static Result regist() throws Exception {
    Map<String, Object> map = menuClassify();
    List<Classify> parentList = (List<Classify>)map.get("parents");
    Map<Classify, List<Classify>> menuClassify = (Map<Classify, List<Classify>>)map.get("parentChildMap");
    return ok(views.html.user.regist.render(parentList, menuClassify, self(), request()));
  }

  private static Pattern nicknamePtn = Pattern.compile("^[a-z]{6,12}$");
  private static Pattern emailPtn = Pattern.compile("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
  private static Pattern truenamePtn = Pattern.compile("^[^\\x00-\\xffa-zA-Z0-9]{2,20}$");
  private static Pattern passwordPtn = Pattern.compile("^[a-zA-Z0-9]{6,12}$");
  public static Result registAjax() {
    Map<String, String[]> data = request().body().asFormUrlEncoded();
    String nickname = getString("nickname", "", data);
    String email = getString("email", "", data);
    String truename = getString("truename", "", data);
    String password = getString("password", "", data);

    ObjectNode result = Json.newObject();
    if (!nicknamePtn.matcher(nickname).matches()) {
      return ok(result.put("status", 0).put("msg", "昵称只能由6到12个a至z的字符组成！"));
    }
    if (!emailPtn.matcher(email).matches()) {
      return ok(result.put("status", 0).put("msg", "邮箱地址不正确！"));
    }
    if (!truenamePtn.matcher(truename).matches()) {
      return ok(result.put("status", 0).put("msg", "真实姓名格式不正确！"));
    }
    if (!passwordPtn.matcher(password).matches()) {
      return ok(result.put("status", 0).put("msg", "密码应该是6到12位，或者密码中包含非法字符！"));
    }

    try {
      if (UserDB.existsByNicknameOrEmail(nickname, email)) {
        return ok(result.put("status", 0).put("msg", "已经存在这个昵称或者这个邮箱的用户了！"));
      }

      User user = new User(nickname, email, truename, password);
      UserDB.save(user);
      session().put("email", user.email);
      session().put("nickname", user.nickname);
    } catch (Exception e) {
      e.printStackTrace();
      return ok(result.put("status", 0).put("msg", "服务器错误！"));
    }

    return ok(result.put("status", 1));
  }

  public static Result login() throws Exception {
    Map<String, Object> map = menuClassify();
    List<Classify> parentList = (List<Classify>)map.get("parents");
    Map<Classify, List<Classify>> menuClassify = (Map<Classify, List<Classify>>)map.get("parentChildMap");
    return ok(views.html.user.login.render(parentList, menuClassify, self(), request()));
  }
  public static Result loginAjax() {
    Map<String, String[]> data = request().body().asFormUrlEncoded();
    String account = getString("account", "", data);
    String password = getString("password", "", data);

    ObjectNode result = Json.newObject();
    try {
      User user = UserDB.getByAccount(account);
      if (user != null && user.password.equals(password)) {
        session().clear();
        session().put("email", user.email);
        session().put("nickname", user.nickname);
        result.put("status", 1).put("data", user.toObjectNode());
      } else {
        result.put("status", 0).put("msg", "不存在这样的账号！");
      }
    } catch (Exception e) {
      e.printStackTrace();
      result.put("status", 0).put("msg", "服务器错误！");
    }
    return ok(result);
  }

  public static Result out() {
    String url = request().getQueryString("url");
    session().clear();
    return url == null ? redirect("/") : redirect(url);
  }
}