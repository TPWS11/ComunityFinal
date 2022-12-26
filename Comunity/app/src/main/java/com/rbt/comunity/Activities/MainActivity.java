package com.rbt.comunity.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavGraph;
import androidx.navigation.NavOptions;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.rbt.comunity.R;
import com.rbt.comunity.fragment.HomeFragment;
import com.rbt.comunity.fragment.SearchFragment;
import com.rbt.comunity.fragment.UserFragment;

public class MainActivity extends AppCompatActivity {

    private MeowBottomNavigation bnv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bnv = findViewById(R.id.bnv_main);

        bnv.add(new MeowBottomNavigation.Model(1, R.drawable.search));
        bnv.add(new MeowBottomNavigation.Model(2, R.drawable.home));
        bnv.add(new MeowBottomNavigation.Model(3, R.drawable.user));

        bnv.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment fragment = null;
                switch (item.getId()) {
                    case 1:
                        fragment = new SearchFragment();
                        break;
                    case 2:
                        fragment = new HomeFragment();
                        break;
                    case 3:
                        fragment = new UserFragment();
                        break;
                }

                loadFragment(fragment);
            }
        });
        bnv.show(2, true);
        bnv.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {

            }
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit()
        ;
    }


}