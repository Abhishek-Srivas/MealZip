package users.com.mealzip.NavFragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.pixplicity.easyprefs.library.Prefs;

import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import users.com.mealzip.AdditemActivity;
import users.com.mealzip.Interface.Retroclient;
import users.com.mealzip.IntroUI.IntroActivity;
import users.com.mealzip.R;
import users.com.mealzip.Request.LoginRequest;
import users.com.mealzip.Request.TimeRequest;
import users.com.mealzip.Response.LoginResponse;

public class ProfileFragment extends Fragment {
    TextView logout;
    TextView time;
    Dialog dialog;
    ProgressBar progressBar;
    DoubleBounce pb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        progressBar = view.findViewById(R.id.spin_kit);
        pb = new DoubleBounce();
        progressBar.setIndeterminateDrawable(pb);

        logout=view.findViewById(R.id.logout);
        time = view.findViewById(R.id.etwork_hrs);
        dialog= new Dialog(getActivity());

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Prefs.putBoolean("registered",false);
                startActivity(new Intent(getActivity(), IntroActivity.class));
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                setTime();
            }
        });
        return view;
    }

    private void setTime() {
        dialog.setContentView(R.layout.merchant_profile);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.bg2));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        ImageView cancel,done;
        EditText start_time,end_time;
        cancel= dialog.findViewById(R.id.iv_cancel);
        done= dialog.findViewById(R.id.iv_done);
        start_time=dialog.findViewById(R.id.stime);
        end_time=dialog.findViewById(R.id.etime);
        String st=start_time.getText().toString();
        String et= end_time.getText().toString();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

              /*  if (start_time.getText().toString().isEmpty())
                {
                    start_time.setError("Enter opening time");
                    start_time.requestFocus();
                    return;
                }
                if (end_time.getText().toString().isEmpty()){
                    end_time.setError("Enter closing time");
                    end_time.requestFocus();
                    return;
                }*/

                    time.setText(start_time.getText().toString()+"am -"+end_time.getText().toString()+"pm");
                    dialog.dismiss();
                    doWork();

            }
        });
    }

    private void doWork() {
        String email=Prefs.getString("email","");
        TimeRequest timeRequest= new TimeRequest(email,time.getText().toString());
        Call<ResponseBody> call= Retroclient
                .getInstance()
                .getapi()
                .setTime(timeRequest);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressBar.setVisibility(View.GONE);
                if(response.isSuccessful()){
                    Toasty.info(getContext(),"Working hours updated").show();
                }
                else {
                    Toasty.info(getContext(),"Something went wrong").show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Toasty.info(getContext(),"Something went wrong").show();
            }
        });
    }
}