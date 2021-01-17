package users.com.mealzip.NavFragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.ybq.android.spinkit.style.DoubleBounce;

import java.util.ArrayList;
import java.util.List;

import users.com.mealzip.Adapter.TopOrderAdapter;
import users.com.mealzip.Models.TodaysModel;
import users.com.mealzip.R;
import users.com.mealzip.ViewModels.TopViewModel;

public class StatsFragment extends Fragment {
    TopViewModel topViewModel;
    Context context;
    RecyclerView recyclerView;
    TopOrderAdapter itemadapter;
    BarChart barChart;
    BarData barData;
    BarDataSet barDataSet;
    ArrayList barentries;
    String[] days={"M","T","W","TH","F","SAT","SUN"};
    ProgressBar progressBar;
    DoubleBounce pb;

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
        View view= inflater.inflate(R.layout.fragment_stats, container, false);
        recyclerView=view.findViewById(R.id.recycler_view1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        //Spinkit progress bar obj initialisation
        progressBar = view.findViewById(R.id.spin_kit);
        pb = new DoubleBounce();
        progressBar.setIndeterminateDrawable(pb);

        barChart=view.findViewById(R.id.graph);
        getentries();
        barDataSet= new BarDataSet(barentries,"Data set");
        barData=new BarData(barDataSet);
        barChart.setData(barData);
        barDataSet.setColors(ColorTemplate.rgb("#fed8b1"));
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(12f);
        barDataSet.setBarBorderColor(Color.rgb(251,133,0));
        barDataSet.setBarBorderWidth(0.5f);
        barDataSet.setStackLabels(days);
        loaddata();
        return view;
    }

    private void loaddata() {
        topViewModel = ViewModelProviders.of((FragmentActivity) context).get(TopViewModel.class);
        topViewModel.getFeed(context).observe(getViewLifecycleOwner(), new Observer<List<TodaysModel>>() {
            @Override
            public void onChanged(@Nullable List<TodaysModel> topList) {
                itemadapter = new TopOrderAdapter(topList, context);
                progressBar.setVisibility(View.GONE);
                barChart.setVisibility(View.VISIBLE);
                recyclerView.setAdapter(itemadapter);
                //Toast.makeText(context, "Top order is: "+topList.get(0).getName().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getentries() {
        barentries= new ArrayList<>();
        barentries.add(new BarEntry(1f,10));
        barentries.add(new BarEntry(2f,5));
        barentries.add(new BarEntry(3f,6));
        barentries.add(new BarEntry(4f,1));
        barentries.add(new BarEntry(5f,7));
        barentries.add(new BarEntry(6f,9));
        barentries.add(new BarEntry(7f,11));

    }
}
