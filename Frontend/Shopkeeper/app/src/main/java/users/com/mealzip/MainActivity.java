package users.com.mealzip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import users.com.mealzip.NavFragments.AddFragment;
import users.com.mealzip.NavFragments.ItemFragment;
import users.com.mealzip.NavFragments.OrderFragment;
import users.com.mealzip.NavFragments.ProfileFragment;
import users.com.mealzip.NavFragments.StatsFragment;

public class MainActivity extends AppCompatActivity {
    ChipNavigationBar chipNavigationBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chipNavigationBar = findViewById(R.id.bottom_nav);
        chipNavigationBar.setItemSelected(R.id.page_1,true);
        getSupportFragmentManager().beginTransaction().replace(R.id.container1,new StatsFragment()).commit();
        menu();
    }

    private void menu() {
        chipNavigationBar.setOnItemSelectedListener(i -> {
            Fragment fragment=null;
            switch(i){
                case R.id.page_1:
                    fragment= new StatsFragment();
                    break;

                case R.id.page_2:
                    fragment= new ItemFragment();
                    break;

                case R.id.page_3:
                    fragment= new AddFragment();
                    break;

                case R.id.page_4:
                    fragment= new OrderFragment();
                    break;

                case R.id.page_5:
                    fragment= new ProfileFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container1,fragment).addToBackStack(null).commit();
        });
    }
    }
