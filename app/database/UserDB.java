package database;

/**
 * Created by zhangli on 14-3-29.
 */

import models.User;

import java.sql.ResultSet;
import java.util.*;

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

    final Map<String, String> ret = new HashMap<String, String>();
    Query.query("select * from User where nickname=? or email=? limit 0,1", params, new DataBaseCallback() {
        @Override
      public void queryCb(ResultSet rs) throws Exception {
        super.queryCb(rs);
        if (rs.next()) {
          ret.put("id", String.valueOf(rs.getInt("id")));
          ret.put("nickname", rs.getString("nickname"));
          ret.put("email", rs.getString("email"));
          ret.put("truename", rs.getString("truename"));
          ret.put("password", rs.getString("password"));
        }
      }
    });
    return ret.keySet().size() == 0 ? null : new User(Integer.parseInt(ret.get("id"), 0), ret.get("nickname"), ret.get("email"), ret.get("truename"), ret.get("password"));
  }
}
