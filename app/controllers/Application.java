package controllers;

import database.ClassifyDB;
import models.Classify;
import play.*;
import play.mvc.*;

import views.html.*;

import database.Query;
import database.DataBaseCallback;
import models.Flower;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.sql.ResultSet;
import java.util.Map;

import database.*;

public class Application extends Base {

  public static Result index() throws Exception {
    List<Flower> recommandFlowers = FlowerDB.allFlowers(0, 9);

    Map<String, Object> map = menuClassify();
    List<Classify> parentList = (List<Classify>)map.get("parents");
    Map<Classify, List<Classify>> menuClassify = (Map<Classify, List<Classify>>)map.get("parentChildMap");

    return ok(index.render(parentList, menuClassify, recommandFlowers, self(), request()));
  }

  public static Result hotFlowers() {
    return null;
  }

  public static Result search() throws Exception {
    Map<String, Object> map = menuClassify();
    List<Classify> parentList = (List<Classify>)map.get("parents");
    Map<Classify, List<Classify>> menuClassify = (Map<Classify, List<Classify>>)map.get("parentChildMap");

    String search = request().getQueryString("search");
    if (search == null) search = "";

    String page = request().getQueryString("page");
    if (page == null || page == "") page = "1";
    int pageIndex = Integer.parseInt(page);
    if (pageIndex < 1) pageIndex = 1;

    int pageSize = 10;

    List<Flower> flowers = FlowerDB.search(search, (pageIndex - 1) * pageSize, pageSize);
    long flowerCount = FlowerDB.searchTotal(search);
    int totalPage = (int)(flowerCount % pageSize == 0 ? flowerCount / pageSize : (flowerCount / pageSize + 1));

    return ok(views.html.search.render(
      search, 
      flowers,
      pageIndex, pageSize, totalPage, 
      parentList, 
      menuClassify, 
      self(), 
      request())
    );
  }

}
