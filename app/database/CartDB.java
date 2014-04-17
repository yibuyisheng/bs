package database;

import models.Cart;

import java.sql.ResultSet;
import java.util.*;

/**
 * Created by zhangli on 14-4-15.
 */
public class CartDB {
    public static void save(Cart cart) throws Exception {
        String sql = "insert into cart(userId, flowerId, count) values(?,?,?)";
        List<Object> params = new ArrayList<Object>();
        params.add(cart.userId);
        params.add(cart.flowerId);
        params.add(cart.count);
        Query.update(sql, params);
    }

    public static List<Cart> getByUserId(int userId) throws Exception {
        String sql = "select * from cart where userId=?";
        List<Object> params = new ArrayList<Object>();
        params.add(userId);

        final List<Cart> ret = new LinkedList<Cart>();
        Query.query(sql, params, new DataBaseCallback() {
            @Override
            public void queryCb(ResultSet rs) throws Exception {
                super.queryCb(rs);
                while(rs.next()) {
                    Cart cart = new Cart(rs.getInt("id"), rs.getInt("userId"), rs.getInt("flowerId"), rs.getInt("count"));
                    ret.add(cart);
                }
            }
        });
        return ret;
    }

    public static void delete(int id, int uid) throws Exception {
        List<Object> params = new ArrayList<Object>();
        params.add(id);
        params.add(uid);
        Query.update("delete from cart where id=? and userId=?", params);
    }
}
