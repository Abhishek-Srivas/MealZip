package users.com.mealzip.Repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import users.com.mealzip.Interface.Retroclient;
import users.com.mealzip.Models.OrdersModel;
import users.com.mealzip.Request.OrderRequest;

public class OrderRepo {
    private MutableLiveData<OrdersModel> orderlist;

    public OrderRepo(MutableLiveData<OrdersModel> orderlist) {
        this.orderlist = orderlist;
        loaddata();
    }

    public MutableLiveData<OrdersModel> loaddata() {
        OrderRequest orderRequest=new OrderRequest(Prefs.getString("userID","5f9dbc0d17de675208bc323e"));

        Call<OrdersModel> call = Retroclient.getInstance()
                .getapi()
                .getorderstatus(orderRequest);

        call.enqueue(new Callback<OrdersModel>() {
            @Override
            public void onResponse(Call<OrdersModel> call, Response<OrdersModel> response) {
                //finally we are setting the list to our MutableLiveData
                if (response.isSuccessful()) {
                    orderlist.setValue(response.body());
                    Log.d("rlog",response.body().toString());
                    String s= response.body().toString();
                }

                else
                {
                    try {
                        Log.d("rlogerror",response.errorBody().string());
                    } catch (IOException e) {
                        Log.d("rlogerror",e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<OrdersModel> call, Throwable t) {
                Log.d("failure",t.getMessage());
            }
        });
        return orderlist;
    }
}
