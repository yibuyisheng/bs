package database;

import models.Flower;

import java.sql.ResultSet;
import java.util.*;

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

    public static List<Flower> getByClassify(int cid) throws Exception {
        String sql = "select * from flower where classify=?";
        List<Object> params = new ArrayList<Object>();
        params.add(cid);

        final List<Flower> flowers = new LinkedList<Flower>();
        Query.query(sql, params, new DataBaseCallback() {
            @Override
            public void queryCb(ResultSet rs) throws Exception {
                super.queryCb(rs);
                while(rs.next()) {
                    Flower flower = new Flower();
                    flower.id = rs.getInt("id");
                    flower.classify = rs.getInt("classify");
                    flower.detail = rs.getString("detail");
                    flower.image = rs.getString("image");
                    flower.abs = rs.getString("abstract");
                    flower.price = rs.getFloat("price");
                    flower.title = rs.getString("title");
                    flowers.add(flower);
                }
            }
        });

        return flowers;
    }
}
