package com.rbt.comunity.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.rbt.comunity.Activities.UploadFotoProfilActivity;
import com.rbt.comunity.R;


public class ProfilFragment extends Fragment {

    private LinearLayout profil, delete;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_profil, container, false);

        profil = root.findViewById(R.id.ll_upload_profil);
        delete = root.findViewById(R.id.delete_profil);
        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Profil", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), UploadFotoProfilActivity.class));
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return root;
    }
}