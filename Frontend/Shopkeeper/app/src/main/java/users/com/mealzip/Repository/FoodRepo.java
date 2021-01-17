package users.com.mealzip.Repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import users.com.mealzip.Interface.Retroclient;
import users.com.mealzip.Models.FoodModel;
import users.com.mealzip.Request.FoodRequest;

public class FoodRepo {
    private MutableLiveData<List<FoodModel>> foodlist;

    public FoodRepo(MutableLiveData<List<FoodModel>> foodlist) {
        this.foodlist = foodlist;
        loaddata();
    }

    public MutableLiveData<List<FoodModel>> loaddata() {
        String email = Prefs.getString("email","");
        FoodRequest foodRequest= new FoodRequest(email);
        Call<List<FoodModel>> call = Retroclient.getInstance().getapi().getitems(foodRequest);


        call.enqueue(new Callback<List<FoodModel>>() {
            @Override
            public void onResponse(Call<List<FoodModel>> call, Response<List<FoodModel>> response) {
                //finally we are setting the list to our MutableLiveData
                if (response.isSuccessful()) {
                    List<FoodModel> temp=response.body();
                    Collections.reverse(temp);
                    foodlist.setValue(temp);
                    // Log.d("rlog",response.body().toString());
                }

                else{
                    try {
                        Log.d("rlogerror",response.errorBody().string());
                    } catch (IOException e) {
                        Log.d("rlogerror",e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<List<FoodModel>> call, Throwable t) {
                Log.d("failure",t.getMessage());
            }
        });
        return  foodlist;
    }
}
