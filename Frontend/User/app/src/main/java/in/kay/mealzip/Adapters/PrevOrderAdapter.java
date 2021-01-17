package in.kay.mealzip.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.kay.mealzip.Models.PrevorderModel;
import in.kay.mealzip.R;

public class PrevOrderAdapter extends RecyclerView.Adapter<PrevOrderAdapter.ViewHolder> {
    List<PrevorderModel> list;
    Context context;

    public PrevOrderAdapter(List<PrevorderModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.prevorders, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        int res= list.get(position).getImgres();
        String name1=list.get(position).getName();
        String price1= list.get(position).getPrice();
        String category1= list.get(position).getCategory();
        String rating1= list.get(position).getRating();
        String status1=list.get(position).getOrderstatus();
        holder.imageView.setImageResource(res);
        holder.name.setText(name1);
        holder.price.setText("Rs. "+price1);
        holder.rating.setText(rating1);
        holder.category.setText(category1);
        holder.status.setText(status1);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView,typeimg;
        TextView name,price,rating,category,status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image);
            name=itemView.findViewById(R.id.foodname);
            price=itemView.findViewById(R.id.tv_price);
            category=itemView.findViewById(R.id.tv_category);
            rating=itemView.findViewById(R.id.tv_rating);
            typeimg=itemView.findViewById(R.id.type);
            status=itemView.findViewById(R.id.tvstatus);
        }
    }
}