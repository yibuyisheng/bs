package database;

import java.sql.ResultSet;
import java.util.*;
import models.Order;

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
}