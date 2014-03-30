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
}