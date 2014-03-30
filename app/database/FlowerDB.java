package database;

import models.Flower;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangli on 14-3-30.
 */
public class FlowerDB {
  public static void add(Flower flower) throws Exception {
    List<Object> params = new ArrayList<Object>();
    params.add(flower.title);
    params.add(flower.abs);
    params.add(flower.price);
    params.add(flower.image);
    params.add(flower.detail);
    params.add(flower.classify);
    Query.update("insert into flower(title, abstract, price, image, detail, classify) values(?,?,?,?,?,?)", params);
  }
}
