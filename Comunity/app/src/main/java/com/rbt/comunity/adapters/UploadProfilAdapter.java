package com.rbt.comunity.adapters;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.rbt.comunity.fragment.BackgroundFragment;
import com.rbt.comunity.fragment.ProfilFragment;

public class UploadProfilAdapter extends FragmentPagerAdapter {

    int TotalTabs;
    public UploadProfilAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.TotalTabs = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0 :
                return new ProfilFragment();
            case 1 :
                return new BackgroundFragment();

        }
        return null;
    }

    @Override
    public int getCount() {
        return TotalTabs;
    }
}
