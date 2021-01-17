package in.kay.mealzip.Interface;

import java.util.List;

import in.kay.mealzip.Models.ShoplistModel;
import in.kay.mealzip.Request.LoginRequest;
import in.kay.mealzip.Request.OrderRequest;
import in.kay.mealzip.Request.Otpverify;
import in.kay.mealzip.Request.Resendotp;
import in.kay.mealzip.Request.ResetRequest;
import in.kay.mealzip.Request.ResetpassRequest;
import in.kay.mealzip.Request.ShoplistRequest;
import in.kay.mealzip.Request.Signup;
import in.kay.mealzip.Response.LoginResponse;
import in.kay.mealzip.Response.OtpResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
public interface Api {

    @POST("consumer-signup")
    Call<ResponseBody> createuser(@Body Signup create);

    @POST("/consumer-signup/otp-check")
    Call<OtpResponse> verifyuser(@Body Otpverify verify);

    @POST("resendOtp")
    Call<ResponseBody> resend_otp(@Body Resendotp resend);

    @POST("/consumer-login")
    Call<LoginResponse> loginuser(@Body LoginRequest login);

    @POST("/consumer-signup/resetOtp")
    Call<ResponseBody> resetotpemail(@Body ResetRequest reset);

    @POST("check-Reset-Otp")
    Call<ResponseBody> resetotpcheck(@Body Otpverify reset);

    @POST("consumer/reset-Password")
    Call<ResponseBody> resetpassword(@Body ResetpassRequest reset);

    @POST("consumer/shopList")
    Call<List<ShoplistModel>> getshops(@Body ShoplistRequest shoplist);

    @POST("consumer/placeOrder")
    Call<ResponseBody> placeorder(@Body OrderRequest order);
}
