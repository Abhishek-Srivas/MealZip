package in.kay.mealzip.Request;

public class ResetRequest {
    String email;

    public ResetRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
