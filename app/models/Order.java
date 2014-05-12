package models;

import java.util.Date;

public class Order {
  public int id;

  public int userId;
  public User user;

  public String flowerIds; // 格式:{flowerId: count, ...}

  public String ordererName;
  public String ordererPhone;
  public String ordererMobile;
  public String ordererProvince;
  public String ordererCity;
  public String ordererEmail;

  public String consigneeName;
  public String consigneePhone;
  public String consigneeMobile;
  public String consigneeProvince;
  public String consigneeCity;
  public String consigneeAddress;

  public int sendArea;
  public Date sendDate;
  public int sendTime;

  public String specialNeeds;
  public String leaveMessage;
  public int leaveName;
  public String leaveNameMyName;

  // 订单状态, (1->已下单，2->已发货，3->已确认收货)
  public int state; 

  public static String leaveNameStr(int leaveName) {
    String ret = "未知";
    switch(leaveName) {
      case 1: ret = "无需另外署名，卡片按留言栏填写就好了"; break;
      case 2: ret = "需要署名，我的署名是："; break;
      case 3: ret = "不需要署名，我想保密！"; break;
    }
    return ret;
  }
  public static String stateStr(int state) {
    String ret = "未知";
    switch(state) {
      case 1: ret = "已下单"; break;
      case 2: ret = "已发货"; break;
      case 3: ret = "已确认收货"; break;
    }
    return ret;
  }
  public static String sendAreaStr(int sendArea) {
    String ret = "未知";
    switch(sendArea) {
      case 1: ret = "市区送货（免费送货）"; break;
      case 2: ret = "郊区配送（＋30元）"; break;
      case 3: ret = "远郊配送（＋50元）"; break;
    }
    return ret;
  }
  public static String sendTimeStr(int sendTime) {
    String ret = "未知";
    switch(sendTime) {
      case 1: ret = "不限"; break;
      case 2: ret = "上午（8:30-12:00）"; break;
      case 3: ret = "下午（12:00-18:00）"; break;
      case 4: ret = "晚上（18:00-21:00）"; break;
      case 5: ret = "定时服务"; break;
    }
    return ret;
  }

  public Order() {}
  public Order(int id, int userId, String flowerIds, String ordererName, String ordererPhone, 
      String ordererMobile, String ordererProvince, String ordererCity, String ordererEmail,
      String consigneeName, String consigneePhone, String consigneeMobile, String consigneeProvince,
      String consigneeCity, String consigneeAddress, int sendArea, Date sendDate, int sendTime, 
      String specialNeeds, String leaveMessage, int leaveName, String leaveNameMyName, int state) {

    this.id = id;
    this.userId = userId;
    this.flowerIds = flowerIds;

    this.ordererName = ordererName;
    this.ordererPhone = ordererPhone;
    this.ordererMobile = ordererMobile;
    this.ordererProvince = ordererProvince;
    this.ordererCity = ordererCity;
    this.ordererEmail = ordererEmail;

    this.consigneeName = consigneeName;
    this.consigneePhone = consigneePhone;
    this.consigneeMobile = consigneeMobile;
    this.consigneeProvince = consigneeProvince;
    this.consigneeCity = consigneeCity;
    this.consigneeAddress = consigneeAddress;

    this.sendArea = sendArea;
    this.sendDate = sendDate;
    this.sendTime = sendTime;

    this.specialNeeds = specialNeeds;
    this.leaveMessage = leaveMessage;
    this.leaveName = leaveName;
    this.leaveNameMyName = leaveNameMyName;

    this.state = state;
  }
}