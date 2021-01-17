package in.kay.mealzip.NavFragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.pixplicity.easyprefs.library.Prefs;

import in.kay.mealzip.IntroActivity;
import in.kay.mealzip.R;

public class ProfileFragment extends Fragment {

    ImageView edit;
    TextView tv_logout;
    Dialog dialog;
    Context context;
    TextView tv_name,phoneno;
    String name,no;
    Dialog dialog1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        edit=view.findViewById(R.id.iv_edit);
        tv_logout=view.findViewById(R.id.logout);
        tv_name=view.findViewById(R.id.tvname);
        phoneno=view.findViewById(R.id.tv_no);
        dialog= new Dialog(context);
        dialog1= new Dialog(context);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
               showdialog();
            }
        });

        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.show();
                logout();
            }
        });
        return view;
    }

    private void logout() {
        dialog1.setContentView(R.layout.logout_dialog);
        dialog1.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.bg2));
        dialog1.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog1.setCancelable(false);
        TextView cancel,done;
        cancel=dialog1.findViewById(R.id.tv_cancel);
        done= dialog1.findViewById(R.id.tv_logout);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Prefs.putBoolean("registered",false);
                startActivity(new Intent(getActivity(), IntroActivity.class));
            }
        });
    }

    private void showdialog() {
        dialog.setContentView(R.layout.edit_dialog);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.bg2));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        Button edit;
        ImageView back;
        back=dialog.findViewById(R.id.imageView5);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             dialog.dismiss();
            }
        });
        final EditText name1,no1;
        edit=dialog.findViewById(R.id.btn_edit);
        name1=dialog.findViewById(R.id.etname);
        no1=dialog.findViewById(R.id.et_no);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=name1.getText().toString();
                no= no1.getText().toString();
                if(!name.isEmpty())
                tv_name.setText(name);
                if(!no.isEmpty())
                phoneno.setText(no);
                dialog.dismiss();
            }
        });

    }
}