package in.kay.mealzip;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.kay.mealzip.Models.LastorderModel;

public class LastOrderAdapter extends RecyclerView.Adapter<LastOrderAdapter.ViewHolder> {
    List<LastorderModel> list;
    Context context;

    public LastOrderAdapter(List<LastorderModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lastordercard, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
       int res= list.get(position).getImageres();
       String name1=list.get(position).getName();
       String price1= list.get(position).getPrice();

       holder.imageView.setImageResource(res);
       holder.name.setText(name1);
       holder.price.setText("Rs. "+price1);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name,price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        imageView=itemView.findViewById(R.id.img_food);
        name=itemView.findViewById(R.id.foodname);
        price=itemView.findViewById(R.id.tv_price);
        }
    }
}