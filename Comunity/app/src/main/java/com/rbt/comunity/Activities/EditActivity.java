package com.rbt.comunity.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rbt.comunity.R;
import com.rbt.comunity.adapters.UploadProfilAdapter;
import com.rbt.comunity.fragment.BackgroundFragment;
import com.rbt.comunity.fragment.ComunityFragment;
import com.rbt.comunity.fragment.PostFragment;
import com.rbt.comunity.fragment.ProfilFragment;
import com.rbt.comunity.fragment.UserFragment;
import com.rbt.comunity.model.ReadWriteUserDetails;

import java.util.ArrayList;
import java.util.List;

public class EditActivity extends AppCompatActivity {

    private ImageView iv_close, iv_check, profil, background;
    private EditText userName, initial, bio;
    private TextView edit;
    private String username;
    private ConstraintLayout tautan;
    private CardView cardProfil;
    private BottomSheetDialog bottomSheetDialog;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private ViewPagerAdapter adapter;


    private FirebaseAuth authProfil;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil);

        iv_close = findViewById(R.id.btn_close);
        iv_check = findViewById(R.id.btn_check);
        userName = findViewById(R.id.et_user_edit);
        initial = findViewById(R.id.et_init_edit);
        bio = findViewById(R.id.et_bio_edit);

        cardProfil = findViewById(R.id.cv_prof);
        profil = findViewById(R.id.iv_edit_profil);
        background = findViewById(R.id.iv_background_edit);
        edit = findViewById(R.id.tv_edit);
        tautan = findViewById(R.id.cl_tautan);

        authProfil = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfil.getCurrentUser();

        if (firebaseUser == null) {
            Toast.makeText(EditActivity.this, "Something went wrong! User's details are not available at the moment", Toast.LENGTH_SHORT).show();
        } else {
            showUserProfile(firebaseUser);
        }

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditActivity.this, MainActivity.class));
            }
        });

        iv_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditActivity.this, MainActivity.class));
            }
        });

        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog = new BottomSheetDialog(EditActivity.this, R.style.AppBottomSheetDialogTheme);
                bottomSheetDialog.setContentView(R.layout.sheet_upload_image_profil_and_background);
                bottomSheetDialog.show();

                tabLayout = bottomSheetDialog.findViewById(R.id.tab_upload);
                viewPager2 = bottomSheetDialog.findViewById(R.id.vp_upload);

                initView();

            }
        });

        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        tautan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditActivity.this, TautanActivity.class));
            }
        });
    }

    private void initView() {
        FragmentManager fm = getSupportFragmentManager();
        adapter = new EditActivity.ViewPagerAdapter(fm, getLifecycle());
        adapter.addFragment(new ProfilFragment(), "Profil");
        adapter.addFragment(new BackgroundFragment(), "Background");

        viewPager2.setAdapter(adapter);
        viewPager2.setOffscreenPageLimit(1);
        new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> {
                    tab.setText(adapter.fragmentTitleList.get(position));
                }).attach();

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TextView tv = (TextView) LayoutInflater.from(EditActivity.this)
                    .inflate(R.layout.custom_tabs, null);
            tabLayout.getTabAt(i).setCustomView(tv);
        }

    }

    private void showUserProfile(FirebaseUser firebaseUser) {
        String userId = firebaseUser.getUid();

        // Extracting User Reference from Database for "Registered Users"
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Sign-up Users");
        referenceProfile.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserDetails readUserDetails = snapshot.getValue(ReadWriteUserDetails.class);
                if (readUserDetails != null) {
                    username = firebaseUser.getDisplayName();
                    userName.setText(username);
                    initial.setText(username);
                } else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditActivity.this, "Somethine went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    class ViewPagerAdapter extends FragmentStateAdapter{
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