package database;

import models.Classify;

import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhangli on 14-3-29.
 */
public class ClassifyDB {
  public static List<Classify> getAll() throws Exception {
    final List<Classify> list = new LinkedList<Classify>();
    Query.query("select * from classify", null, new DataBaseCallback() {
      @Override
      public void queryCb(ResultSet rs) throws Exception {
        super.queryCb(rs);
        while(rs.next()) {
          Classify classify = new Classify();
          classify.id = rs.getInt("id");
          classify.name = rs.getString("name");
          classify.parent = rs.getInt("parent");
          classify.order = rs.getInt("order");
          list.add(classify);
        }
      }
    });
    return list;
  }
}
