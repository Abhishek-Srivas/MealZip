package in.kay.mealzip.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.pixplicity.easyprefs.library.Prefs;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import in.kay.mealzip.Models.ItemlistModel;
import in.kay.mealzip.NavFragments.HomeFragment;
import in.kay.mealzip.R;


public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    List<ItemlistModel> list;
    Context context;
    Fragment fragment;
    int count=0;
    int c=0;

    public ItemAdapter(List<ItemlistModel> list, Context context, Fragment fragment) {
        this.list = list;
        this.context = context;
        this.fragment=fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.itemlistlayout, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        String img=list.get(position).getImgUrl();
        String name1=list.get(position).getName();
        final String price1= list.get(position).getPrice();
        String category1= list.get(position).getCategory();
        String rating1= list.get(position).getRating();
        String type= list.get(position).getVeg_type();
        holder.name.setText(name1);
        holder.price.setText("₹ "+price1);
        holder.rating.setText(rating1);
        holder.category.setText(category1);
        Picasso.get()
                .load(img)
                .placeholder(R.drawable.ic_placeholder)
                .into(holder.imageView);
        if(type.equalsIgnoreCase("true")){
            holder.typeimg.setImageResource(R.drawable.ic_veg);
        }
        else{
            holder.typeimg.setImageResource(R.drawable.ic_nonveg);
        }

        holder.ivadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObject=new JSONObject();
                try {
                    SaveDatatoJSON(jsonObject,position);
                } catch (JSONException e) {
                    Toast.makeText(context, "error"+e, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

                c++;
                holder.ivadd.setVisibility(View.INVISIBLE);
                holder.layout.setVisibility(View.VISIBLE);
                holder.ivplus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                 increment();
                    }
                });
                holder.ivminus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        decrement();
                    }
                });
                Prefs.putBoolean("added",true);
                Prefs.putString("price","₹"+price1);
                Prefs.putInt("index",position);
                if(c==1)
                Prefs.putInt("prev",position);
                ((HomeFragment) fragment).addtocart();
            }

            private void decrement() {
                if(count<=1){
                    holder.ivadd.setVisibility(View.VISIBLE);
                    holder.layout.setVisibility(View.INVISIBLE);
                }
                else{
                count--;
                holder.qty.setText(Integer.toString(count));

            }
            }

            private void increment() {
                count++;
                holder.qty.setText(Integer.toString(count));
            }
        });
    }

    private void SaveDatatoJSON(JSONObject jsonObject,int position) throws JSONException {
        jsonObject.put("name", list.get(position).getName());
        jsonObject.put("rating", "4.5");
        jsonObject.put("type", list.get(position).getVeg_type());
        jsonObject.put("category", list.get(position).getCategory());
        jsonObject.put("imgurl", list.get(position).getImgUrl());
        jsonObject.put("price", list.get(position).getPrice());
        jsonObject.put("userId","5fbd38abd288760bac65659f");
        jsonObject.put("itemid",list.get(position).getItemId());
        jsonObject.put("shopid",list.get(position).get_id());
        Prefs.putString("saveCart",jsonObject.toString());
    }
    @Override
    public int getItemCount() {
        return list.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView,typeimg,ivadd,ivplus,ivminus;
        LinearLayout layout;
        TextView name,price,rating,category,qty;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image);
            name=itemView.findViewById(R.id.foodname);
            price=itemView.findViewById(R.id.tv_price);
            category=itemView.findViewById(R.id.tv_category);
            rating=itemView.findViewById(R.id.tv_rating);
            typeimg=itemView.findViewById(R.id.type);
            ivadd=itemView.findViewById(R.id.iv_add);
            layout=itemView.findViewById(R.id.linearlay);
            qty=itemView.findViewById(R.id.tv_qty);
            ivplus=itemView.findViewById(R.id.iv_plus);
            ivminus=itemView.findViewById(R.id.iv_minus);
        }
    }
}