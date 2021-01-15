package users.com.mealzip.IntroUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import users.com.mealzip.IntroUI.Auth.AuthActivity;
import users.com.mealzip.R;

public class IntroActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        findViewById(R.id.btnsignup).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);

        YoYo.with(Techniques.Bounce)
                .duration(2000)
                .repeat(YoYo.INFINITE)
                .playOn(findViewById(R.id.imageView2));
    }
    @Override
    public void onClick(View v) {
        String msg="";
        switch (v.getId()) {
            case R.id.btnsignup:
                msg="signup";
                break;

            case R.id.button2:
                msg="login";
                break;
        }
        Intent baseintent = new Intent(this, AuthActivity.class);
        baseintent.putExtra("authkeyword", msg);
        startActivity(baseintent);
    }
    }
