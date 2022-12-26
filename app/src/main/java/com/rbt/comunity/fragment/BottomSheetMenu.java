package com.rbt.comunity.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.rbt.comunity.Activities.DasbordActivity;
import com.rbt.comunity.Activities.MainActivity;
import com.rbt.comunity.R;


public class BottomSheetMenu extends BottomSheetDialogFragment {

    private LinearLayout logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_sheet_menu, container, false);

        logout = view.findViewById(R.id.ll_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DasbordActivity.class);
                Toast.makeText(getContext(), "Logout", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
        return view;
    }
}