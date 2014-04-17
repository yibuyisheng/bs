package models;

/**
 * Created by zhangli on 14-4-15.
 */
public class Cart {
    public int id;
    public int userId;
    public int flowerId;
    public int count;

    public Cart() {}
    public Cart(int userId, int flowerId, int count) {
        this.userId = userId;
        this.flowerId = flowerId;
        this.count = count;
    }
    public Cart(int id, int userId, int flowerId, int count) {
        this.id = id;
        this.userId = userId;
        this.flowerId = flowerId;
        this.count = count;
    }
}
