package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import database.CartDB;
import database.FlowerDB;
import models.Classify;
import models.Flower;
import models.User;
import models.Cart;
import play.libs.Json;
import play.mvc.Result;
import scala.Option;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangli on 14-4-15.
 */
public class CartController extends Base {
    public static Result add(int fid, int count) throws Exception {
        ObjectNode result = Json.newObject();
        Option<User> self = self();
        if (!self.isDefined()) {
            return ok(result.put("status", -1).put("msg", "请登录！"));
        }

        CartDB.save(new Cart(self.get().id, fid, count));
        return ok(result.put("status", 1));
    }

    public static Result all() throws Exception {
        if (!self().isDefined()) return ok("请登录！");

        Map<String, Object> map = menuClassify();
        List<Classify> parentList = (List<Classify>)map.get("parents");
        Map<Classify, List<Classify>> menuClassify = (Map<Classify, List<Classify>>)map.get("parentChildMap");

        List<Cart> carts = CartDB.getByUserId(self().get().id);

        List<Integer> ids = new LinkedList<Integer>();
        for (Cart c : carts) ids.add(c.flowerId);
        List<Flower> flowers = ids.size() > 0 ? FlowerDB.getByIds(ids) : new LinkedList<Flower>();

        return ok(views.html.cart.carts.render(carts, flowers, parentList, menuClassify, self(), request()));
    }

    public static Result delete(int cid) throws Exception {
        ObjectNode result = Json.newObject();
        Option<User> self = self();
        if (!self.isDefined()) {
            return ok(result.put("status", -1).put("msg", "请登录！"));
        }

        CartDB.delete(cid, self.get().id);
        return ok(result.put("status", 1));
    }
}
