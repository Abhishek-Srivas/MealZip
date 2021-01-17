package users.com.mealzip.NavFragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.github.ybq.android.spinkit.style.DoubleBounce;

import java.util.List;

import users.com.mealzip.Adapter.FoodAdapter;
import users.com.mealzip.AdditemActivity;
import users.com.mealzip.Models.FoodModel;
import users.com.mealzip.R;
import users.com.mealzip.ViewModels.FoodViewModel;

public class ItemFragment extends Fragment {
    LinearLayout isempty;
    FoodViewModel foodViewModel;
    Context context;
    RecyclerView recyclerView;
    FoodAdapter itemadapter;
    ProgressBar progressBar;
    DoubleBounce pb;
    ConstraintLayout cl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_allitem, container, false);
        recyclerView=view.findViewById(R.id.recycler_view);
        isempty= view.findViewById(R.id.empytycart);
        cl= view.findViewById(R.id.parentcontainer);

        progressBar = view.findViewById(R.id.spin_kit);
        pb = new DoubleBounce();
        progressBar.setIndeterminateDrawable(pb);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        loaddata();
        return view;
    }

    private void loaddata() {
        foodViewModel = ViewModelProviders.of((FragmentActivity) context).get(FoodViewModel.class);
        foodViewModel.getFeed(context).observe(getViewLifecycleOwner(), new Observer<List<FoodModel>>() {
            @Override
            public void onChanged(@Nullable List<FoodModel> foodList) {
                progressBar.setVisibility(View.GONE);
                cl.setVisibility(View.VISIBLE);
                if (foodList.size() == 0) {
                    isempty.setVisibility(View.VISIBLE);
                } else {
                    itemadapter = new FoodAdapter(foodList, context);
                    recyclerView.setAdapter(itemadapter);
                    // Toast.makeText(context, "Food is: "+foodList.get(5).getPriceArray().get(0).getPrice(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}