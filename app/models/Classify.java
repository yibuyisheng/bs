package models;

/**
 * Created by zhangli on 14-3-29.
 */
public class Classify {
  public int id;
  public String name;
  public int parent;
  public int order;

  public Classify() {}
  public Classify(int id, String name, int parent, int order) {
    this.id = id;
    this.name = name;
    this.parent = parent;
    this.order = order;
  }
}
