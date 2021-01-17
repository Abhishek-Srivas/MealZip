package users.com.mealzip.Models;

import java.util.List;

public class Order {
    private String _id;
    private String shopId;
    private String consumerId;
    private String orderDate;
    private Integer v;
    private String customerName;
    private List<Orderarray> ordersArray;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public List<Orderarray> getOrdersArray() {
        return ordersArray;
    }

    public void setOrdersArray(List<Orderarray> ordersArray) {
        this.ordersArray = ordersArray;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}
