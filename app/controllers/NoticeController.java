package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import models.*;
import play.libs.Json;
import play.mvc.Result;
import play.api.mvc.Request;
import play.api.mvc.Session;

import java.util.*;
import java.util.regex.Pattern;
import database.*;
import scala.Option;

public class NoticeController extends Base {
  public static Result notice(int id) throws Exception {
    Notice notice = NoticeDB.get(id);

    Map<String, Object> map = menuClassify();
    List<Classify> parentList = (List<Classify>)map.get("parents");
    Map<Classify, List<Classify>> menuClassify = (Map<Classify, List<Classify>>)map.get("parentChildMap");

    return ok(views.html.notice.notice.render(notice, parentList, menuClassify, self(), request()));
  }
}