package database;

import java.util.*;
import java.sql.*;

public abstract class Base {
  protected static long getCount(String sql, List<Object> params) throws Exception {
    final Map<String, Long> count = new HashMap<String, Long>();
    count.put("ct", 0L);
    Query.query(sql, params, new DataBaseCallback() {
        @Override
        public void queryCb(ResultSet rs) throws Exception {
          rs.next();
          count.put("ct", rs.getLong("ct"));
        }
      }
    );
    return count.get("ct");
  }
}