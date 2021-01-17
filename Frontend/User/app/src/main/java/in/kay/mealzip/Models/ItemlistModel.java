package in.kay.mealzip.Models;

public class ItemlistModel {
    String name,rating,veg_type,category,price,imgUrl;
    String _id,itemId;
   // int imgres;


    public ItemlistModel(String name, String rating, String veg_type, String category, String price, String imgUrl, String _id, String itemId) {
        this.name = name;
        this.rating = rating;
        this.veg_type = veg_type;
        this.category = category;
        this.price = price;
        this.imgUrl = imgUrl;
        this._id = _id;
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getVeg_type() {
        return veg_type;
    }

    public void setVeg_type(String veg_type) {
        this.veg_type = veg_type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
}
