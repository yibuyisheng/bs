package controllers;

import play.*;
import play.mvc.*;

import views.html.*;
import play.data.*;
import models.*;

import play.libs.Json;
import com.fasterxml.jackson.databind.JsonNode;

public class UserController extends Controller {
  public static Result regist() {
    return ok(views.html.user.regist.render());
  }


  class UserRegist {
    public String nickName;

    public String validate() {
      return null;
    }
  }
  private static Form<UserRegist> userRegistForm = Form.form(UserRegist.class);
  public static Result registAjax() {
    Form formTask = userRegistForm.bindFromRequest();

    ObjectNode result = Json.newObject();
    if (formTask.hasErrors()) {
      result.put("status", 0);
      result.put("msg", "");
    } else {
      result.put("status", 1);
    }
    return ok(result);
  }
}