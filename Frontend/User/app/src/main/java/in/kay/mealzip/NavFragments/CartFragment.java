package in.kay.mealzip.NavFragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pixplicity.easyprefs.library.Prefs;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import es.dmoral.toasty.Toasty;
import in.kay.mealzip.Interface.Retroclient;
import in.kay.mealzip.R;
import in.kay.mealzip.Request.OrderRequest;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment implements PaymentResultListener {

    private static final String TAG = "Payment error";
    ImageView food_image,type;
    int bill;String amount;
    TextView tv_foodname,rating,category,price,price1,total,sumtotal,tv_pay;
    Bundle b;
    LinearLayout layout;
    JSONObject jsonObject;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_cart, container, false);
        //Checkout.preload(getContext());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        food_image=view.findViewById(R.id.image);
        type=view.findViewById(R.id.type);
        tv_foodname=view.findViewById(R.id.foodname);
        rating=view.findViewById(R.id.tv_rating);
        category=view.findViewById(R.id.tv_category);
        price=view.findViewById(R.id.tv_price);
        price1=view.findViewById(R.id.tvprice);
        total=view.findViewById(R.id.tv_totalprice);
        sumtotal=view.findViewById(R.id.tv_totalprice1);
        tv_pay=view.findViewById(R.id.tv_order);
        layout=view.findViewById(R.id.orderbar);
        b= this.getArguments();
        if(b!=null){
            //Toast.makeText(getContext(), b.getString("name"), Toast.LENGTH_SHORT).show();
            tv_foodname.setText(b.getString("name"));
            rating.setText(b.getString("rating"));
            category.setText(b.getString("category"));
            price.setText("₹"+b.getString("price"));
            price1.setText("₹"+b.getString("price"));
            total.setText("₹"+b.getString("price"));
            sumtotal.setText("₹"+b.getString("price"));

            bill=Integer.parseInt(b.getString("price"))*100;
            amount=Integer.toString(bill);

            String imgurl=b.getString("imgurl");
            String typeimg=b.getString("type");
            Picasso.get()
                    .load(imgurl)
                    .placeholder(R.drawable.ic_placeholder)
                    .into(food_image);
            if(typeimg.equalsIgnoreCase("true")){
                type.setImageResource(R.drawable.ic_veg);
            }
            else{
                type.setImageResource(R.drawable.ic_nonveg);
            }

        }
        else {
            //Toast.makeText(getContext(), "null", Toast.LENGTH_SHORT).show();
            //Bundle is null
            try {
                jsonObject=new JSONObject(Prefs.getString("saveCart",""));
                tv_foodname.setText(jsonObject.getString("name"));
                rating.setText(jsonObject.getString("rating"));
                category.setText(jsonObject.getString("category"));
                price.setText("₹"+jsonObject.getString("price"));
                price1.setText("₹"+jsonObject.getString("price"));
                total.setText("₹"+jsonObject.getString("price"));
                sumtotal.setText("₹"+jsonObject.getString("price"));

                bill=Integer.parseInt(jsonObject.getString("price"))*100;
                amount=Integer.toString(bill);
                String imgurl=jsonObject.getString("imgurl");
                String typeimg=jsonObject.getString("type");
                Picasso.get()
                        .load(imgurl)
                        .placeholder(R.drawable.ic_placeholder)
                        .into(food_image);
                if(typeimg.equalsIgnoreCase("true")){
                    type.setImageResource(R.drawable.ic_veg);
                }
                else{
                    type.setImageResource(R.drawable.ic_nonveg);
                }
               // Toast.makeText(getContext(), "Name is "+jsonObject.getString("name"), Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
               // e.printStackTrace();
                Toast.makeText(getContext(), ""+e, Toast.LENGTH_SHORT).show();

            }
        }
        tv_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "on click", Toast.LENGTH_SHORT).show();
                startPayment();
            }
        });
    }
    public void startPayment() {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_88mKGL260K9Vf3");
        /**
         * Set your logo here
         */
        //checkout.setImage(R.drawable.logo);

        /**
         * Reference to current activity
         */
        final Activity activity = this.getActivity();

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name", "College scout");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            // options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount",amount );//pass amount in currency subunits
            // options.put("prefill.email", "collegescout@example.com");
            // options.put("prefill.contact","7339778906");
            checkout.open(activity, options);
        } catch(Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        //Toast.makeText(getContext(), "Payment successful", Toast.LENGTH_SHORT).show();
        try {
            placeorder();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void placeorder() throws JSONException {
        Prefs.putBoolean("added", false);
        Prefs.putString("price", "");
        layout.setVisibility(View.GONE);
        Toasty.success(getActivity(), "Payment successful and order placed", Toast.LENGTH_LONG, true).show();
//        b = this.getArguments();
//        if (b != null) {
//            OrderRequest orderRequest = new OrderRequest(b.getString("shopid"), b.getString("userId"), b.getString("itemid"), b.getString("name"), "28/11/20", b.getString("price"), b.getString("rating"));
//            Call<ResponseBody> call = Retroclient
//                    .getInstance()
//                    .getapi()
//                    .placeorder(orderRequest);
//            call.enqueue(new Callback<ResponseBody>() {
//                @Override
//                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                    if (response.isSuccessful()) {
//                        Prefs.putBoolean("added", false);
//                        Prefs.putString("price", "");
//                        layout.setVisibility(View.GONE);
//                        Toasty.success(getActivity(), "Payment successful and order placed", Toast.LENGTH_LONG, true).show();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    Toast.makeText(getActivity(), "Error occured, try again!", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//        else {
//            OrderRequest orderRequest = new OrderRequest(jsonObject.getString("shopid"), jsonObject.getString("userId"), jsonObject.getString("itemid"), jsonObject.getString("name"), "28/11/20", jsonObject.getString("price"), jsonObject.getString("rating"));
//            Call<ResponseBody> call = Retroclient
//                    .getInstance()
//                    .getapi()
//                    .placeorder(orderRequest);
//            call.enqueue(new Callback<ResponseBody>() {
//                @Override
//                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                    if (response.isSuccessful()) {
//                        Prefs.putBoolean("added", false);
//                        Prefs.putString("price", "");
//                        layout.setVisibility(View.GONE);
//                        Toasty.success(getActivity(), "Payment successful and order placed", Toast.LENGTH_LONG, true).show();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    Toast.makeText(getActivity(), "Error occured, try again!", Toast.LENGTH_SHORT).show();
//                }
//            });
//            Toast.makeText(getActivity(), "null", Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(getActivity(), "Error occured, try again!", Toast.LENGTH_SHORT).show();
    }
}