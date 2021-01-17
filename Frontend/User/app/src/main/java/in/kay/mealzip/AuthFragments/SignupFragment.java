package in.kay.mealzip.AuthFragments;

import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.pixplicity.easyprefs.library.Prefs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import in.kay.mealzip.Request.Signup;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import in.kay.mealzip.Interface.Retroclient;
import in.kay.mealzip.R;

public class SignupFragment extends Fragment {

    EditText et_name, et_email, et_password;
    Button btn_signup;
    TextView tvlogin, tv_college;
    SpinnerDialog spinnerDialog;
    ArrayList<String> college = new ArrayList<>();
    String selected_clg;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        et_name = view.findViewById(R.id.etname);
        et_email = view.findViewById(R.id.etemail);
        et_password = view.findViewById(R.id.etpassword);
        btn_signup = view.findViewById(R.id.btn_nxt1);
        tv_college = view.findViewById(R.id.tvcollege);

        et_password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (et_password.getRight() - et_password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (et_password.getTransformationMethod().getClass().getSimpleName().equals("PasswordTransformationMethod")) {
                            et_password.setTransformationMethod(new SingleLineTransformationMethod());
                        } else {
                            et_password.setTransformationMethod(new PasswordTransformationMethod());
                        }

                        et_password.setSelection(et_password.getText().length());
                        return true;
                    }
                }
                return false;
            }
        });
        //  et_cpass.setOnTouchListener(new View.OnTouchListener() {
        //      @Override
        //      public boolean onTouch(View v, MotionEvent event) {
        //          final int DRAWABLE_RIGHT = 2;
        //
        //          if (event.getAction() == MotionEvent.ACTION_UP) {
        //              if (event.getRawX() >= (et_cpass.getRight() - et_cpass.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
        //                  if (et_cpass.getTransformationMethod().getClass().getSimpleName().equals("PasswordTransformationMethod")) {
        //                      et_cpass.setTransformationMethod(new SingleLineTransformationMethod());
        //                  } else {
        //                      et_cpass.setTransformationMethod(new PasswordTransformationMethod());
        //                  }
        //
        //                  et_cpass.setSelection(et_cpass.getText().length());
        //                  return true;
        //              }
        //          }
        //          return false;
        //      }
        //  });

        tvlogin = view.findViewById(R.id.tv_login);
        tvlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new LoginFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment).commit();
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        tv_college.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_college();
                spinnerDialog = new SpinnerDialog(getActivity(), college, "Select college", "");// With No Animation
                //spinnerDialog=new SpinnerDialog(AdditemActivity.this,category,"Select category",R.style.DialogAnimations_SmileWindow,"Close Button Text");// With Animation

                spinnerDialog.setCancellable(false);
                spinnerDialog.setShowKeyboard(false);// for open keyboard by default
                // spinnerDialog.setItemColor(getResources().getColor(R.color.colorPrimary));
                spinnerDialog.showSpinerDialog();
                // category.clear();
                spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                    @Override
                    public void onClick(String item, int position) {
                        selected_clg = item;
                        college.clear();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_college.setText(selected_clg);
                                tv_college.setTextColor(getResources().getColor(R.color.black));
                            }
                        });
                    }
                });

            }
        });

        return view;
    }

    private void add_college() {
        Call<ResponseBody> call = Retroclient
                .getInstance()
                .getapi()
                .getcollege();

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful()) {
                        String str = response.body().string();
                        JSONArray jsonArray = new JSONArray(str);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String colleges = jsonObject.getString("name");
                            college.add(colleges);
                        }
                        Log.d("college",college.get(0));
                    }

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void signup() {
        final String email = et_email.getText().toString();
        String name = et_name.getText().toString();
        String password = et_password.getText().toString();
        String college = tv_college.getText().toString();

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
            et_password.setError("Password is required");
            et_password.requestFocus();
            return;
        }
        if (password.length() < 8) {
            et_password.setError("Password must be atleast 8 characters long");
            et_password.requestFocus();
            return;
        }
        /* if (!cpassword.equals(password)) {
            et_cpass.setError("Passwords do not match");
            et_cpass.requestFocus();
            return;
        }      */

        if (name.isEmpty()) {
            et_name.setError("Name is required");
            et_name.requestFocus();
            return;
        }
        if (college.isEmpty()) {
            tv_college.setError("Select college");
            tv_college.requestFocus();
            return;
        } else {
            Signup signin = new Signup(email, password, name);
            Call<ResponseBody> call = Retroclient
                    .getInstance()
                    .getapi()
                    .createuser(signin);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    if (response.isSuccessful()) {
                        Toasty.success(getContext(), "OTP sent to your email", Toast.LENGTH_LONG, true).show();
                        //Toast.makeText(getActivity(), "OTP sent to your email", Toast.LENGTH_LONG).show();
                        Prefs.putString("email", email);
                        Fragment fragment = new OtpFragment();
                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.container, fragment).commit();

                    } else {
                        Toasty.error(getContext(), "Email already exists", Toast.LENGTH_LONG, true).show();
                        //Toast.makeText(getActivity(), "Email already exists", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toasty.error(getContext(), t.getMessage(), Toast.LENGTH_LONG, true).show();
                    //Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        }
    }
}