package com.example.bootcampassistant.ui;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bootcampassistant.R;
import com.example.bootcampassistant.data.model.UserResponse;

import java.util.List;

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.ViewHolder> {
    private final Context context;
    private List<UserResponse> userResponsesList;
    private final OnItemClickListener listener;

    public MainListAdapter(Context context, List<UserResponse> userResponsesList, OnItemClickListener itemClickListener) {
        this.context = context;
        this.userResponsesList = userResponsesList;
        this.listener = itemClickListener;
    }

    public void updateData(List<UserResponse> newData) {
        userResponsesList = newData;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(UserResponse item);
    }

    @NonNull
    @Override
    public MainListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.user_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainListAdapter.ViewHolder holder, int position) {
        final UserResponse userData = userResponsesList.get(position);

        holder.bind(context, userData, listener);

        holder.txtName.setText(userData.getFirstName() + " " + userData.getLastName());
        holder.txtEmail.setText(userData.getEmail());

        Glide.with(context)
                .load(userData.getAvatar())
                .into(holder.imgUser);

    }

    @Override
    public int getItemCount() {
        return userResponsesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgUser;
        TextView txtName, txtEmail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgUser = itemView.findViewById(R.id.img_user);
            txtName = itemView.findViewById(R.id.txt_name);
            txtEmail = itemView.findViewById(R.id.txt_email);
        }

        public void bind(Context context, final UserResponse item , final OnItemClickListener listener) {
            txtName.setText(item.getFirstName() + " " + item.getLastName());
            txtEmail.setText(item.getEmail());

            Glide.with(context).load(item.getAvatar()).into(imgUser);

            itemView.setOnClickListener(v -> listener.onItemClick(item));

        }
    }


}
