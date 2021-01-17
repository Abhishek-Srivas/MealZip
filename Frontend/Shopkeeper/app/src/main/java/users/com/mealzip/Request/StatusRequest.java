package users.com.mealzip.Request;

public class StatusRequest {
    String orderId,itemId;

    public StatusRequest(String orderId,String itemId) {
        this.orderId = orderId;
        this.itemId= itemId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
}
