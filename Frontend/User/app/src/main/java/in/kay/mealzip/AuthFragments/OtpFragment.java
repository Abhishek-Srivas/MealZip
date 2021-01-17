package in.kay.mealzip.AuthFragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;

import es.dmoral.toasty.Toasty;
import in.kay.mealzip.Request.Otpverify;
import in.kay.mealzip.Request.Resendotp;
import in.kay.mealzip.Response.OtpResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import in.kay.mealzip.Interface.Retroclient;
import in.kay.mealzip.R;

public class OtpFragment extends Fragment {

    Button btn_verify;
    EditText etotp;
    TextView timer;
    private OtpView otpView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_otp, container, false);
        btn_verify= view.findViewById(R.id.btn_verify);
        etotp= view.findViewById(R.id.et_otp);
        timer=view.findViewById(R.id.timer);
        otpView = view.findViewById(R.id.otp_view);
        otpView.setOtpCompletionListener(new OnOtpCompletionListener() {
            @Override public void onOtpCompleted(String otp) {
                // do Stuff
                btn_verify.setClickable(true);
            }
        });
        timer();

        btn_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verify();
            }
        });

        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendotp();
            }
        });
        return view;
    }

    private void verify() {
        String otp = otpView.getText().toString();
        final String email = Prefs.getString("email","");
        Otpverify verify= new Otpverify(email,otp);
        Call<OtpResponse> call= Retroclient
                .getInstance()
                .getapi()
                .verifyuser(verify);

        call.enqueue(new Callback<OtpResponse>() {
            @Override
            public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {
                //Toast.makeText(getActivity(),"on response",Toast.LENGTH_LONG).show();
                //Log.d("onresponse",response.toString());
                try {
                    if (response.code()==200)
                    {
                        Prefs.putBoolean("registered",true);
                        OtpResponse res = response.body();
                        String msg= res.getMessage();
                        String accesstoken = res.getSignAccessToken();
                        String refreshtoken = res.getRefreshToken();
                        String id= res.getUserId();
                        String name= res.getUserName();
                        String college = res.getCollege();
                        Prefs.putString("access_token", accesstoken);
                        Prefs.putString("refresh_token", refreshtoken);
                        Prefs.putString("my_email",email);
                        Prefs.putString("college",college);
                        Prefs.putString("id",id);
                        Prefs.putString("name",name);

                        Toasty.success(getContext(), msg, Toast.LENGTH_LONG, true).show();
                     //   startActivity(new Intent(getActivity(), BaseActivity.class));
                    }
                    else
                    {
                        String str = response.errorBody().string();
                        Toasty.warning(getContext(), str, Toast.LENGTH_LONG, true).show();
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<OtpResponse> call, Throwable t) {
                Toasty.error(getContext(),t.getMessage(), Toast.LENGTH_LONG, true).show();
            }
        });
    }

    private void resendotp() {
        String email = Prefs.getString("email","");
        Resendotp otpresend= new Resendotp(email);
        Call<ResponseBody> call = Retroclient
                .getInstance()
                .getapi()
                .resend_otp(otpresend);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful()) {
                        Toasty.success(getContext(), "New OTP has been sent to your mail", Toast.LENGTH_LONG).show();
                    }

                    else {
                        String str = null;
                        str = response.errorBody().string();
                        Toasty.warning(getContext(), str, Toast.LENGTH_LONG, true).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toasty.error(getContext(),t.getMessage(), Toast.LENGTH_LONG, true).show();
            }
        });

    }

    private void timer() {
        new CountDownTimer(120000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText("Time remaining: 00:" + millisUntilFinished / 1000);
                timer.setClickable(false);
            }

            public void onFinish() {
                timer.setText("Resend OTP");
                timer.setClickable(true);
            }

        }.start();
    }
}