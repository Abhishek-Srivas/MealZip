package users.com.mealzip.IntroUI.Auth.AuthFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;

import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import users.com.mealzip.Interface.Retroclient;
import users.com.mealzip.R;
import users.com.mealzip.Request.ResetpassRequest;

public class ResetpasswordFragment extends Fragment {
    Button reset;
    EditText pass, con_pass;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_resetpassword, container, false);
        pass = view.findViewById(R.id.et_password);
        con_pass = view.findViewById(R.id.et_confirmpassword);
        reset = view.findViewById(R.id.btn_reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetpass();
            }
        });

        return view;
    }

    private void resetpass() {
        String email = Prefs.getString("reset_mail", "");
        String password = pass.getText().toString();
        String con_password = con_pass.getText().toString();
        if (password.isEmpty()) {
            pass.setError("Password is required");
            pass.requestFocus();
            return;
        }
        if (password.length() < 8) {
            pass.setError("Password must be atleast 8 characters long");
            pass.requestFocus();
            return;
        }
        if (!con_password.equals(password)) {
            con_pass.setError("Passwords do not match");
            con_pass.requestFocus();
            return;
        } else {
            ResetpassRequest resetpass = new ResetpassRequest(email, password, con_password);
            Call<ResponseBody> call = Retroclient
                    .getInstance()
                    .getapi()
                    .resetpassword(resetpass);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        if (response.isSuccessful()) {
                            Toasty.success(getContext(), "New password saved", Toast.LENGTH_LONG, true).show();
                            Fragment fragment = new LoginFragment();
                            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.container, fragment).commit();
                        } else {
                            String error = response.errorBody().string();
                            Toasty.error(getContext(), error, Toast.LENGTH_LONG, true).show();

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toasty.error(getContext(), t.getMessage(), Toast.LENGTH_LONG, true).show();
                }
            });
        }
    }
}
