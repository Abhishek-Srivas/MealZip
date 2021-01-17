package users.com.mealzip.Interface;

import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import users.com.mealzip.Models.FoodModel;
import users.com.mealzip.Models.OrdersModel;
import users.com.mealzip.Models.TodaysModel;
import users.com.mealzip.Request.DetailRequest;
import users.com.mealzip.Request.FoodRequest;
import users.com.mealzip.Request.GettopRequest;
import users.com.mealzip.Request.LoginRequest;
import users.com.mealzip.Request.OrderRequest;
import users.com.mealzip.Request.Otpverify;
import users.com.mealzip.Request.RefreshRequest;
import users.com.mealzip.Request.Resendotp;
import users.com.mealzip.Request.ResetRequest;
import users.com.mealzip.Request.ResetpassRequest;
import users.com.mealzip.Request.Signup;
import users.com.mealzip.Request.StatusRequest;
import users.com.mealzip.Request.TimeRequest;
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

    @POST("/shop/timing")
    Call<ResponseBody> setTime(@Body TimeRequest timerequest);

    @POST("refreshToken")
    Call<ResponseBody> refreshtoken(@Body RefreshRequest refreshRequest);

    @GET("getCategory")
    Call<ResponseBody> getcategory();

    @POST("shop/addItem")
    Call<ResponseBody> additem(@Body JsonObject add_item, @Header("Authorization") String header);

    @POST("/shop/getItem")
    Call<List<FoodModel>> getitems(@Body FoodRequest foodRequest);

    @POST("/shop/shopOrder")
    Call<OrdersModel> getorderstatus(@Body OrderRequest orderRequest);

    @POST("/shop/orderStatus")
    Call<ResponseBody> changestatus(@Body StatusRequest statusRequest);

    @POST("/todaysTop")
    Call<List<TodaysModel>> gettop(@Body GettopRequest gettop);
}
