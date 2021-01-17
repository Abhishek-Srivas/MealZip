package in.kay.mealzip.Response;

public class OtpResponse {
    String message,signAccessToken,refreshToken,college,userId,userName;

    public OtpResponse(String message, String signAccessToken, String refreshToken, String college, String userId, String userName) {
        this.message = message;
        this.signAccessToken = signAccessToken;
        this.refreshToken = refreshToken;
        this.college = college;
        this.userId = userId;
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }
}
