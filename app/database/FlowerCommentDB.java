package database;

import models.*;
import java.util.*;
import java.sql.*;

public class FlowerCommentDB extends Base {
  public static void save(FlowerComment comment) throws Exception {
    String sql = "insert into flowerComments(content, providerId, createDate, kind, flowerId) values(?,?,?,?,?)";
    List<Object> params = new ArrayList<Object>();
    params.add(comment.content);
    params.add(comment.providerId);
    params.add(comment.createDate);
    params.add(comment.kind);
    params.add(comment.flowerId);
    Query.update(sql, params);
  }

  private static List<FlowerComment> _comments(String sql, List<Object> params) throws Exception {
    final List<FlowerComment> comments = new ArrayList<FlowerComment>();
    Query.query(sql, params, new DataBaseCallback() {
      @Override
      public void queryCb(ResultSet rs) throws Exception {
        super.queryCb(rs);
        while(rs.next()) {
          FlowerComment comment = new FlowerComment(
            rs.getInt("id"),
            rs.getString("content"),
            rs.getInt("providerId"),
            rs.getDate("createDate"),
            rs.getInt("kind"),
            rs.getInt("flowerId"),
            rs.getString("reply")
          );
          
          comments.add(comment);
        }
      }
    });
    return comments;
  }

  public static List<FlowerComment> getByFlowerId(int fid, int start, int offset) throws Exception {
    String sql = "select * from flowerComments where flowerId=" + fid + 
                  " order by createDate desc limit " + start + "," + offset;
    return _comments(sql, null);
  }

  public static long getCountByFlowerId(int fid) throws Exception {
    String sql = "select count(id) ct from flowerComments where flowerId=" + fid;
    return getCount(sql, null);
  }

  public static List<FlowerComment> getAll(int start, int offset) throws Exception {
    String sql = "select * from flowerComments " + 
                  " order by createDate desc limit " + start + "," + offset;
    return _comments(sql, null);
  }

  public static long getCount() throws Exception {
    String sql = "select count(id) ct from flowerComments";
    return getCount(sql, null);
  }

  public static void reply(int id, String reply) throws Exception {
    String sql = "update flowerComments set reply=? where id=?";
    List<Object> params = new ArrayList<Object>();
    params.add(reply);
    params.add(id);
    Query.update(sql, params);
  }
}