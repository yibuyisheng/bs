package database;

import java.sql.ResultSet;
import java.util.*;
import models.Order;
import java.sql.SQLException;

public class OrderDB {
  public static void save(Order order) throws Exception {
    String sql = "insert into " +
            "order(userId, flowerIds, ordererName, ordererPhone, " + 
            "ordererMobile, ordererProvince, ordererCity, ordererEmail, " +
            "consigneeName, consigneePhone, consigneeMobile, consigneeProvince, " +
            "consigneeCity, consigneeAddress, sendArea, sendDate, sendTime, " +
            "specialNeeds, leaveMessage, leaveName, leaveNameMyName)" +
            "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    List<Object> params = new ArrayList<Object>();
    params.add(order.userId);
    params.add(order.flowerIds);
    params.add(order.ordererName);
    params.add(order.ordererPhone);
    params.add(order.ordererMobile);
    params.add(order.ordererProvince);
    params.add(order.ordererCity);
    params.add(order.ordererEmail);
    params.add(order.consigneeName);
    params.add(order.consigneePhone);
    params.add(order.consigneeMobile);
    params.add(order.consigneeProvince);
    params.add(order.consigneeCity);
    params.add(order.consigneeAddress);
    params.add(order.sendArea);
    params.add(order.sendDate);
    params.add(order.sendTime);
    params.add(order.specialNeeds);
    params.add(order.leaveMessage);
    params.add(order.leaveName);
    params.add(order.leaveNameMyName);
    Query.update(sql, params);
  }

  private static void fetchList(List<Order> orders, ResultSet rs) throws SQLException {
    while(rs.next()) {
      Order order = new Order();
      order.id = rs.getInt("id");
      order.userId = rs.getInt("userId");
      order.flowerIds = rs.getString("flowerIds");
      order.ordererName = rs.getString("ordererName");
      order.ordererPhone = rs.getString("ordererPhone");
      order.ordererMobile = rs.getString("ordererMobile");
      order.ordererProvince = rs.getString("ordererProvince");
      order.ordererCity = rs.getString("ordererCity");
      order.ordererEmail = rs.getString("ordererEmail");
      order.consigneeName = rs.getString("consigneeName");
      order.consigneePhone = rs.getString("consigneePhone");
      order.consigneeMobile = rs.getString("consigneeMobile");
      order.consigneeProvince = rs.getString("consigneeProvince");
      order.consigneeCity = rs.getString("consigneeCity");
      order.consigneeAddress = rs.getString("consigneeAddress");
      order.sendArea = rs.getInt("sendArea");
      order.sendDate = rs.getDate("sendDate");
      order.sendTime = rs.getInt("sendTime");
      order.specialNeeds = rs.getString("specialNeeds");
      order.leaveMessage = rs.getString("leaveMessage");
      order.leaveName = rs.getInt("leaveName");
      order.leaveNameMyName = rs.getString("leaveNameMyName");
      orders.add(order);
    }
  }

  public static List<Order> all(int start, int offset) throws Exception {
    String sql = "select * from `order` order by id limit " + start + "," + offset;
    final List<Order> orders = new LinkedList<Order>();
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
    String sql = "select count(id) ct from `order`";
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