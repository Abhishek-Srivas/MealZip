package in.kay.mealzip;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class IntroActivity extends AppCompatActivity implements View.OnClickListener {

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
                // startActivity(new Intent(this, SignupActivity.class));
                break;

            case R.id.button2:
                msg="login";
                //startActivity(new Intent(this, LoginActivity.class));
                break;
        }
        Intent baseintent = new Intent(this, AuthActivity.class);
        baseintent.putExtra("authkeyword", msg);
        startActivity(baseintent);
        // finish();
    }
}