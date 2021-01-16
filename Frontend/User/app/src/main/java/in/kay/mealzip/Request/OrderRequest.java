package in.kay.mealzip.Request;

public class OrderRequest {
    String shopId, consumerId, itemId, itemName, date, price, rating;

    public OrderRequest(String shopId, String consumerId, String itemId, String itemName, String date, String price, String rating) {
        this.shopId = shopId;
        this.consumerId = consumerId;
        this.itemId = itemId;
        this.itemName = itemName;
        this.date = date;
        this.price = price;
        this.rating = rating;
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

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
