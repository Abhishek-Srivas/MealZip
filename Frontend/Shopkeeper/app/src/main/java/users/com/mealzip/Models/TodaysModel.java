package users.com.mealzip.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TodaysModel {
    @SerializedName("_id")
    @Expose
    private String id;
    private Integer total;
    private Integer itemSold;
    private List<String> name = null;
    private List<Float> rating = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getItemSold() {
        return itemSold;
    }

    public void setItemSold(Integer itemSold) {
        this.itemSold = itemSold;
    }

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public List<Float> getRating() {
        return rating;
    }

    public void setRating(List<Float> rating) {
        this.rating = rating;
    }
}
