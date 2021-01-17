package users.com.mealzip.Models;

import java.util.List;

public class FoodModel {
    String _id,name,imgUrl,isveg,category;
    List<PriceArray> priceArray;

    public FoodModel(String _id, String name, String imgUrl, String isveg, String category, List<PriceArray> priceArray) {
        this._id = _id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.isveg = isveg;
        this.category = category;
        this.priceArray = priceArray;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getIsveg() {
        return isveg;
    }

    public void setIsveg(String isveg) {
        this.isveg = isveg;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<PriceArray> getPriceArray() {
        return priceArray;
    }

    public void setPriceArray(List<PriceArray> priceArray) {
        this.priceArray = priceArray;
    }
}
