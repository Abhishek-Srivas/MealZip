package users.com.mealzip.Request;

public class FoodRequest {
    String email;

    public FoodRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
