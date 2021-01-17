package users.com.mealzip.Helper;

import android.app.Application;
import android.content.ContextWrapper;

import com.pixplicity.easyprefs.library.Prefs;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import users.com.mealzip.Interface.Retroclient;
import users.com.mealzip.Request.RefreshRequest;

public class PrefsApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName("Mealzip")
                .setUseDefaultSharedPreference(true)
                .build();
    }
    public void refreshToken(String token){
        // String retoken = Prefs.getString("refreshToken","");
        RefreshRequest refreshRequest= new RefreshRequest(token);
        Call<ResponseBody> call= Retroclient
                .getInstance()
                .getapi()
                .refreshtoken(refreshRequest);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try{
                    if(response.isSuccessful()){
                        String str= response.body().string();
                        JSONObject jsonObject= new JSONObject(str);
                        String token1 = jsonObject.getString("signAccessToken");
                        String token2 = jsonObject.getString("refreshToken");

                        Prefs.putString("access_token",token1);
                        Prefs.putString("refresh_token",token2);
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
}
