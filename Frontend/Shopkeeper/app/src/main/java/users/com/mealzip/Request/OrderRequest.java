package users.com.mealzip.Request;

public class OrderRequest {
    String shopId;

    public OrderRequest(String shopId) {
        this.shopId = shopId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
}
