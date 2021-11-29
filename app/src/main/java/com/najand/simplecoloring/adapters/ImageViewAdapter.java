package com.najand.simplecoloring.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.najand.simplecoloring.PaintActivity;
import com.najand.simplecoloring.R;
import com.najand.simplecoloring.commons.Common;
import com.najand.simplecoloring.viewholders.ImageViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ImageViewAdapter extends RecyclerView.Adapter<ImageViewHolder> {

    private Context context;
    private List<Integer> list;
    private static final String TAG = ImageViewAdapter.class.getSimpleName();

    public ImageViewAdapter(Context c) {
        Log.i(TAG, "ImageViewAdapter: created.");
        list = getImages(c);

    }

    private List<Integer> getImages(Context context) {
        this.context = context;
        list = new ArrayList<>();
//        list.add(R.drawable.outline_baloon);
        list.add(R.drawable.outline_elephant);
        list.add(R.drawable.outline_dolphine);
        list.add(R.drawable.outline_flower_1);
        list.add(R.drawable.outline_hat);
        list.add(R.drawable.outline_shell);
        return list;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder: " + list.get(position));
        holder.imageView.setImageResource(list.get(position));
        holder.setListener(pos -> {
            Common.PICTURE_SELECTED = list.get(pos);
            holder.imageView.getContext().startActivity(new Intent(context, PaintActivity.class));
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
