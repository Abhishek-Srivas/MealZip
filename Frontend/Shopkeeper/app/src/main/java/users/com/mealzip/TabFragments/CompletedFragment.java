package users.com.mealzip.TabFragments;

import android.content.Context;
import android.os.Bundle;

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

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.annimon.stream.function.Predicate;

import java.util.ArrayList;
import java.util.List;

import users.com.mealzip.Adapter.OrderAdapter;
import users.com.mealzip.Models.Orderarray;
import users.com.mealzip.Models.OrdersModel;
import users.com.mealzip.R;
import users.com.mealzip.ViewModels.OrderViewModel;

public class CompletedFragment extends Fragment {
    Context context;
    OrderViewModel orderViewModel;
    OrderAdapter orderAdapter;
    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_completed, container, false);
        recyclerView = view.findViewById(R.id.recycler_view1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        loaddata();
        return view;
    }

    private void loaddata() {
        orderViewModel = ViewModelProviders.of((FragmentActivity) context).get(OrderViewModel.class);
        orderViewModel.getFeed(context).observe(getViewLifecycleOwner(), new Observer<OrdersModel>() {
            @Override
            public void onChanged(@Nullable OrdersModel orderList) {

                List<Orderarray> filteredList = new ArrayList<>();
                List<Orderarray> list = new ArrayList<>();

                for (int i = 0; i < orderList.getOrders().size(); i++) {
                    filteredList = Stream.of(orderList.getOrders().get(i).getOrdersArray()).filter(new Predicate<Orderarray>() {
                        @Override
                        public boolean test(Orderarray item) {
                            return item.getOrderStatus().equalsIgnoreCase("completed");
                        }
                    }).collect(Collectors.toList());
                    list.addAll(filteredList);
                }
                orderAdapter = new OrderAdapter(filteredList, context);
                recyclerView.setAdapter(orderAdapter);
                // Toast.makeText(context, "Food is: "+orderList.getOrders().get(0).getItemName(), Toast.LENGTH_LONG).show();
            }
        });
    }
}