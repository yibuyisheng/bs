package controllers;

import database.ClassifyDB;
import models.Classify;
import play.*;
import play.mvc.*;

import views.html.*;

import database.Query;
import database.DataBaseCallback;
import models.Flower;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.sql.ResultSet;
import java.util.Map;

public class Application extends Base {

  public static Result index() throws Exception {
    List<Flower> recommandFlowers = new LinkedList<Flower>();
    recommandFlowers.add(new Flower("flowers/u=1215724165,312236757&fm=21&gp=0.jpg"));
    recommandFlowers.add(new Flower("flowers/u=1710403397,3137808111&fm=21&gp=0.jpg"));
    recommandFlowers.add(new Flower("flowers/u=2379991951,704885466&fm=56.jpg"));
    recommandFlowers.add(new Flower("flowers/u=1215724165,312236757&fm=21&gp=0.jpg"));

    Map<String, Object> map = menuClassify();
    List<Classify> parentList = (List<Classify>)map.get("parents");
    Map<Classify, List<Classify>> menuClassify = (Map<Classify, List<Classify>>)map.get("parentChildMap");

    return ok(index.render(parentList, menuClassify, recommandFlowers, self(), request()));
  }

  public static Result hotFlowers() {
    return null;
  }

}
