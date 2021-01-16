package in.kay.mealzip;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import in.kay.mealzip.AuthFragments.LoginFragment;
import in.kay.mealzip.AuthFragments.SignupFragment;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        String msg = getIntent().getStringExtra("authkeyword");
        Fragment fragment=null;
        if(msg.equals("login"))
            fragment= new LoginFragment();
        else
            fragment= new SignupFragment();
        FragmentManager fragmentManager= getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container,fragment).commit();
    }
}