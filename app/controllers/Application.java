package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

import database.Query;
import database.DataBaseCallback;
import models.Flower;

import java.util.LinkedList;
import java.util.List;
import java.sql.ResultSet;

public class Application extends Controller {

  public static Result index() {
    // try {
    //   LinkedList<Object> params = new LinkedList<Object>();
    //   params.add(Integer.valueOf(2));
    //   params.add("address insert");
    //   Query.update("insert into user_info(id, address) values(?,?)", params);

    //   Query.query("select * from user_info", new LinkedList<Object>(), new DataBaseCallback() {
    //     public void queryCb(ResultSet rs) throws Exception {
    //       while(rs.next()) {
    //         System.out.println(rs.getString("address"));
    //       }
    //     }
    //   });
    // } catch(Exception e) {
    //   e.printStackTrace();
    // }

    List<Flower> recommandFlowers = new LinkedList<Flower>();
    recommandFlowers.add(new Flower("flowers/u=1215724165,312236757&fm=21&gp=0.jpg"));
    recommandFlowers.add(new Flower("flowers/u=1710403397,3137808111&fm=21&gp=0.jpg"));
    recommandFlowers.add(new Flower("flowers/u=2379991951,704885466&fm=56.jpg"));
    recommandFlowers.add(new Flower("flowers/u=1215724165,312236757&fm=21&gp=0.jpg"));

    return ok(index.render(recommandFlowers));
  }

  public static Result hotFlowers() {
    return null;
  }

}
