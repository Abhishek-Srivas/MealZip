package users.com.mealzip.ViewModels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import users.com.mealzip.Models.FoodModel;
import users.com.mealzip.Repository.FoodRepo;

public class FoodViewModel extends ViewModel {
    public MutableLiveData<List<FoodModel>> fooddatalist;

    public LiveData<List<FoodModel>> getFeed(Context context) {

        if(fooddatalist==null)
        {
            fooddatalist = new MutableLiveData<List<FoodModel>>();
            FoodRepo repo=new FoodRepo(fooddatalist);
            //we will load it asynchronously from server in this method
            repo.loaddata();
        }
        return fooddatalist;
    }
}
