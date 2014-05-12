package controllers;

import play.mvc.Result;
import java.util.Map;
import java.util.List;
import models.Classify;
import models.Order;
import models.User;
import scala.Option;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;
import models.Cart;
import utils.ListUtil;
import database.*;
import java.util.Date;
import java.util.ArrayList;

public class CashController extends Base {
  public static Result cash() throws Exception {
    Map<String, Object> map = menuClassify();
    List<Classify> parentList = (List<Classify>)map.get("parents");
    Map<Classify, List<Classify>> menuClassify = (Map<Classify, List<Classify>>)map.get("parentChildMap");

    // List<Cart> carts = CartDB.getByUserId(self().get().id);

    return ok(views.html.cash.cash.render(parentList, menuClassify, self(), request()));
  }

  public static Result createOrder() throws Exception {
    Option<User> self = self();
    ObjectNode result = Json.newObject();
    if (!self.isDefined()) return ok(result.put("status", -1).put("msg", "请登录！"));

    Map<String, String[]> data = request().body().asFormUrlEncoded();
    String ordererName = getString("orderer-name", "", data);
    String ordererPhone = getString("orderer-phone", "", data);
    String ordererMobile = getString("orderer-mobile", "", data);
    String ordererProvince = getString("orderer-province", "", data);
    String ordererCity = getString("orderer-city", "", data);
    String ordererEmail = getString("orderer-email", "", data);

    String consigneeName = getString("consignee-name", "", data);
    String consigneePhone = getString("consignee-phone", "", data);
    String consigneeMobile = getString("consignee-mobile", "", data);
    String consigneeProvince = getString("consignee-province", "", data);
    String consigneeCity = getString("consignee-city", "", data);
    String consigneeAddress = getString("consignee-address", "", data);

    String sendArea = getString("send-area", "", data);
    String sendDate = getString("send-date", "", data);
    String sendTime = getString("send-time", "", data);

    String specialNeeds = getString("special-needs", "", data);
    String leaveMessage = getString("leave-message", "", data);
    String leaveName = getString("leave-name", "", data);
    String leavaNameMyName = getString("leave-name-my-name", "", data);

    List<Cart> carts = CartDB.getByUserId(self().get().id);
    List<String> flowerIds = new ArrayList<String>();
    for (Cart cart : carts) {
      flowerIds.add(cart.flowerId + ":" + cart.count);
    }

    Order order = new Order(0, self.get().id, "{" + ListUtil.mkString(flowerIds, "," ) + "}", 
        ordererName, ordererPhone, ordererMobile, ordererProvince, ordererCity, ordererEmail,
        consigneeName, consigneePhone, consigneeMobile, consigneeProvince, consigneeCity, consigneeAddress,
        Integer.parseInt(sendArea), new Date(Long.parseLong(sendDate)), Integer.parseInt(sendTime), 
        specialNeeds, leaveMessage, Integer.parseInt(leaveName), leavaNameMyName, 1);
    OrderDB.save(order);

    // 删掉购物车中相应记录
    CartDB.deleteByUserId(self.get().id);

    return ok(result.put("status", 1));
  }

  public static Result orders() throws Exception {
    Option<User> self = self();
    ObjectNode result = Json.newObject();
    if (!self.isDefined()) return ok(result.put("status", -1).put("msg", "请登录！"));

    Map<String, Object> map = menuClassify();
    List<Classify> parentList = (List<Classify>)map.get("parents");
    Map<Classify, List<Classify>> menuClassify = (Map<Classify, List<Classify>>)map.get("parentChildMap");


    String page = request().getQueryString("page");
    if (page == null || page == "") page = "1";

    int pageIndex = Integer.parseInt(page);
    if (pageIndex < 1) pageIndex = 1;
    int pageSize = 10;

    long total = OrderDB.totalByUser(self.get().id);
    int totalPage = (int)(total % pageSize > 0 ? (total / pageSize + 1) : total / pageSize);

    List<Order> orders = OrderDB.getByUser(self.get().id, (pageIndex - 1) * pageSize, pageSize);

    return ok(views.html.cash.orders.render(orders, pageIndex, pageSize, totalPage, parentList, menuClassify, self(), request()));
  }

  public static Result order(int oid) throws Exception {
    Map<String, Object> map = menuClassify();
    List<Classify> parentList = (List<Classify>)map.get("parents");
    Map<Classify, List<Classify>> menuClassify = (Map<Classify, List<Classify>>)map.get("parentChildMap");

    Order order = OrderDB.get(oid);
    if (order == null) return status(404, "not found");
    return ok(views.html.cash.orderDetail.render(order, parentList, menuClassify, self(), request()));
  }
}