package com.rbt.comunity.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.rbt.comunity.Activities.DasbordActivity;
import com.rbt.comunity.Activities.EditActivity;
import com.rbt.comunity.R;
import com.rbt.comunity.model.ReadWriteUserDetails;

import java.util.ArrayList;
import java.util.List;

public class UserFragment extends Fragment {
    private ViewPager2 viewPager2;
    private TabLayout tabLayout;
    private ViewPagerAdapter adapter;

    private CardView ivProf;

    private ImageView ivMenu, ivCreate, btnClose, btnCheck, ivBackground, btnBackTautan;
    private BottomSheetDialog sheetDialog;
    private LinearLayout logout, llEdit, llCheck, llTautan, llTautanAdd;
    private ConstraintLayout clTautan;
    private Button editProfil;

    private TextView username, bio, initial;
    private ImageView pProfil, bProfil, email, facebook, number;
    private String userName, st_email;
    private FirebaseAuth authProfile;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        viewPager2 = view.findViewById(R.id.vp_user);
        tabLayout = view.findViewById(R.id.tb_user);
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        ivCreate = view.findViewById(R.id.iv_create);
        ivMenu = view.findViewById(R.id.iv_menu);
        editProfil = view.findViewById(R.id.btn_edit);

        username = view.findViewById(R.id.tv_username);
        initial = view.findViewById(R.id.tv_initial);
        bio = view.findViewById(R.id.tv_bio);

        pProfil = view.findViewById(R.id.iv_pProfil);
        bProfil = view.findViewById(R.id.iv_background_profil);
        email = view.findViewById(R.id.iv_email);
        facebook = view.findViewById(R.id.iv_facebook);
        number = view.findViewById(R.id.iv_number);

        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();

        if (firebaseUser == null) {
            Toast.makeText(getContext(), "Something went wrong! User's details are not available at the moment", Toast.LENGTH_SHORT).show();
        } else {
            showUserProfile(firebaseUser);
        }

        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetDialog = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
                sheetDialog.setContentView(R.layout.fragment_bottom_sheet_menu);
                sheetDialog.show();

                logout = sheetDialog.findViewById(R.id.ll_logout);
                logout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        authProfile.signOut();
                        Intent intent = new Intent(getActivity(), DasbordActivity.class);
                        Toast.makeText(getContext(), "Logout", Toast.LENGTH_SHORT).show();

                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
            }
        });
        ivCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetDialog = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
                sheetDialog.setContentView(R.layout.fragment_bottom_sheet_create);
                sheetDialog.show();
            }
        });

        editProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), EditActivity.class));
            }
        });


        initView();
        return view;
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
                    userName = firebaseUser.getDisplayName();
                    st_email = firebaseUser.getEmail();
                    username.setText(userName);
                    initial.setText(userName);
                    email.setVisibility(View.VISIBLE);
                } else {
                    checkEmailVerified(firebaseUser);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Somethine went wrong!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void checkEmailVerified(FirebaseUser firebaseUser) {
        if (!firebaseUser.isEmailVerified()) {
            showAlertDialog();
        }
    }

    private void showAlertDialog() {
        // Setup allert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Email Not Verified");
        builder.setMessage("Please verify your email now. You can not login without email verification next time.");

        // Open Email Apps if User clicks/tabs continue button
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // To email app in new window and not within our app
                startActivity(intent);
            }
        });

        // Create Alert Dialog Box
        AlertDialog alertDialog = builder.create();

        // Show Alert Dialog
        alertDialog.show();
    }

    private void initView() {
        FragmentManager fm = getChildFragmentManager();
        adapter = new ViewPagerAdapter(fm, getLifecycle());

        adapter.addFragment(new PostFragment(), "Post");
        adapter.addFragment(new ComunityFragment(), "Comunity");

        viewPager2.setAdapter(adapter);
        viewPager2.setOffscreenPageLimit(1);

        new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> {
                    tab.setText(adapter.fragmentTitleList.get(position));
                }).attach();

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TextView tv = (TextView) LayoutInflater.from(getContext())
                    .inflate(R.layout.custom_tabs, null);
            tabLayout.getTabAt(i).setCustomView(tv);
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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