package com.rbt.comunity.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.rbt.comunity.R;
import com.rbt.comunity.adapters.DasbordAdapter;

public class DasbordActivity extends AppCompatActivity {

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dasbord);

        viewPager = findViewById(R.id.vp_card);

        DasbordAdapter dasbordAdapter = new DasbordAdapter(getSupportFragmentManager());
        viewPager.setAdapter(dasbordAdapter);
        viewPager.setPadding(50, 0,50, 0);

    }
}