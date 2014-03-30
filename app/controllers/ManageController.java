package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import database.ClassifyDB;
import database.FlowerDB;
import models.Classify;
import models.Flower;
import play.libs.Json;
import play.mvc.Result;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by zhangli on 14-3-30.
 */
public class ManageController extends Base {
  public static Result manage() throws Exception {
    List<Classify> classifyList = ClassifyDB.getAll();
    return ok(views.html.manage.manage.render(classifyList, request()));
  }

  private static Pattern titlePtn = Pattern.compile("^[^\\x00-\\xffa-zA-Z0-9]{0,50}$");
  private static Pattern absPtn = Pattern.compile("^[^\\x00-\\xffa-zA-Z0-9]{0,300}$");
  private static Pattern pricePtn = Pattern.compile("^[1-9]{1}[0-9]*(\\.{1}[0-9]+)*$");
  private static Pattern imagePtn = Pattern.compile("^[^\\x00-\\xffa-zA-Z0-9]{0,200}$");
  private static Pattern detailPtn = Pattern.compile("^[^\\x00-\\xffa-zA-Z0-9]*$");
  private static Pattern classifyPtn = Pattern.compile("^[1-9]{1}[0-9]*$");
  public static Result addFlowerAjax() throws Exception {
    String title = getString("title", "");
    String abs = getString("abstract", "");
    String price = getString("price", "");
    String image = getString("image", "");
    String detail = getString("detail", "");
    String classify = getString("classify", "");

    ObjectNode result = Json.newObject();
    if (!titlePtn.matcher(title).matches()) {
      return ok(result.put("status", 0).put("msg", "标题不正确！"));
    }
    if (!absPtn.matcher(abs).matches()) {
      return ok(result.put("status", 0).put("msg", "简介不正确！"));
    }
    if (!pricePtn.matcher(price).matches()) {
      return ok(result.put("status", 0).put("msg", "价格不正确！"));
    }
    if (!imagePtn.matcher(image).matches()) {
      return ok(result.put("status", 0).put("msg", "图片链接不正确！"));
    }
    if (!detailPtn.matcher(detail).matches()) {
      return ok(result.put("status", 0).put("msg", "详情不正确！"));
    }
    if (!classifyPtn.matcher(classify).matches()) {
      return ok(result.put("status", 0).put("msg", "分类有误！"));
    }

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
}
