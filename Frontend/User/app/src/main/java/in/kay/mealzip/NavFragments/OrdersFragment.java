package in.kay.mealzip.NavFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import in.kay.mealzip.Adapters.CartAdapter;
import in.kay.mealzip.Adapters.PrevOrderAdapter;
import in.kay.mealzip.Models.CartModel;
import in.kay.mealzip.Models.PrevorderModel;
import in.kay.mealzip.R;

public class OrdersFragment extends Fragment {
    
    RecyclerView recyclerView,recyclerView1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_orders, container, false);
       recyclerView=view.findViewById(R.id.recyclerview3);
        LinearLayoutManager layoutManager =new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        
        recyclerView1=view.findViewById(R.id.recyclerview4);
        LinearLayoutManager layoutManager1 =new LinearLayoutManager(getContext());
        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView1.setLayoutManager(layoutManager1);


        List<CartModel> cartlist= new ArrayList<>();
        cartlist.add(new CartModel("Food 1","100",R.drawable.dessert));
        cartlist.add(new CartModel("Food 2","150",R.drawable.chinese));
        cartlist.add(new CartModel("Food 3","200",R.drawable.softdrink));

        CartAdapter adapter= new CartAdapter(cartlist,getContext());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        List<PrevorderModel> orderlist =new ArrayList<>();
        orderlist.add(new PrevorderModel("Foodname1","4.5","veg","Chinese","150","Picked",R.drawable.chinese));
        orderlist.add(new PrevorderModel("Foodname2","5.0","veg","Italian","200","Cancelled",R.drawable.italian));
        orderlist.add(new PrevorderModel("Foodname3","4.2","veg","Dessert","220","Picked",R.drawable.dessert));
        orderlist.add(new PrevorderModel("Foodname4","3.8","veg","Soft drink","100","Cancelled",R.drawable.softdrink));
        orderlist.add(new PrevorderModel("Foodname1","4.1","veg","Fast food","150","Picked",R.drawable.fastfood));

        PrevOrderAdapter adapter1=new PrevOrderAdapter(orderlist,getContext());
        recyclerView1.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();
        return view;
    }
}