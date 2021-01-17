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

import users.com.mealzip.Models.TodaysModel;
import users.com.mealzip.R;

public class TopOrderAdapter extends RecyclerView.Adapter<TopOrderAdapter.ViewHolder> {
    List<TodaysModel> list;
    Context context;

    public TopOrderAdapter(List<TodaysModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.statslayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        String name1=list.get(position).getName().toString();
        name1=name1.replace("[","");
        name1=name1.replace("]","");
        String price1= list.get(position).getTotal().toString();
        String qty1= list.get(position).getItemSold().toString();
        String imgurl="https://cutt.ly/7jRuWw7";
        holder.name.setText(name1);
        holder.price.setText(price1);
        holder.qty.setText(qty1);
        Picasso.get()
                .load(imgurl)
                .placeholder(R.drawable.spicy)
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView price,qty,name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.foodimg);
            name=itemView.findViewById(R.id.foodname);
            price=itemView.findViewById(R.id.tv_price);
            qty=itemView.findViewById(R.id.tv_qty);
        }
    }
}
