package users.com.mealzip.Request;

public class GettopRequest {
    String Date;
    String shopId;

    public GettopRequest(String date, String shopId) {
        Date = date;
        this.shopId = shopId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
