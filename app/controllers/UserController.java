package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import play.*;
import play.mvc.*;

import views.html.*;
import play.data.*;
import models.*;

import play.libs.Json;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Map;
import java.util.regex.Pattern;

public class UserController extends Base {

  public static Result regist() {
    return ok(views.html.user.regist.render());
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
    return ok(result.put("status", 1));
  }
}