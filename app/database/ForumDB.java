package database;

import models.Forum;
import java.util.*;
import java.sql.*;
import models.*;

public class ForumDB {
  public static void save(Forum forum) throws Exception {
    String sql = 
      "insert into " +
        "forum(" +
          "createTime, " +
          "refreshTime, " +
          "content, " +
          "title, " +
          "kind, " +
          "state, " +
          "replyNo, " +
          "viewNo, " +
          "providerId, " +
          "targetId)" +
      "values(?,?,?,?,?,?,?,?,?,?)";
    List<Object> params = new ArrayList<Object>();
    params.add(forum.createTime);
    params.add(forum.refreshTime);
    params.add(forum.content);
    params.add(forum.title);
    params.add(forum.kind);
    params.add(forum.state);
    params.add(forum.replyNo);
    params.add(forum.viewNo);
    params.add(forum.providerId);
    params.add(forum.targetId);
    Query.update(sql, params);
  }

  public static void addViewNo(int fid) throws Exception {
    String sql = "update forum set viewNo=viewNo+1 where id=? and kind=?";
    System.out.println("update forum set viewNo=viewNo+1 where id=" + fid + " and kind=1");
    List<Object> params = new ArrayList<Object>();
    params.add(fid);
    params.add(Forum.KIND_POST);
    Query.update(sql, params);
  }

  public static void addReplyNo(int fid) throws Exception {
    String sql = "update forum set replyNo=replyNo+1 where id=? and kind=?";
    List<Object> params = new ArrayList<Object>();
    params.add(fid);
    params.add(Forum.KIND_POST);
    Query.update(sql, params);
  }

  private static List<Forum> _forums(String sql, List<Object> params) throws Exception {
    final List<Forum> forums = new ArrayList<Forum>();
    Query.query(sql, params, new DataBaseCallback() {
      @Override
      public void queryCb(ResultSet rs) throws Exception {
        super.queryCb(rs);
        while(rs.next()) {
          Forum forum = new Forum(
            rs.getInt("id"),
            rs.getDate("createTime"),
            rs.getDate("refreshTime"),
            rs.getString("content"),
            rs.getString("title"),
            rs.getInt("kind"),
            rs.getInt("providerId"),
            rs.getInt("targetId"),
            rs.getInt("state"),
            rs.getInt("replyNo"),
            rs.getInt("viewNo")
          );
          
          forums.add(forum);
        }
      }
    });
    return forums;
  }

  public static List<Forum> getAll(int start, int offset) throws Exception {
    String sql = "select * from forum where kind=? order by refreshTime desc limit " + start + "," + offset;
    List<Object> params = new ArrayList<Object>();
    params.add(Forum.KIND_POST);

    return _forums(sql, params);
  }

  public static List<Forum> getLatest(int start, int offset) throws Exception {
    String sql = "select * from forum where kind=? and state=? order by refreshTime desc limit " + start + "," + offset;
    List<Object> params = new ArrayList<Object>();
    params.add(Forum.KIND_POST);
    params.add(Forum.STATE_SHOW);

    return _forums(sql, params);
  }

  public static List<Forum> getReplys(int fid, int start, int offset) throws Exception {
    String sql = "select * from forum where kind=? and state=? and targetId=? order by createTime desc limit " + start + "," + offset;
    List<Object> params = new ArrayList<Object>();
    params.add(Forum.KIND_REPLY);
    params.add(Forum.STATE_SHOW);
    params.add(fid);

    return _forums(sql, params);
  }

  public static long getReplyCount(int fid) throws Exception {
    String sql = "select count(id) ct from forum where kind=? and state=? and targetId=?";
    List<Object> params = new ArrayList<Object>();
    params.add(Forum.KIND_REPLY);
    params.add(Forum.STATE_SHOW);
    params.add(fid);

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

  public static long getAllCount() throws Exception {
    String sql = "select count(id) ct from forum where kind=?";
    List<Object> params = new ArrayList<Object>();
    params.add(Forum.KIND_POST);

    Map<String, Long> count = new HashMap<String, Long>();
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

  public static long getPostCount() throws Exception {
    String sql = "select count(id) ct from forum where kind=? and state=?";
    List<Object> params = new ArrayList<Object>();
    params.add(Forum.KIND_POST);
    params.add(Forum.STATE_SHOW);

    Map<String, Long> count = new HashMap<String, Long>();
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

  public static Forum get(int id) throws Exception {
    String sql = "select * from forum where id=?";
    List<Object> params = new ArrayList<Object>();
    params.add(id);

    final Forum forum = new Forum();
    forum.id = 0;
    Query.query(sql, params, new DataBaseCallback() {
      @Override
      public void queryCb(ResultSet rs) throws Exception {
        super.queryCb(rs);
        if (!rs.next()) return;
        forum.id = rs.getInt("id");
        forum.createTime = rs.getDate("createTime");
        forum.refreshTime = rs.getDate("refreshTime");
        forum.content = rs.getString("content");
        forum.title = rs.getString("title");
        forum.kind = rs.getInt("kind");
        forum.state = rs.getInt("state");
        forum.replyNo = rs.getInt("replyNo");
        forum.viewNo = rs.getInt("viewNo");
        forum.providerId = rs.getInt("providerId");
        forum.targetId = rs.getInt("targetId");
      }
    });

    return forum.id == 0 ? null : forum;
  }

  public static List<Forum> getForumsProviders(List<Forum> forums) throws Exception {
    List<Integer> userIds = new LinkedList<Integer>();
    for (Forum f : forums) userIds.add(f.providerId);

    Map<Integer, User> users = UserDB.getByIds(userIds);
    for (Forum f : forums) {
      if (users.containsKey(f.providerId)) f.provider = users.get(f.providerId);
    }
    return forums;
  }

  public static void showPost(int id) throws Exception {
    String sql = "update forum set state=" + Forum.STATE_SHOW + " where id=?";
    List<Object> params = new ArrayList<Object>();
    params.add(id);
    Query.update(sql, params);
  }

  public static void hidePost(int id) throws Exception {
    String sql = "update forum set state=" + Forum.STATE_HIDE + " where id=?";
    List<Object> params = new ArrayList<Object>();
    params.add(id);
    Query.update(sql, params);
  }
}