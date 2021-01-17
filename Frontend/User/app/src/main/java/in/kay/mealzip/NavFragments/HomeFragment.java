package in.kay.mealzip.NavFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;

import in.kay.mealzip.Adapters.ItemAdapter;
import in.kay.mealzip.LastOrderAdapter;
import in.kay.mealzip.Models.ItemlistModel;
import in.kay.mealzip.Models.LastorderModel;
import in.kay.mealzip.Models.ShoplistModel;
import in.kay.mealzip.R;
import in.kay.mealzip.ViewModel.ItemViewModel;


public class HomeFragment extends Fragment {
    RecyclerView recyclerView,recyclerView1;
    ItemViewModel itemViewModel;
    ItemAdapter itemAdapter;
    Context context;
    TextView price,view_cart;
    RelativeLayout cart;
    Bundle b;
    View view;
    List<ItemlistModel> itemList;
    // int c=0;

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
        view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recyclerview1);
        recyclerView1 = view.findViewById(R.id.recyclerview2);
        cart = view.findViewById(R.id.cartlayout);
        price = view.findViewById(R.id.tv_price);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext());
        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView1.setLayoutManager(layoutManager1);
        //recyclerView.setHasFixedSize(true);
        loaddata();

        List<LastorderModel> orderlist = new ArrayList<>();
        orderlist.add(new LastorderModel("Food 1", "100", R.drawable.dessert));
        orderlist.add(new LastorderModel("Food 2", "150", R.drawable.chinese));
        orderlist.add(new LastorderModel("Food 3", "200", R.drawable.italian));
        orderlist.add(new LastorderModel("Food 4", "250", R.drawable.softdrink));
        LastOrderAdapter adapter = new LastOrderAdapter(orderlist, getContext());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        //Toast.makeText(context, "" + Prefs.getBoolean("added", false), Toast.LENGTH_SHORT).show();
        if (Prefs.getBoolean("added", false)) {
            addtocart();
        }

        return view;
    }

    public void addtocart() {

        cart.setVisibility(View.VISIBLE);
        view_cart = view.findViewById(R.id.tv_cart);

        // Toast.makeText(context,Prefs.getString("price", "") , Toast.LENGTH_SHORT).show();
        price.setText(Prefs.getString("price", ""));
        view_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewcart();
            }
        });
    }

    public void viewcart() {
        // Prefs.putInt("index", 0);
        b = new Bundle();

        b.putString("name", itemList.get(Prefs.getInt("index", 0)).getName());
        b.putString("rating", "4.5");
        b.putString("type", itemList.get(Prefs.getInt("index", 0)).getVeg_type());
        b.putString("category", itemList.get(Prefs.getInt("index", 0)).getCategory());
        b.putString("imgurl", itemList.get(Prefs.getInt("index", 0)).getImgUrl());
        b.putString("price", itemList.get(Prefs.getInt("index", 0)).getPrice());
        b.putString("userId","5fbd38abd288760bac65659f");
        b.putString("itemid",itemList.get(Prefs.getInt("index",0)).getItemId());
        b.putString("shopid",itemList.get(Prefs.getInt("index",0)).get_id());
        Fragment fragment = new CartFragment();
        fragment.setArguments(b);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container1, fragment).commit();
    }



    private void loaddata() {
        itemViewModel = ViewModelProviders.of((FragmentActivity) context).get(ItemViewModel.class);
        itemList=new ArrayList<>();
        itemViewModel.getFeed(context).observe(getViewLifecycleOwner(), new Observer<List<ShoplistModel>>() {
            @Override
            public void onChanged(@Nullable List<ShoplistModel> foodList) {
                for (int i = 0; i < foodList.size(); i++) {
                    String shopid=foodList.get(i).get_id();
                    if (foodList.get(i).getShopItem().size() != 0) {
                        //c++;
                        itemList.clear();
                        for (int j = 0; j < foodList.get(i).getShopItem().size(); j++) {
                            // itemlist.add(new ItemlistModel("Foodname1","4.1","veg","Fast food","150","hg"));
                            itemList.add(new ItemlistModel(foodList.get(i).getShopItem().get(j).getName(), "4.5", foodList.get(i).getShopItem().get(j).getIsveg(), foodList.get(i).getShopItem().get(j).getCategory(), foodList.get(i).getShopItem().get(j).getPriceArray().get(0).getPrice(), foodList.get(i).getShopItem().get(j).getImgUrl(),shopid,foodList.get(i).getShopItem().get(j).get_id()));
                        }
                    }
                }

                itemAdapter = new ItemAdapter(itemList,context, HomeFragment.this);
                recyclerView1.setAdapter(itemAdapter);
                //   Toast.makeText(context, "Food is: "+foodList.get(0).getShopItem().size()+","+c, Toast.LENGTH_SHORT).show();

            }
        });
    }

}