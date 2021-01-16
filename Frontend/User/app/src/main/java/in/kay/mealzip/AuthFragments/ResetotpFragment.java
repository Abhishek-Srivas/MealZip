package in.kay.mealzip.AuthFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;

import es.dmoral.toasty.Toasty;
import in.kay.mealzip.Request.Otpverify;
import in.kay.mealzip.Request.Resendotp;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import in.kay.mealzip.Interface.Retroclient;
import in.kay.mealzip.R;

public class ResetotpFragment extends Fragment {
    EditText otp;
    TextView resend;
    Button verify_otp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_resetotp, container, false);
        String email = Prefs.getString("reset_mail","");
        otp= view.findViewById(R.id.et_otp);
        resend=view.findViewById(R.id.tv_resend);
        verify_otp=view.findViewById(R.id.btn_verify);

        verify_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verify();
            }
        });
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendotp();
            }
        });
        return view;
    }

    private void resendotp() {
        String email = Prefs.getString("reset_mail","");
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
                        Toasty.error(getContext(), str, Toast.LENGTH_LONG, true).show();
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

    private void verify() {
        String otp1 = otp.getText().toString();
        String email = Prefs.getString("reset_mail", "");
        Otpverify verify = new Otpverify(email, otp1);
        Call<ResponseBody> call = Retroclient
                .getInstance()
                .getapi()
                .resetotpcheck(verify);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful()) {
                        String res = response.body().string();
                        Toasty.success(getContext(), res, Toast.LENGTH_LONG, true).show();
                        Fragment fragment= new ResetpasswordFragment();
                        FragmentTransaction fragmentTransaction= getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.container, fragment).commit();
                    }
                    else
                    {
                        String error =response.errorBody().string();
                        Toasty.error(getContext(), error, Toast.LENGTH_LONG, true).show();

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
    }
