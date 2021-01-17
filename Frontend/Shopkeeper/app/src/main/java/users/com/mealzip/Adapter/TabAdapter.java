package users.com.mealzip.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import users.com.mealzip.TabFragments.CompletedFragment;
import users.com.mealzip.TabFragments.PendingFragment;

public class TabAdapter  extends FragmentStatePagerAdapter {
    int tabcount;

    public TabAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabcount = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new PendingFragment();
            case 1:
                return new CompletedFragment();
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return tabcount;
    }
}