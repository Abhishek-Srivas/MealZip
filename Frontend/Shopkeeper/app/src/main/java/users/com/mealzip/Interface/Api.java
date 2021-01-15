package users.com.mealzip.Interface;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import users.com.mealzip.Request.Signup;

public interface Api {
    @POST("signup")
    Call<ResponseBody> createuser(@Body Signup create);
}
