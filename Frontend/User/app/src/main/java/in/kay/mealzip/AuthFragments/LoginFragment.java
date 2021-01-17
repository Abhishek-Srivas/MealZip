package in.kay.mealzip.AuthFragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import es.dmoral.toasty.Toasty;
import in.kay.mealzip.Interface.Retroclient;
import in.kay.mealzip.R;
import in.kay.mealzip.Request.LoginRequest;
import in.kay.mealzip.Response.LoginResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {
    TextView signup, resetpass;
    Button login;
    EditText etemail, et_password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        signup = view.findViewById(R.id.tv_signup);
        login = view.findViewById(R.id.btn_login);
        etemail = view.findViewById(R.id.et_email);
        resetpass = view.findViewById(R.id.tv_resetpass);
        et_password = view.findViewById(R.id.etpassword);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new SignupFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment).commit();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginuser();
            }
        });

        resetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ResetFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment).commit();
            }
        });
        return view;
    }

    private void loginuser() {
        final String email = etemail.getText().toString();
        String password = et_password.getText().toString();
        if (email.isEmpty()) {
            etemail.setError("Email is required");
            etemail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etemail.setError("Please enter a valid email id");
            etemail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            et_password.setError("Password is required");
            et_password.requestFocus();
            return;
        }
       /* if (password.length() < 8) {
            et_password.setError("Password should be atleast 8 char long");
            et_password.requestFocus();
            return;
        } */
        else {
            LoginRequest loginRequest = new LoginRequest(email, password);
            Call<LoginResponse> call = Retroclient
                    .getInstance()
                    .getapi()
                    .loginuser(loginRequest);

            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    //Toasty.success(getContext(),"on response", Toast.LENGTH_LONG, true).show();
                    if (!response.isSuccessful()) {
                        String error = null;
                        try {
                            error = response.errorBody().string();
                            JSONObject jsonObject = new JSONObject(error);
                            String msg = jsonObject.getString("message");
                            Toasty.error(getContext(), msg, Toast.LENGTH_LONG, true).show();

                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        //Toasty.success(getContext(),"success", Toast.LENGTH_LONG, true).show();
                        LoginResponse res = response.body();
                        String msg = res.getMessage();
                        String accesstoken = res.getSignAccessToken();
                        String refreshtoken = res.getRefreshToken();
                        String id= res.getUserId();
                        String name= res.getUserName();
                        String college = res.getCollege();
                        Prefs.putBoolean("registered", true);
                        Prefs.putString("access_token", accesstoken);
                        Prefs.putString("refresh_token", refreshtoken);
                        Prefs.putString("email", email);
                        Prefs.putString("college",college);
                        Prefs.putString("id",id);
                        Prefs.putString("name",name);
                        Toasty.success(getContext(), msg, Toast.LENGTH_LONG, true).show();
                    //    startActivity(new Intent(getActivity(), BaseActivity.class));
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toasty.error(getContext(), t.getMessage(), Toast.LENGTH_LONG, true).show();
                }
            });

        }
    }
}