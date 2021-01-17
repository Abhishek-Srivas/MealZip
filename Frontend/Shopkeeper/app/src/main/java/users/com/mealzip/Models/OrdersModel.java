package users.com.mealzip.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrdersModel {
    @SerializedName("orders")
    private List<Order> orders;

    @SerializedName("message")
    private String message;

    public List<Order> getOrders(){
        return orders;
    }

    public String getMessage(){
        return message;
    }
}
