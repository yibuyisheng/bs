package database;

/**
 * Created by zhangli on 14-3-29.
 */

import models.User;

import java.sql.ResultSet;
import java.util.*;
import utils.*;

public class UserDB {
  public static Boolean existsByNicknameOrEmail(String nickname, String email) throws Exception {
    List<Object> params = new ArrayList<Object>();
    params.add(nickname);
    params.add(email);

    final Map<String, Object> ret = new HashMap<String, Object>();
    Query.query("select count(id) ct from user where nickname=? or email=?", params, new DataBaseCallback() {
      @Override
      public void queryCb(ResultSet rs) throws Exception {
        super.queryCb(rs);
        ret.put("exists", rs.next() && rs.getInt("ct") >= 1);
      }
    });
    return (Boolean)ret.get("exists");
  }

  public static void save(User user) throws Exception {
    List<Object> params = new ArrayList<Object>();
    params.add(user.nickname);
    params.add(user.email);
    params.add(user.truename);
    params.add(user.password);

    Query.update("insert into user(nickname, email, truename, password) values(?,?,?,?)", params);
  }

  public static User getByAccount(String account) throws Exception {
    List<Object> params = new ArrayList<Object>();
    params.add(account);
    params.add(account);

    final User user = new User();
    user.id = -1;
    Query.query("select * from user where nickname=? or email=? limit 0,1", params, new DataBaseCallback() {
      @Override
      public void queryCb(ResultSet rs) throws Exception {
        super.queryCb(rs);
        if (rs.next()) {
          user.id = rs.getInt("id");
          user.nickname = rs.getString("nickname");
          user.email = rs.getString("email");
          user.truename = rs.getString("truename");
          user.password = rs.getString("password");
        }
      }
    });
    return user.id == -1 ? null : user;
  }

  public static Map<Integer, User> getByIds(List<Integer> ids) throws Exception {
    if (ids.size() == 0) return new HashMap<Integer, User>();

    String sql = "select * from user where id in (" + ListUtil.mkString(ids, ",") + ")";
    final Map<Integer, User> users = new HashMap<Integer, User>();
    Query.query(sql, null, new DataBaseCallback() {
      @Override
      public void queryCb(ResultSet rs) throws Exception {
        super.queryCb(rs);
        while(rs.next()) {
          User user = new User(rs.getInt("id"), rs.getString("nickname"), rs.getString("email"), 
            rs.getString("truename"), rs.getString("password"));
          users.put(user.id, user);
        }
      }
    });
    return users;
  }

  private static void fetchList(List<User> users, ResultSet rs) throws Exception {
    while(rs.next()) {
      User user = new User();
      user.id = rs.getInt("id");
      user.nickname = rs.getString("nickname");
      user.email = rs.getString("email");
      user.truename = rs.getString("truename");
      user.password = rs.getString("password");
      users.add(user);
    }
  }

  public static List<User> all(int start, int offset) throws Exception {
    String sql = "select * from `user` where id<>0 order by id limit " + start + "," + offset;
    final List<User> orders = new LinkedList<User>();
    Query.query(sql, null, new DataBaseCallback() {
      @Override
      public void queryCb(ResultSet rs) throws Exception {
        super.queryCb(rs);
        fetchList(orders, rs);
      }
    });
    return orders;
  }

  public static long total() throws Exception {
    String sql = "select count(id) ct from `user`";
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
