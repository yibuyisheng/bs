package database;

import play.db.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

import java.util.List;

public class Query {

  public static void query(String sql, List<Object> params, DataBaseCallback cb) throws Exception {
    ResultSet rs = null;
    Connection conn = DB.getConnection();

    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      build(sql, params, ps);
      rs = ps.executeQuery();

      cb.queryCb(rs);
    } catch(Exception e) {
      throw e;
    } finally {
      if (rs != null) rs.close();
      if (conn != null && !conn.isClosed()) conn.close();
    }
  }

  public static int update(String sql, List<Object> params) throws Exception {
    ResultSet rs = null;
    Connection conn = DB.getConnection();

    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      build(sql, params, ps);
      return ps.executeUpdate();
    } catch(Exception e) {
      throw e;
    } finally {
      if (rs != null) rs.close();
      if (conn != null && !conn.isClosed()) conn.close();
    }
  }

  private static void build(String sql, List<Object> params, PreparedStatement ps) throws SQLException {
    if (params == null) return;
    for (int i = 0, il = params.size(); i < il; i ++) {
      Object value = params.get(i);
      if (value instanceof Integer) {
        ps.setInt(i + 1, ((Integer)value).intValue());
      } else if (value instanceof Long) {
        ps.setLong(i + 1, ((Long)value).longValue());
      } else if (value instanceof Double) {
        ps.setDouble(i + 1, ((Double)value).doubleValue());
      } else if (value instanceof Date) {
        ps.setDate(i + 1, (Date)value);
      } else if (value instanceof String) {
        ps.setString(i + 1, value.toString());
      } else {
        ps.setObject(i + 1, value);
      }
    }
  }
}