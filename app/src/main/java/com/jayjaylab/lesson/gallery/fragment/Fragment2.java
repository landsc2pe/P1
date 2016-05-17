package com.jayjaylab.lesson.gallery.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.KeyEventCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jayjaylab.lesson.gallery.MyData;
import com.jayjaylab.lesson.gallery.R;
import com.jayjaylab.lesson.gallery.activity.MainActivity;
import com.jayjaylab.lesson.gallery.adapter.ImageAdapter;
import com.jayjaylab.lesson.gallery.adapter.MyAdapter;
import com.jayjaylab.lesson.gallery.adapter.MyAdapter_Above;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Homin on 2016-04-07.
 */
public class Fragment2 extends Fragment {
    final String TAG = Fragment2.class.getSimpleName();
    public static String KEY_IMAGE = "image";

    static Uri[] imageFiles;
    static Context mContext;

    private RecyclerView recyclerViewAbove;
    private RecyclerView recyclerViewMenu;
    private RecyclerView recyclerViewGallery;
    private RecyclerView.Adapter adapterMenu, adapterGallery, adapterAbove;
    private ArrayList<MyData> myDataset;
    private File mGalleryFolder;
    private String GALLERY_LOCATION = ".thumbnails";

    public static Fragment2 newInstance(String[] imageFiles) {
        Bundle args = new Bundle();
        args.putStringArray(KEY_IMAGE, imageFiles);

        Fragment2 fragment = new Fragment2();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        createImageGallery();

        View rootView = inflater.inflate(R.layout.manage_layout, container, false);
        recyclerViewAbove = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        recyclerViewMenu = (RecyclerView) rootView.findViewById(R.id.my_recycler_view1);
        recyclerViewGallery = (RecyclerView) rootView.findViewById(R.id.image_layout);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "bundle : " + savedInstanceState);
        Bundle args = getArguments();
        if(args != null) {
            String[] images = args.getStringArray(KEY_IMAGE);
            Log.d(TAG, "images : " + images);
        }

        myDataset = new ArrayList<>();

        myDataset.add(new MyData("icon1", R.mipmap.ic_launcher));
        myDataset.add(new MyData("icon2", R.mipmap.ic_launcher));
        myDataset.add(new MyData("icon3", R.mipmap.ic_launcher));
        myDataset.add(new MyData("icon4", R.mipmap.ic_launcher));
        myDataset.add(new MyData("icon5", R.mipmap.ic_launcher));
        myDataset.add(new MyData("icon6", R.mipmap.ic_launcher));
        myDataset.add(new MyData("icon7", R.mipmap.ic_launcher));
        myDataset.add(new MyData("icon8", R.mipmap.ic_launcher));
        myDataset.add(new MyData("icon9", R.mipmap.ic_launcher));

        mContext = getActivity();

//        imageFiles = mGalleryFolder.listFiles();
//        imageFiles = getThumbnails();

        recyclerViewAbove.setHasFixedSize(true);
        recyclerViewMenu.setHasFixedSize(true);
        recyclerViewGallery.setHasFixedSize(true);


        LinearLayoutManager linearLayoutManagerAbove = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4);

        recyclerViewAbove.setLayoutManager(linearLayoutManagerAbove);
        recyclerViewMenu.setLayoutManager(linearLayoutManager);
        recyclerViewGallery.setLayoutManager(gridLayoutManager);
        recyclerViewGallery.setItemViewCacheSize(40);

        adapterAbove = new MyAdapter_Above(myDataset);
        adapterMenu = new MyAdapter(myDataset);
//        adapterGallery = new ImageAdapter(this, MainActivity.uris);
//        adapterGallery = new ImageAdapter(this, imageFiles);

        recyclerViewAbove.setAdapter(adapterAbove);
        recyclerViewMenu.setAdapter(adapterMenu);
        recyclerViewGallery.setAdapter(adapterGallery);


    }

        Uri[] getThumbnails() {
        String[] projection = {MediaStore.Images.Thumbnails._ID,
                MediaStore.Images.Thumbnails.IMAGE_ID};
        Cursor cur = getActivity().getContentResolver().query(
                MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
                projection,
                null, null, null);
        if (cur.getCount() == 0) {
            return null;
        }
        cur.moveToFirst();

        Uri[] urls = new Uri[cur.getCount()];
        int id;
        int count = 0;
        while (cur.moveToNext()) {
            id = cur.getInt(0);
            Uri uri = Uri.parse(
                    MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI + "/" + id);
            urls[count++] = uri;
        }
        cur.close();

        return urls;
    }

//    // TODO: 2016. 5. 10. 비동기록 동작해야 함
//    Uri[] getThumbnails() {
//        String[] projection = {MediaStore.Images.Thumbnails._ID,
//                MediaStore.Images.Thumbnails.IMAGE_ID};
//        Cursor cur = getActivity().getContentResolver().query(
//                MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
//                projection,
//                null, null, null);
//        if (cur.getCount() == 0) {
//            return null;
//        }
//        cur.moveToFirst();
//
//        Uri[] urls = new Uri[cur.getCount()];
//        int id;
//        int count = 0;
//        while (cur.moveToNext()) {
//            id = cur.getInt(0);
//            Uri uri = Uri.parse(
//                    MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI + "/" + id);
//            urls[count++] = uri;
//        }
//        cur.close();
//
//        return urls;
//    }

    //폴더생성
    private void createImageGallery() {
        File storageDirectory = Environment.getExternalStoragePublicDirectory("/DCIM");
        mGalleryFolder = new File(storageDirectory, GALLERY_LOCATION);
        if (!mGalleryFolder.exists()) {
            mGalleryFolder.mkdirs();
        }
    }
}
