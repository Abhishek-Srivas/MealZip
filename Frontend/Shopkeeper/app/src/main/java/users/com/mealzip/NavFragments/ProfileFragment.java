package users.com.mealzip.NavFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pixplicity.easyprefs.library.Prefs;

import users.com.mealzip.IntroUI.IntroActivity;
import users.com.mealzip.R;

public class ProfileFragment extends Fragment {
    TextView logout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        logout=view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Prefs.putBoolean("registered",false);
                startActivity(new Intent(getActivity(), IntroActivity.class));
            }
        });
        return view;
    }
}