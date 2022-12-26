package com.rbt.comunity.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.rbt.comunity.R;
import com.rbt.comunity.fragment.CameraFragment;
import com.rbt.comunity.fragment.ComunityFragment;
import com.rbt.comunity.fragment.GaleryFragment;
import com.rbt.comunity.fragment.PostFragment;
import com.rbt.comunity.fragment.UserFragment;

import java.util.ArrayList;
import java.util.List;

public class UploadFotoProfilActivity extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private TabLayout tabLayout;
    private ViewPagerAdapter adapter;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_foto_profil);

        viewPager2 = findViewById(R.id.vp_upload_pp);
        tabLayout = findViewById(R.id.tabLayout);


        initView();
    }

    private void initView() {
        FragmentManager fm = getSupportFragmentManager();
        adapter = new UploadFotoProfilActivity.ViewPagerAdapter(fm, getLifecycle());

        adapter.addFragment(new GaleryFragment(), "Galeri");
        adapter.addFragment(new CameraFragment(), "Foto");

        viewPager2.setAdapter(adapter);
        viewPager2.setOffscreenPageLimit(1);

        new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> {
                    tab.setText(adapter.fragmentTitleList.get(position));
                }).attach();

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TextView tv = (TextView) LayoutInflater.from(UploadFotoProfilActivity.this)
                    .inflate(R.layout.custom_tabs, null);
            tabLayout.getTabAt(i).setCustomView(tv);
        }
    }

    class ViewPagerAdapter extends FragmentStateAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }
        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getItemCount() {
            return fragmentList.size();
        }
    }

}