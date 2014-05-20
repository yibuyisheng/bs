package models;

import java.util.*;

public class FlowerComment {
  public int id;
  public String content;

  public int providerId;
  public User provider;

  public Date createDate;
  public int kind;  // 1代表是购买之前的评价，2代表是购买之后的评价，3代表的是收获之后的评价；
  
  public int flowerId;
  public Flower flower;

  public String reply;

  public FlowerComment() {}
  public FlowerComment(int id, String content, int providerId, Date createDate, int kind, int flowerId, String reply) {
    this.id = id;
    this.content = content;
    this.providerId = providerId;
    this.createDate = createDate;
    this.kind = kind;
    this.flowerId = flowerId;
    this.reply = reply;
  }
  public FlowerComment(String content, int providerId, Date createDate, int kind, int flowerId, String reply) {
    this.content = content;
    this.providerId = providerId;
    this.createDate = createDate;
    this.kind = kind;
    this.flowerId = flowerId;
    this.reply = reply;
  }

  public static String kindStr(int kind) {
    switch (kind) {
      case 1: return "购买前";
      case 2: return "购买后";
      case 3: return "收货后";
      default: return "未知";
    }
  }
}