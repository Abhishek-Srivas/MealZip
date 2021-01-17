package users.com.mealzip.ViewModels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import users.com.mealzip.Models.TodaysModel;
import users.com.mealzip.Repository.ToporderRepo;

public class TopViewModel extends ViewModel {
    public MutableLiveData<List<TodaysModel>> topdatalist;

    public LiveData<List<TodaysModel>> getFeed(Context context) {

        if(topdatalist==null)
        {
            topdatalist = new MutableLiveData<List<TodaysModel>>();
            ToporderRepo repo=new ToporderRepo(topdatalist);
            //we will load it asynchronously from server in this method
            repo.loaddata();
        }
        return topdatalist;
    }
}
