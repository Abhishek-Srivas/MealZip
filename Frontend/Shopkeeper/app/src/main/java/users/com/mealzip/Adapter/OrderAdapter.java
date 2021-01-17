package users.com.mealzip.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import users.com.mealzip.Interface.Retroclient;
import users.com.mealzip.Models.Orderarray;
import users.com.mealzip.R;
import users.com.mealzip.Request.StatusRequest;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    List<Orderarray> list;
    Context context;
    Dialog dialog;
    String status;

    public OrderAdapter(List<Orderarray> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.orderstatus, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        String name1 = list.get(position).getItemName();
        String price1 = list.get(position).getPrice().toString();
        String imgurl = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.eatthis.com%2Fitalian-food-not-in-italy%2F&psig=AOvVaw1wGAPhY3omNDRkfP-5y9dm&ust=1605819318218000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCKibna79jO0CFQAAAAAdAAAAABAJ";
        final String id = list.get(position).get_id();
        ;
        holder.foodname.setText(name1);
        holder.price.setText(price1);
        Picasso.get()
                .load(imgurl)
                .placeholder(R.drawable.spicy)
                .into(holder.imageView);
        status = list.get(position).getOrderStatus();
        if (status.equalsIgnoreCase("completed")) {
            holder.status.setImageResource(R.drawable.ic_completed);
        }
        holder.status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialog(id, holder);
            }
        });
    }

    private void showdialog(String id, ViewHolder holder) {
        dialog = new Dialog(context);
        dialog.show();
        update_stats(id, holder);
    }

    private void update_stats(String id, ViewHolder holder) {
        final RadioGroup radioGroup;
        RadioButton radioButton, radioButton1, radioButton2;
        dialog.setContentView(R.layout.order_statusdialog);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.bg2));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        radioGroup = dialog.findViewById(R.id.radiogrp1);
        Button btn = dialog.findViewById(R.id.update_status);
        Button cancel = dialog.findViewById(R.id.cancel1);
        //radioButton = dialog.findViewById(R.id.pendingbtn);
        //radioButton1 = dialog.findViewById(R.id.completedbtn);
        //radioButton2 = dialog.findViewById(R.id.na_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                if (checkedRadioButtonId == -1) {
                    // No item selected
                } else {
                    if (checkedRadioButtonId == R.id.pendingbtn) {
                        //  Toast.makeText(context, "pending", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                    } else if (checkedRadioButtonId == R.id.completedbtn) {
                        // Toast.makeText(context, "completed", Toast.LENGTH_SHORT).show();
                        doWork(id, holder);
                    } else
                        //Toast.makeText(context, "na", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                }
            }
        });
        cancel.setOnClickListener(v -> dialog.dismiss());

    }

    private void doWork(String id, ViewHolder holder) {
        StatusRequest request = new StatusRequest(id);
        Call call = Retroclient.getInstance()
                .getapi()
                .changestatus(request);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    Toasty.success(context, "Order status has been updated", Toasty.LENGTH_SHORT, true).show();
                    if (status.equalsIgnoreCase("completed")) {
                        holder.status.setImageResource(R.drawable.ic_completed);
                    }
                    dialog.dismiss();
                } else {
                    Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView, status;
        TextView foodname, price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.foodimg);
            foodname = itemView.findViewById(R.id.foodname);
            price = itemView.findViewById(R.id.itemprice);
            status = itemView.findViewById(R.id.pendingbtn);
        }
    }
}
