package users.com.mealzip.Response;

public class OtpResponse {
    String message,signAccessToken,refreshToken,userId;

    public OtpResponse(String message, String signAccessToken, String refreshToken) {
        this.message = message;
        this.signAccessToken = signAccessToken;
        this.refreshToken = refreshToken;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSignAccessToken() {
        return signAccessToken;
    }

    public void setSignAccessToken(String signAccessToken) {
        this.signAccessToken = signAccessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
