package users.com.mealzip.IntroUI.Auth.AuthFragments;

import android.content.ContextWrapper;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;

import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import users.com.mealzip.Interface.Retroclient;
import users.com.mealzip.R;
import users.com.mealzip.Request.Signup;


public class SignupFragment extends Fragment {
    TextView login;
    EditText etpassword, etconfirmpassword, et_email, et_number;
    View view;
    Button btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_signup, container, false);
        etpassword = view.findViewById(R.id.etpassword);
        etconfirmpassword = view.findViewById(R.id.etconfirmpassword);
        btn = view.findViewById(R.id.btn_nxt1);
        et_email = view.findViewById(R.id.etemail);
        et_number=view.findViewById(R.id.etphoneno);
        // Initialize the Prefs class
        new Prefs.Builder()
                .setContext(getContext())
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName("Collegescout")
                .setUseDefaultSharedPreference(true)
                .build();

        etpassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (etpassword.getRight() - etpassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (etpassword.getTransformationMethod().getClass().getSimpleName().equals("PasswordTransformationMethod")) {
                            etpassword.setTransformationMethod(new SingleLineTransformationMethod());
                        } else {
                            etpassword.setTransformationMethod(new PasswordTransformationMethod());
                        }

                        etpassword.setSelection(etpassword.getText().length());
                        return true;
                    }
                }
                return false;
            }
        });
        etconfirmpassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (etconfirmpassword.getRight() - etconfirmpassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (etconfirmpassword.getTransformationMethod().getClass().getSimpleName().equals("PasswordTransformationMethod")) {
                            etconfirmpassword.setTransformationMethod(new SingleLineTransformationMethod());
                        } else {
                            etconfirmpassword.setTransformationMethod(new PasswordTransformationMethod());
                        }

                        etconfirmpassword.setSelection(etconfirmpassword.getText().length());
                        return true;
                    }
                }
                return false;
            }
        });

        login = view.findViewById(R.id.tv_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new LoginFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment).commit();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        return view;
    }

    private void signup() {
        final String email = et_email.getText().toString();
        String number = et_number.getText().toString();
        String password = etpassword.getText().toString();
        String cpassword = etconfirmpassword.getText().toString();

        if (email.isEmpty()) {
            et_email.setError("Email is required");
            et_email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            et_email.setError("Enter a valid email");
            et_email.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            etpassword.setError("Password is required");
            etpassword.requestFocus();
            return;
        }
        if (password.length() < 8) {
            etpassword.setError("Password must be atleast 8 characters long");
            etpassword.requestFocus();
            return;
        }
        if (!cpassword.equals(password)) {
            etconfirmpassword.setError("Passwords do not match");
            etconfirmpassword.requestFocus();
            return;
        }
        if (number.isEmpty()) {
            et_number.setError("Phone number is required");
            et_number.requestFocus();
            return;
        }
        if (number.length() < 10) {
            et_number.setError("Enter a valid number");
            et_number.requestFocus();
            return;
        } else {
            Signup signin = new Signup(email, password, number);
            Call<ResponseBody> call = Retroclient
                    .getInstance()
                    .getapi()
                    .createuser(signin);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Toasty.success(getContext(), "OTP sent to your email", Toast.LENGTH_LONG, true).show();
                        Prefs.putString("email", email);
                        Fragment fragment = new OtpFragment();
                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.container, fragment).commit();

                    } else {
                        Toasty.error(getContext(), "Email already exists", Toast.LENGTH_LONG, true).show();
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