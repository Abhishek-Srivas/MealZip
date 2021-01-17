package users.com.mealzip.Models;

public class IdModel {
    private Boolean isaccepted;
    private String orderStatus;
    private Boolean isPaid;
    private String _id;
    private String itemName;
    private String itemId;
    private Integer price;
    private float rating;
    private  String id;

    public IdModel(Boolean isaccepted, String orderStatus, Boolean isPaid, String _id, String itemName, String itemId, Integer price, float rating, String id) {
        this.isaccepted = isaccepted;
        this.orderStatus = orderStatus;
        this.isPaid = isPaid;
        this._id = _id;
        this.itemName = itemName;
        this.itemId = itemId;
        this.price = price;
        this.rating = rating;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getIsaccepted() {
        return isaccepted;
    }

    public void setIsaccepted(Boolean isaccepted) {
        this.isaccepted = isaccepted;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
