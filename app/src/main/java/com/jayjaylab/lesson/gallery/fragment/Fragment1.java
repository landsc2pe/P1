package com.jayjaylab.lesson.gallery.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jayjaylab.lesson.gallery.R;

/**
 * Created by Homin on 2016-04-07.
 */
public class Fragment1 extends Fragment {
    public static final String TAG = Fragment1.class.getSimpleName();
    private ImageView ImageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.folder_layout, container, false);
        ImageView = (ImageView) rootView.findViewById(R.id.image1);

        return rootView;


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Glide.with(this).load(R.drawable.icon_music).into(ImageView);
    }

}
