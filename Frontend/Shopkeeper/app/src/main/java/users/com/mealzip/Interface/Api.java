package users.com.mealzip.Interface;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import users.com.mealzip.Request.DetailRequest;
import users.com.mealzip.Request.LoginRequest;
import users.com.mealzip.Request.Otpverify;
import users.com.mealzip.Request.RefreshRequest;
import users.com.mealzip.Request.Resendotp;
import users.com.mealzip.Request.ResetRequest;
import users.com.mealzip.Request.ResetpassRequest;
import users.com.mealzip.Request.Signup;
import users.com.mealzip.Response.LoginResponse;
import users.com.mealzip.Response.OtpResponse;

public interface Api {

    @POST("signup")
    Call<ResponseBody> createuser(@Body Signup create);

    @POST("signup/otp-check")
    Call<OtpResponse> verifyuser(@Body Otpverify verify);

    @POST("resendOtp")
    Call<ResponseBody> resend_otp(@Body Resendotp resend);

    @POST("login")
    Call<LoginResponse> loginuser(@Body LoginRequest login);

    @POST("signup/resetOtp")
    Call<ResponseBody> resetotpemail(@Body ResetRequest reset);

    @POST("check-Reset-Otp")
    Call<ResponseBody> resetotpcheck(@Body Otpverify reset);

    @POST("reset-Password")
    Call<ResponseBody> resetpassword(@Body ResetpassRequest reset);

    @GET("/getCollage")
    Call<ResponseBody> getcollege();

    @POST("shopInfo")
    Call<ResponseBody> savedetail(@Body DetailRequest detail , @Header("Authorization") String header);

    @POST("refreshToken")
    Call<ResponseBody> refreshtoken(@Body RefreshRequest refreshRequest);
}
