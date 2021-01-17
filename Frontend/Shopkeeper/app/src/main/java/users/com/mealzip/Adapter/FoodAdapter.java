package users.com.mealzip.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import users.com.mealzip.Models.FoodModel;
import users.com.mealzip.R;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    List<FoodModel> list;
    Context context;

    public FoodAdapter(List<FoodModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.added_itemlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        String imgurl = list.get(position).getImgUrl();
        String title = list.get(position).getName();
        String type= list.get(position).getIsveg();
        int length=list.get(position).getPriceArray().size();
        if (length>0) {
            int price = list.get(position).getPriceArray().get(0).getPrice();
            holder.sizes.setText("Available sizes-" + length);
            if (length == 1) {
                holder.price.setText("₹ " + price);
            }
            else{
                int price1 = list.get(position).getPriceArray().get(1).getPrice();
                int rate = Math.max(price, price1);
                int rate1 = Math.min(price,price1);
                holder.price.setText("₹" +rate1 +"-" +rate);
            }
        }
        String category= list.get(position).getCategory();
        Picasso.get()
                .load(imgurl)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.food_img);
        holder.food_name.setText(title);
        holder.category.setText(category);

        if(type.equalsIgnoreCase("true")){
            holder.veg_type.setImageResource(R.drawable.ic_veg);
        }
        else{
            holder.veg_type.setImageResource(R.drawable.ic_nonveg);
        }
        /*holder.rowlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext() , Coursedetail.class);
                intent.putExtra("position",holder.getAdapterPosition());
                context.startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView food_img,veg_type;
        TextView food_name,sizes,category,price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            food_img= itemView.findViewById(R.id.foodimg);
            veg_type=itemView.findViewById(R.id.card_type);
            food_name=itemView.findViewById(R.id.foodname);
            sizes=itemView.findViewById(R.id.av_size);
            category=itemView.findViewById(R.id.card_category);
            price=itemView.findViewById(R.id.cardprice);
        }
    }
}
