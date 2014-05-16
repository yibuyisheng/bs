package controllers;

import database.*;
import models.Classify;
import play.mvc.Result;
import models.*;
import java.util.*;

import scala.Option;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;

/**
 * Created by zhangli on 14-4-11.
 */
public class FlowerController extends Base {
    public static Result classify(int cid) throws Exception {
        List<Flower> flowers = FlowerDB.getByClassify(cid);

        Map<String, Object> map = menuClassify();
        List<Classify> parentList = (List<Classify>)map.get("parents");
        Map<Classify, List<Classify>> menuClassify = (Map<Classify, List<Classify>>)map.get("parentChildMap");

        return ok(views.html.flower.flowers.render(
                ClassifyDB.getClassifyById(cid),
                flowers,
                parentList,
                (Map<Classify, List<Classify>>)map.get("parentChildMap"),
                self(),
                request())
        );
    }

    public static Result flower(int fid) throws Exception {
        Flower flower = FlowerDB.get(fid);

        Map<String, Object> map = menuClassify();
        List<Classify> parentList = (List<Classify>)map.get("parents");
        Map<Classify, List<Classify>> menuClassify = (Map<Classify, List<Classify>>)map.get("parentChildMap");

        Classify currentClassify = ClassifyDB.getClassifyById(flower.classify);

        String page = request().getQueryString("page");
        if (page == null || page == "") page = "1";

        int pageIndex = Integer.parseInt(page);
        if (pageIndex < 1) pageIndex = 1;
        int pageSize = 10;

        long count = FlowerCommentDB.getCountByFlowerId(flower.id);
        int totalPage = (int)(count % pageSize > 0 ? (count / pageSize + 1) : count / pageSize);

        List<FlowerComment> comments = FlowerCommentDB.getByFlowerId(flower.id, (pageIndex - 1) * pageSize, pageSize);
        List<Integer> userIds = new LinkedList<Integer>();
        for (FlowerComment cmt : comments) userIds.add(cmt.providerId);
        Map<Integer, User> users = UserDB.getByIds(userIds);
        for (FlowerComment cmt : comments) {
            if (users.containsKey(cmt.providerId)) cmt.provider = users.get(cmt.providerId);
        }

        return ok(views.html.flower.flower.render(
                currentClassify,
                flower,
                comments,
                pageIndex, pageSize, totalPage,
                parentList,
                (Map<Classify, List<Classify>>)map.get("parentChildMap"),
                self(),
                request()
            )
        );
    }

    public static Result comment(int fid) throws Exception {
        ObjectNode result = Json.newObject();
        Option<User> self = self();
        if (!self.isDefined()) return ok(result.put("status", -1).put("msg", "请登录！"));

        String content = getString("content", "");

        Order order = OrderDB.getRecentOneByUserIdAndFlowerId(self.get().id, fid);
        int kind = 1;
        if (order != null) {
            if (order.state == 3) {
                // 已确认收货
                kind = 3;
            } else if (order.state == 1 || order.state == 2) {
                // 购买了，但是未确认收货
                kind = 2;
            }
        }
        FlowerComment comment = new FlowerComment(
            content, 
            self.get().id, 
            new Date(), 
            kind, 
            fid
        );
        FlowerCommentDB.save(comment);
        return ok(result.put("status", 1));
    }
}
