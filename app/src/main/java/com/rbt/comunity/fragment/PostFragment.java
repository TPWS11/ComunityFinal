package com.rbt.comunity.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.rbt.comunity.R;
import com.rbt.comunity.adapters.GridAdapter;
import com.rbt.comunity.model.DataCom;
import com.rbt.comunity.model.ModelPost;

import java.util.ArrayList;

public class PostFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<ModelPost> posts = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        recyclerView = view.findViewById(R.id.rv_post);
        posts.addAll(DataCom.getData());
        displayGrid();

        return view;
    }

    private void displayGrid() {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        GridAdapter adapter = new GridAdapter(posts);
        recyclerView.setAdapter(adapter);

    }
}