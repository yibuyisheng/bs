package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import models.*;
import play.libs.Json;
import play.mvc.Result;

import java.util.List;
import java.util.regex.Pattern;
import database.*;

/**
 * Created by zhangli on 14-3-30.
 */
public class ManageController extends Base {
  public static Result flowerAdd() throws Exception {
    List<Classify> classifyList = ClassifyDB.getAll();
    return ok(views.html.manage.addFlower.render(classifyList, request()));
  }

  private static Pattern titlePtn = Pattern.compile("^[^\\x00-\\xffa-zA-Z0-9]{0,50}$");
  private static Pattern absPtn = Pattern.compile("^[^\\x00-\\xffa-zA-Z0-9]{0,300}$");
  private static Pattern pricePtn = Pattern.compile("^[1-9]{1}[0-9]*(\\.{1}[0-9]+)*$");
  private static Pattern imagePtn = Pattern.compile(".{0,2000}$");
  private static Pattern detailPtn = Pattern.compile("^[^\\x00-\\xffa-zA-Z0-9]*$");
  private static Pattern classifyPtn = Pattern.compile("^[1-9]{1}[0-9]*$");
  private static void validate(String title, String abs, String price, 
      String image, String detail, String classify, ObjectNode result) {
    if (!titlePtn.matcher(title).matches()) {
      result.put("status", 0).put("msg", "标题不正确！");
    }
    if (!absPtn.matcher(abs).matches()) {
      result.put("status", 0).put("msg", "简介不正确！");
    }
    if (!pricePtn.matcher(price).matches()) {
      result.put("status", 0).put("msg", "价格不正确！");
    }
    if (!imagePtn.matcher(image).matches()) {
      result.put("status", 0).put("msg", "图片链接不正确！");
    }
    if (!detailPtn.matcher(detail).matches()) {
      result.put("status", 0).put("msg", "详情不正确！");
    }
    if (!classifyPtn.matcher(classify).matches()) {
      result.put("status", 0).put("msg", "分类有误！");
    }
  }
  public static Result addFlowerAjax() throws Exception {
    String title = getString("title", "");
    String abs = getString("abstract", "");
    String price = getString("price", "");
    String image = getString("image", "");
    String detail = getString("detail", "");
    String classify = getString("classify", "");

    ObjectNode result = Json.newObject();
    validate(title, abs, price, image, detail, classify, result);
    if (!result.has("status")) return ok(result);

    Flower flower = new Flower();
    flower.title = title;
    flower.abs = abs;
    flower.price = Float.parseFloat(price);
    flower.image = image;
    flower.detail = detail;
    flower.classify = Integer.parseInt(classify);
    FlowerDB.add(flower);
    return ok(result.put("status", 1));
  }

  public static Result flowers() throws Exception {
    String page = request().getQueryString("page");
    if (page == null || page == "") page = "1";
    int pageIndex = Integer.parseInt(page);
    if (pageIndex < 1) pageIndex = 1;

    int pageSize = 10;

    List<Flower> flowers = FlowerDB.allFlowers((pageIndex - 1) * pageSize, pageSize);
    long flowerCount = FlowerDB.flowersCount();
    int totalPage = (int)(flowerCount % pageSize == 0 ? flowerCount / pageSize : (flowerCount / pageSize + 1));

    return ok(views.html.manage.allFlowers.render(flowers, pageIndex, pageSize, totalPage, request()));
  }

  public static Result modify(int fid) throws Exception {
    Flower flower = FlowerDB.get(fid);
    List<Classify> classifyList = ClassifyDB.getAll();
    return ok(views.html.manage.modifyFlower.render(flower, classifyList, request()));
  }

  public static Result modifyFlower(int fid) throws Exception {
    String title = getString("title", "");
    String abs = getString("abstract", "");
    String price = getString("price", "");
    String image = getString("image", "");
    String detail = getString("detail", "");
    String classify = getString("classify", "");

    ObjectNode result = Json.newObject();
    validate(title, abs, price, image, detail, classify, result);
    if (result.has("status")) return ok(result);

    Flower flower = new Flower();
    flower.id = fid;
    flower.title = title;
    flower.abs = abs;
    flower.price = Float.parseFloat(price);
    flower.image = image;
    flower.detail = detail;
    flower.classify = Integer.parseInt(classify);
    FlowerDB.modify(flower);

    return ok(result.put("status", 1));
  }

  public static Result allPost() throws Exception {
    String page = request().getQueryString("page");
    if (page == null || page == "") page = "1";

    int pageIndex = Integer.parseInt(page);
    if (pageIndex < 1) pageIndex = 1;
    int pageSize = 10;

    long replyCount = ForumDB.getAllCount();
    int totalPage = (int)(replyCount % pageSize > 0 ? (replyCount / pageSize + 1) : replyCount / pageSize);

    List<Forum> forums = ForumDB.getAll((pageIndex - 1) * pageSize, pageSize);

    return ok(views.html.manage.allPost.render(forums, pageIndex, pageSize, totalPage, request()));
  }

  public static Result showPost(int id) throws Exception {
    ForumDB.showPost(id);

    ObjectNode result = Json.newObject();
    return ok(result.put("status", 1));
  }

  public static Result hidePost(int id) throws Exception {
    ForumDB.hidePost(id);

    ObjectNode result = Json.newObject();
    return ok(result.put("status", 1));
  }

  public static Result deleteFlower(int id) throws Exception {
    FlowerDB.delete(id);

    ObjectNode result = Json.newObject();
    return ok(result.put("status", 1));
  }
}
