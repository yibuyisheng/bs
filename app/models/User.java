package models;

/**
 * Created by zhangli on 14-3-29.
 */

import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;

public class User {
  public int id;
  public String nickname;
  public String email;
  public String truename;
  public String password;

  public User() {}
  public User(String nickname, String email, String truename, String password) {
    this.nickname = nickname;
    this.email = email;
    this.truename = truename;
    this.password = password;
  }
  public User(int id, String nickname, String email, String truename, String password) {
    this.nickname = nickname;
    this.email = email;
    this.truename = truename;
    this.password = password;
  }

  public ObjectNode toObjectNode() {
    return Json.newObject()
      .put("nickname", nickname)
      .put("email", email)
      .put("truename", truename)
      .put("password", password);
  }
}
