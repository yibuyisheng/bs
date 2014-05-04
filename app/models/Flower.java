package models;

public class Flower {
  public int id;
  public String title;
  public String abs;
  public float price;
  public String image;
  public String detail;
  public int classify;

  public Flower() {}
  public Flower(String image) {
    this.image = image;
  }
  public Flower(int id, String title, String abs, float price, String image, String detail, int classify) {
    this.id = id;
    this.title = title;
    this.abs = abs;
    this.price = price;
    this.image = image;
    this.detail = detail;
    this.classify = classify;
  }
}