package com.rbt.comunity.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rbt.comunity.R;
import com.rbt.comunity.model.ModelPost;

import java.util.ArrayList;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.GridViewHolder> {

    private ArrayList<ModelPost> dataPost;

    public GridAdapter(ArrayList<ModelPost> dataPost) {
        this.dataPost = dataPost;
    }

    public interface OnItemClickCallBack {
        void onItemClicked(ModelPost data);
    }

    private OnItemClickCallBack callBack;

    public GridAdapter(OnItemClickCallBack callBack) {
        this.callBack = callBack;
    }

    @NonNull
    @Override
    public GridAdapter.GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_grid_item, parent, false);
        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GridAdapter.GridViewHolder holder, int position) {

        ModelPost post = dataPost.get(position);
        holder.tvCom.setText(post.getCom());
        holder.ivCom.setImageResource(post.getImg());
    }

    @Override
    public int getItemCount() {
        return dataPost.size();
    }

    public class GridViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCom;
        TextView tvCom;
        public GridViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCom = itemView.findViewById(R.id.iv_post);
            tvCom = itemView.findViewById(R.id.userCom);
        }
    }
}
