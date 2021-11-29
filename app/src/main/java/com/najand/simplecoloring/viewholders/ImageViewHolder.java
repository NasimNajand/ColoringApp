package com.najand.simplecoloring.viewholders;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.najand.simplecoloring.R;
import com.najand.simplecoloring.listeners.OnImageSelected;


public class ImageViewHolder extends RecyclerView.ViewHolder {

    public ImageView imageView;

    public void setListener(OnImageSelected listener) {
        this.listener = listener;
    }

    private OnImageSelected listener;
    public ImageViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.image_outline);
        itemView.setOnClickListener(view -> listener.onClick(getAdapterPosition()));
    }
}
