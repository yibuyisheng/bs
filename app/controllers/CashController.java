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
import database.CartDB;
import database.OrderDB;
import java.util.Date;
import java.util.ArrayList;

public class CashController extends Base {
  public static Result cash() throws Exception {
    Map<String, Object> map = menuClassify();
    List<Classify> parentList = (List<Classify>)map.get("parents");
    Map<Classify, List<Classify>> menuClassify = (Map<Classify, List<Classify>>)map.get("parentChildMap");

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
    String leavaNameMyName = getString("leave-name-by-name", "", data);

    List<Cart> carts = CartDB.getByUserId(self().get().id);
    List<Integer> flowerIds = new ArrayList<Integer>();
    for (Cart cart : carts) {
      flowerIds.add(cart.flowerId);
    }

    Order order = new Order(0, self.get().id, ListUtil.mkString(flowerIds, ","), 
        ordererName, ordererPhone, ordererMobile, ordererProvince, ordererCity, ordererEmail,
        consigneeName, consigneePhone, consigneeMobile, consigneeProvince, consigneeCity, consigneeAddress,
        Integer.parseInt(sendArea), new Date(Long.parseLong(sendDate)), Integer.parseInt(sendTime), 
        specialNeeds, leaveMessage, Integer.parseInt(leaveName), leavaNameMyName);
    OrderDB.save(order);
    return ok(result.put("status", 1));
  }
}