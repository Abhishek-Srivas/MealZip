package in.kay.mealzip;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.pixplicity.easyprefs.library.Prefs;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkuser();
            }
        }, 1500);
    }

    private void checkuser() {
        boolean user= Prefs.getBoolean("registered",false);
        if(user)
        {
            //startActivity(new Intent(this,BaseActivity.class));
        }
        else{
            startActivity(new Intent(this,IntroActivity.class));
        }
    }
}