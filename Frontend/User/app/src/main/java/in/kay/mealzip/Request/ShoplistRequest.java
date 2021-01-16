package in.kay.mealzip.Request;

public class ShoplistRequest {
    String collegeName;

    public ShoplistRequest(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }
}
