package models;

import java.util.Date;

public class Forum {
  public int id;
  /**
   * 发布的时间
   */
  public Date createTime;
  /**
   * 如果是帖子的话，这个时间代表最后回复的时间
   */
  public Date refreshTime;
  /**
   * 帖子或者回复的内容
   */
  public String content;
  /**
   * 帖子的标题，回复没有标题
   */
  public String title;
  /**
   * 此条记录的类型（帖子、回复等）
   */
  public int kind;
  /**
   * 发布帖子的用户
   */
  public User provider;
  public int providerId;
  /**
   * 如果是回复的话，这个字段指代回复的帖子
   */
  public Forum target;
  public int targetId;
  /**
   * 帖子的状态，比如审核中、可显示、不可显示等
   */
  public int state;
  /**
   * 回复数
   */
  public int replyNo;
  /**
   * 这个帖子被查看的次数
   */
  public int viewNo;
  
  
  public static final int KIND_POST=1;
  public static final int KIND_REPLY=2;
  
  public static final int STATE_SHOW=1;
  public static final int STATE_HIDE=2;
  public static final int STATE_EXAMING=3;

  public Forum() {}
  public Forum(int id, Date createTime, Date refreshTime, 
      String content, String title, int kind, int providerId,
      int targetId, int state, int replyNo, int viewNo) {
    this.id = id;
    this.createTime = createTime;
    this.refreshTime = refreshTime;
    this.content = content;
    this.title = title;
    this.kind = kind;
    this.providerId = providerId;
    this.targetId = targetId;
    this.state = state;
    this.replyNo = replyNo;
    this.viewNo = viewNo;
  }
}