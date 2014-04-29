package models;

import java.util.Date;

public class Order {
  public int id;
  public int userId;
  public String flowerIds;

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

  public Order() {}
  public Order(int id, int userId, String flowerIds, String ordererName, String ordererPhone, 
      String ordererMobile, String ordererProvince, String ordererCity, String ordererEmail,
      String consigneeName, String consigneePhone, String consigneeMobile, String consigneeProvince,
      String consigneeCity, String consigneeAddress, int sendArea, Date sendDate, int sendTime, 
      String specialNeeds, String leaveMessage, int leaveName, String leaveNameMyName) {

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
  }
}