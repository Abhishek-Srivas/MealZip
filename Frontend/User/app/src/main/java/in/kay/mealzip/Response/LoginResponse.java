package in.kay.mealzip.Response;

public class LoginResponse {
    String message,signAccessToken,refreshToken;

    public LoginResponse(String message, String signAccessToken, String refreshToken) {
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
}
