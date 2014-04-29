package controllers;

import play.mvc.Result;
import java.util.Map;
import java.util.List;
import models.Classify;

public class CashController extends Base {
  public static Result cash() throws Exception {
    Map<String, Object> map = menuClassify();
    List<Classify> parentList = (List<Classify>)map.get("parents");
    Map<Classify, List<Classify>> menuClassify = (Map<Classify, List<Classify>>)map.get("parentChildMap");

    return ok(views.html.cash.cash.render(parentList, menuClassify, self(), request()));
  }
}