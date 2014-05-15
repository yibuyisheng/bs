package controllers;

import play.*;
import play.api.mvc.*;
import play.mvc.*;
import play.mvc.Result;
import views.html.*;
import play.data.*;
import models.*;

import java.util.*;
import models.Classify;
import database.*;

import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;
import scala.Option;

public class ForumController extends Base {
  public static Result forum() throws Exception {
    Map<String, Object> map = menuClassify();
    List<Classify> parentList = (List<Classify>)map.get("parents");
    Map<Classify, List<Classify>> menuClassify = (Map<Classify, List<Classify>>)map.get("parentChildMap");

    String page = request().getQueryString("page");
    if (page == null || page == "") page = "1";

    int pageIndex = Integer.parseInt(page);
    if (pageIndex < 1) pageIndex = 1;
    int pageSize = 3;

    long replyCount = ForumDB.getPostCount();
    int totalPage = (int)(replyCount % pageSize > 0 ? (replyCount / pageSize + 1) : replyCount / pageSize);

    List<Forum> forums = ForumDB.getLatest((pageIndex - 1) * pageSize, pageSize);

    return ok(views.html.forum.forum.render(forums, pageIndex, pageSize, totalPage, parentList, menuClassify, self(), request()));
  }

  public static Result add() throws Exception {
    // 判断是否登录
    if (!self().isDefined()) {
      return redirect("/user/login?url=/forum/add");
    }

    Map<String, Object> map = menuClassify();
    List<Classify> parentList = (List<Classify>)map.get("parents");
    Map<Classify, List<Classify>> menuClassify = (Map<Classify, List<Classify>>)map.get("parentChildMap");

    return ok(views.html.forum.add.render(parentList, menuClassify, self(), request()));
  }

  public static Result save() throws Exception {
    ObjectNode result = Json.newObject();

    // 判断是否登录
    if (!self().isDefined()) {
      return ok(result.put("status", -1).put("msg", "请登录！"));
    }

    Map<String, String[]> data = request().body().asFormUrlEncoded();
    String title = getString("title", "", data);
    String content = getString("content", "", data);

    Forum forum = new Forum(0, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), 
      content, title, Forum.KIND_POST, self().get().id,
      0, Forum.STATE_SHOW, 0, 0);
    ForumDB.save(forum);

    return ok(result.put("status", 1));
  }

  public static Result detail(int fid) throws Exception {
    Map<String, Object> map = menuClassify();
    List<Classify> parentList = (List<Classify>)map.get("parents");
    Map<Classify, List<Classify>> menuClassify = (Map<Classify, List<Classify>>)map.get("parentChildMap");

    String page = request().getQueryString("page");
    if (page == null || page == "") page = "1";

    int pageIndex = Integer.parseInt(page);
    if (pageIndex < 1) pageIndex = 1;
    int pageSize = 3;

    Forum forum = ForumDB.get(fid);
    List<Forum> replys = ForumDB.getReplys(fid, (pageIndex - 1) * pageSize, pageSize);
    long replyCount = ForumDB.getReplyCount(fid);
    int totalPage = (int)(replyCount % pageSize > 0 ? (replyCount / pageSize + 1) : replyCount / pageSize);

    ForumDB.getForumsProviders(replys);

    ForumDB.addViewNo(fid);

    return ok(views.html.forum.detail.render(replys, pageIndex, pageSize, 
      totalPage, forum, parentList, menuClassify, self(), request()));
  }

  public static Result reply(int fid) throws Exception {
    ObjectNode result = Json.newObject();

    // 判断是否登录
    if (!self().isDefined()) {
      return ok(result.put("status", -1).put("msg", "请登录！"));
    }

    Map<String, String[]> data = request().body().asFormUrlEncoded();
    String content = getString("content", "", data);

    Forum reply = new Forum(0, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), 
      content, "", Forum.KIND_REPLY, self().get().id,
      fid, Forum.STATE_SHOW, 0, 0);
    ForumDB.save(reply);

    ForumDB.addReplyNo(fid);

    return ok(result.put("status", 1));
  }

  public static Result myPosts() throws Exception {
    Option<User> self = self();
    if (!self.isDefined()) return redirect( "/user/login?url=" + request().path());

    Map<String, Object> map = menuClassify();
    List<Classify> parentList = (List<Classify>)map.get("parents");
    Map<Classify, List<Classify>> menuClassify = (Map<Classify, List<Classify>>)map.get("parentChildMap");

    String page = request().getQueryString("page");
    if (page == null || page == "") page = "1";

    int pageIndex = Integer.parseInt(page);
    if (pageIndex < 1) pageIndex = 1;
    int pageSize = 3;

    long replyCount = ForumDB.postsCountByUserId(self.get().id);
    int totalPage = (int)(replyCount % pageSize > 0 ? (replyCount / pageSize + 1) : replyCount / pageSize);

    List<Forum> forums = ForumDB.postsByUserId(self.get().id, (pageIndex - 1) * pageSize, pageSize);

    return ok(views.html.forum.myPosts.render(forums, pageIndex, pageSize, totalPage, parentList, menuClassify, self(), request()));
  }

  public static Result myReply() throws Exception {
    Option<User> self = self();
    if (!self.isDefined()) return redirect( "/user/login?url=" + request().path());

    Map<String, Object> map = menuClassify();
    List<Classify> parentList = (List<Classify>)map.get("parents");
    Map<Classify, List<Classify>> menuClassify = (Map<Classify, List<Classify>>)map.get("parentChildMap");

    String page = request().getQueryString("page");
    if (page == null || page == "") page = "1";

    int pageIndex = Integer.parseInt(page);
    if (pageIndex < 1) pageIndex = 1;
    int pageSize = 3;

    long replyCount = ForumDB.replyCountByUserId(self.get().id);
    int totalPage = (int)(replyCount % pageSize > 0 ? (replyCount / pageSize + 1) : replyCount / pageSize);

    List<Forum> forums = ForumDB.replyByUserId(self.get().id, (pageIndex - 1) * pageSize, pageSize);

    return ok(views.html.forum.myReply.render(forums, pageIndex, pageSize, totalPage, parentList, menuClassify, self(), request()));
  }
}