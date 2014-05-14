package database;

import models.Flower;
import utils.ListUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
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

  private static void fetchList(List<Flower> flowers, ResultSet rs) throws SQLException {
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

  public static List<Flower> getByClassify(int cid) throws Exception {
    String sql = "select * from flower where classify=?";
    List<Object> params = new ArrayList<Object>();
    params.add(cid);

    final List<Flower> flowers = new LinkedList<Flower>();
    Query.query(sql, params, new DataBaseCallback() {
      @Override
      public void queryCb(ResultSet rs) throws Exception {
      super.queryCb(rs);
      fetchList(flowers, rs);
      }
    });

    return flowers;
  }

  public static Flower get(int fid) throws Exception {
    String sql = "select * from flower where id=?";
    List<Object> params = new ArrayList<Object>();
    params.add(fid);

    final Map<String, String> ret = new HashMap<String, String>();
    Query.query(sql, params, new DataBaseCallback() {
      @Override
      public void queryCb(ResultSet rs) throws Exception {
        super.queryCb(rs);
        if (!rs.next()) return;
        ret.put("id", rs.getString("id"));
        ret.put("title", rs.getString("title"));
        ret.put("abs", rs.getString("abstract"));
        ret.put("price", rs.getString("price"));
        ret.put("image", rs.getString("image"));
        ret.put("detail", rs.getString("detail"));
        ret.put("classify", rs.getString("classify"));
      }
    });
    return ret.keySet().size() == 0 ?
        null :
        new Flower(
          Integer.parseInt(ret.get("id")),
          ret.get("title"),
          ret.get("abs"),
          Float.parseFloat(ret.get("price")),
          ret.get("image"),
          ret.get("detail"),
          Integer.parseInt(ret.get("classify"))
        );
  }

  public static List<Flower> getByIds(List<Integer> ids) throws Exception {
    String sql = "select * from flower where id in (" + ListUtil.mkString(ids, ",") + ")";
    final List<Flower> flowers = new LinkedList<Flower>();
    Query.query(sql, null, new DataBaseCallback() {
      @Override
      public void queryCb(ResultSet rs) throws Exception {
        super.queryCb(rs);
        fetchList(flowers, rs);
      }
    });
    return flowers;
  }

  public static List<Flower> allFlowers(int start, int offset) throws Exception {
    String sql = "select * from flower limit " + start + "," + offset;
    final List<Flower> flowers = new ArrayList<Flower>();
    Query.query(sql, null, new DataBaseCallback() {
      @Override
      public void queryCb(ResultSet rs) throws Exception {
        super.queryCb(rs);
        fetchList(flowers, rs);
      }
    });
    return flowers;
  }

  public static long flowersCount() throws Exception {
    String sql = "select count(id) ct from flower";
    final Map<String, Long> ct = new HashMap<String, Long>();
    Query.query(sql, null, new DataBaseCallback() {
      @Override
      public void queryCb(ResultSet rs) throws Exception {
        super.queryCb(rs);
        rs.next();
        ct.put("ct", rs.getLong("ct"));
      }
    });

    return ct.containsKey("ct") ? ct.get("ct") : 0;
  }

  public static void modify(Flower flower) throws Exception {
    List<Object> params = new ArrayList<Object>();
    params.add(flower.title);
    params.add(flower.abs);
    params.add(flower.price);
    params.add(flower.image);
    params.add(flower.detail);
    params.add(flower.classify);
    params.add(flower.id);
    Query.update("update flower set title=?, abstract=?, price=?, image=?, detail=?, classify=? where id=?", params);
  }

  public static void delete(int id) throws Exception {
    List<Object> params = new ArrayList<Object>();
    params.add(id);
    Query.update("delete from flower where id=?", params);
  }

  public static List<Flower> search(String search, int start, int offset) throws Exception {
    String sql = search.replace(" ", "") == "" ?
      ("select * from flower order by id limit " + start + "," + offset) :
      ("select * from flower where title like '%" + search.replace("'", "\\'") +  "%' order by id limit " + start + "," + offset);
    final List<Flower> flowers = new ArrayList<Flower>();
    Query.query(sql, null, new DataBaseCallback() {
      @Override
      public void queryCb(ResultSet rs) throws Exception {
        super.queryCb(rs);
        fetchList(flowers, rs);
      }
    });
    return flowers;
  }

  public static long searchTotal(String search) throws Exception {
    String sql = search.replace(" ", "") == "" ?
      ("select count(id) ct from flower") :
      ("select count(id) ct from flower where title like '%" + search.replace("'", "\\'") +  "%'");
    final Map<String, Long> ct = new HashMap<String, Long>();
    Query.query(sql, null, new DataBaseCallback() {
      @Override
      public void queryCb(ResultSet rs) throws Exception {
        super.queryCb(rs);
        rs.next();
        ct.put("ct", rs.getLong("ct"));
      }
    });

    return ct.containsKey("ct") ? ct.get("ct") : 0;
  }
}
