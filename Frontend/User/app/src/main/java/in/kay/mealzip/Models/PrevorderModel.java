package in.kay.mealzip.Models;

public class PrevorderModel {
    String name,rating,veg_type,category,price,orderstatus;
    int imgres;

    public PrevorderModel(String name, String rating, String veg_type, String category, String price, String orderstatus, int imgres) {
        this.name = name;
        this.rating = rating;
        this.veg_type = veg_type;
        this.category = category;
        this.price = price;
        this.orderstatus = orderstatus;
        this.imgres = imgres;
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

    public String getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(String orderstatus) {
        this.orderstatus = orderstatus;
    }

    public int getImgres() {
        return imgres;
    }

    public void setImgres(int imgres) {
        this.imgres = imgres;
    }
}
