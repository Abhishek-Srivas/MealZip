package in.kay.mealzip;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;

import in.kay.mealzip.NavFragments.CartFragment;
import in.kay.mealzip.NavFragments.HomeFragment;
import in.kay.mealzip.NavFragments.OrdersFragment;
import in.kay.mealzip.NavFragments.ProfileFragment;
import in.kay.mealzip.R;

public class BaseActivity extends AppCompatActivity implements PaymentResultListener {

    ChipNavigationBar chipNavigationBar;
    Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        chipNavigationBar = findViewById(R.id.bottom_nav);
        chipNavigationBar.setItemSelected(R.id.page_1,true);
        getSupportFragmentManager().beginTransaction().replace(R.id.container1,new HomeFragment()).commit();
        menu();
    }

    private void menu() {
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment=null;
                switch(i){
                    case R.id.page_1:
                        fragment= new HomeFragment();
                        break;

                    case R.id.page_2:
                        fragment= new CartFragment();
                       // Toast.makeText(BaseActivity.this, "cart", Toast.LENGTH_SHORT).show();
                       // HomeFragment fragment1 = (HomeFragment) getSupportFragmentManager().findFragmentById(R.id.container1);
                        //fragment1.viewcart();
                        break;

                    case R.id.page_3:
                        fragment= new OrdersFragment();
                        break;

                    case R.id.page_4:
                        fragment= new ProfileFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container1,fragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public void onPaymentSuccess(String s) {

        CartFragment fragment = (CartFragment) getSupportFragmentManager().findFragmentById(R.id.container1);
        try {
            fragment.placeorder();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Failure "+s, Toast.LENGTH_SHORT).show();
    }
}