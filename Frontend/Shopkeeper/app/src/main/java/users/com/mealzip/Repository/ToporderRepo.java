package users.com.mealzip.Repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import users.com.mealzip.Interface.Retroclient;
import users.com.mealzip.Models.TodaysModel;
import users.com.mealzip.Request.GettopRequest;

public class ToporderRepo {
    private MutableLiveData<List<TodaysModel>> toplist;

    public ToporderRepo(MutableLiveData<List<TodaysModel>> toplist) {
        this.toplist = toplist;
        loaddata();
    }
    public MutableLiveData<List<TodaysModel>> loaddata() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = sdf.format(calendar.getTime());
        GettopRequest gettopRequest= new GettopRequest("13/01/2021","5f9dbc0d17de675208bc323e");
        Call<List<TodaysModel>> call = Retroclient.getInstance().getapi().gettop(gettopRequest);


        call.enqueue(new Callback<List<TodaysModel>>() {
            @Override
            public void onResponse(Call<List<TodaysModel>> call, Response<List<TodaysModel>> response) {
                //finally we are setting the list to our MutableLiveData
                if (response.isSuccessful()) {
                    toplist.setValue(response.body());
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
            public void onFailure(Call<List<TodaysModel>> call, Throwable t) {
                Log.d("failure",t.getMessage());
            }
        });
        return  toplist;
    }
}
