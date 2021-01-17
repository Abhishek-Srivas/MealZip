package in.kay.mealzip.Repo;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.List;

import in.kay.mealzip.Interface.Retroclient;
import in.kay.mealzip.Models.ShoplistModel;
import in.kay.mealzip.Request.ShoplistRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemRepo {
    private MutableLiveData<List<ShoplistModel>> foodlist;

    public ItemRepo(MutableLiveData<List<ShoplistModel>> foodlist) {
        this.foodlist = foodlist;
        loaddata();
    }

    public MutableLiveData<List<ShoplistModel>> loaddata() {
        String college = "college";
        ShoplistRequest shoplistRequest= new ShoplistRequest(college);
        Call<List<ShoplistModel>> call = Retroclient.getInstance().getapi().getshops(shoplistRequest);


        call.enqueue(new Callback<List<ShoplistModel>>() {
            @Override
            public void onResponse(Call<List<ShoplistModel>> call, Response<List<ShoplistModel>> response) {
                //finally we are setting the list to our MutableLiveData
                if (response.isSuccessful()) {
                    foodlist.setValue(response.body());
                    Log.d("rlog",response.body().toString());
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
            public void onFailure(Call<List<ShoplistModel>> call, Throwable t) {
                Log.d("failure",t.getMessage());
            }
        });
        return  foodlist;
    }
}
