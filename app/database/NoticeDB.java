package database;

import java.util.*;
import models.*;
import java.sql.*;

public class NoticeDB extends Base {
  public static void save(Notice notice) throws Exception {
    String sql = "insert into notice(title, content, createDate) values(?,?,?)";
    List<Object> params = new LinkedList<Object>();
    params.add(notice.title);
    params.add(notice.content);
    params.add(notice.createDate);
    Query.update(sql, params);
  }

  public static void modify(Notice notice) throws Exception {
    String sql = "update notice set title=?, content=?, createDate=? where id=?";
    List<Object> params = new LinkedList<Object>();
    params.add(notice.title);
    params.add(notice.content);
    params.add(notice.createDate);
    params.add(notice.id);
    Query.update(sql, params);
  }

  private static List<Notice> _notices(String sql, List<Object> params) throws Exception {
    final List<Notice> notices = new ArrayList<Notice>();
    Query.query(sql, params, new DataBaseCallback() {
      @Override
      public void queryCb(ResultSet rs) throws Exception {
        super.queryCb(rs);
        while(rs.next()) {
          Notice notice = new Notice(
            rs.getInt("id"),
            rs.getString("title"),
            rs.getString("content"),
            rs.getDate("createDate")
          );
          
          notices.add(notice);
        }
      }
    });
    return notices;
  }

  public static List<Notice> getList(int start, int offset) throws Exception {
    String sql = "select * from notice order by createDate desc limit " + start + "," + offset;
    return _notices(sql, null);
  }

  public static long total() throws Exception {
    String sql = "select count(id) ct from notice";
    return getCount(sql, null); 
  }

  public static Notice get(int id) throws Exception {
    String sql = "select * from notice where id=" + id;
    List<Notice> notices = _notices(sql, null);   
    return notices.size() > 0 ? notices.get(0) : null;
  }

  public static void del(int id) throws Exception {
    Query.update("delete from notice where id=" + id, null);
  }
}