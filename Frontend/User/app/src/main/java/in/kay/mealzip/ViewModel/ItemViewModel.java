package in.kay.mealzip.ViewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import in.kay.mealzip.Models.ShoplistModel;
import in.kay.mealzip.Repo.ItemRepo;

public class ItemViewModel extends ViewModel {

    public MutableLiveData<List<ShoplistModel>> itemlist;

    public LiveData<List<ShoplistModel>> getFeed(Context context) {

        if(itemlist==null)
        {
            itemlist = new MutableLiveData<List<ShoplistModel>>();
            ItemRepo repo=new ItemRepo(itemlist);
            //we will load it asynchronously from server in this method
            repo.loaddata();
        }
        return itemlist;
    }
}
