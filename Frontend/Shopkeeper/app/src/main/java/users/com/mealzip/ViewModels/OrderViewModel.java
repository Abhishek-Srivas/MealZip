package users.com.mealzip.ViewModels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import users.com.mealzip.Models.OrdersModel;
import users.com.mealzip.Repository.OrderRepo;

public class OrderViewModel extends ViewModel {
    public MutableLiveData<OrdersModel> orderdatalist;

    public LiveData<OrdersModel> getFeed(Context context) {

        if(orderdatalist==null)
        {
            orderdatalist = new MutableLiveData<OrdersModel>();
            OrderRepo repo=new OrderRepo(orderdatalist);
            //we will load it asynchronously from server in this method
            repo.loaddata();
        }
        return orderdatalist;
    }
}
