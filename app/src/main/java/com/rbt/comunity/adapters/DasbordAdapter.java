package com.rbt.comunity.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.rbt.comunity.fragment.LoginFragment;
import com.rbt.comunity.fragment.SignupFragment;
import com.rbt.comunity.fragment.WelcomeFragment;

public class DasbordAdapter extends FragmentPagerAdapter {

    public DasbordAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new WelcomeFragment();
            case 1:
                return new LoginFragment();
            case 2:
                return new SignupFragment();

        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
