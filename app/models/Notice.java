package models;

import java.util.*;

public class Notice {
  public int id;
  public String title;
  public String content;
  public Date createDate;

  public Notice() {}
  public Notice(int id, String title, String content, Date createDate) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.createDate = createDate;
  }

  public Notice(String title, String content, Date createDate) {
    this.title = title;
    this.content = content;
    this.createDate = createDate;
  }
}