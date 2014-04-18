package controllers;

import database.ClassifyDB;
import database.FlowerDB;
import models.Classify;
import play.mvc.Result;
import models.Flower;
import java.util.List;
import java.util.Map;

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
        return ok(views.html.flower.flower.render(
                currentClassify,
                flower,
                parentList,
                (Map<Classify, List<Classify>>)map.get("parentChildMap"),
                self(),
                request()
            )
        );
    }
}
