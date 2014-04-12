package database;

import models.Classify;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

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

    public static Classify getClassifyById(int cid) throws Exception {
        List<Object> params = new ArrayList<Object>();
        params.add(cid);

        final Map<String, String> ret = new HashMap<String, String>();
        Query.query("select * from classify where id=?", params, new DataBaseCallback() {
            @Override
            public void queryCb(ResultSet rs) throws Exception {
                super.queryCb(rs);
                if (!rs.next()) return;

                ret.put("id", rs.getString("id"));
                ret.put("name", rs.getString("name"));
                ret.put("parent", rs.getString("parent"));
                ret.put("order", rs.getString("order"));
            }
        });
        return ret.keySet().size() == 0 ?
                null :
                new Classify(
                        Integer.parseInt(ret.get("id")),
                        ret.get("name"),
                        Integer.parseInt(ret.get("parent")),
                        Integer.parseInt(ret.get("order"))
                );
    }
}
