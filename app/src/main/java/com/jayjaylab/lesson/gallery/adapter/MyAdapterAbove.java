package com.jayjaylab.lesson.gallery.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jayjaylab.lesson.gallery.R;

import java.util.ArrayList;

/**
 * Created by HOMIN on 2016-05-12.
 */
public class MyAdapterAbove extends RecyclerView.Adapter<MyAdapterAbove.ViewHolder> {
    private ArrayList<MyData> mDataset;

    public MyAdapterAbove(ArrayList<MyData> myDataset) {
        mDataset = myDataset;
    }


    @Override
    public MyAdapterAbove.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_view_above, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyAdapterAbove.ViewHolder holder, int position) {

        holder.mTextView.setText(mDataset.get(position).text);
        holder.mImageView.setImageResource(mDataset.get(position).img);

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView mImageView;
        public TextView mTextView;

        public ViewHolder(View view) {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.image1);
            mTextView = (TextView) view.findViewById(R.id.textview1);
        }
    }
}
