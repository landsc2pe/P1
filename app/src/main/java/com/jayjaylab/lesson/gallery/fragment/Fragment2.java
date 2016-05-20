package com.jayjaylab.lesson.gallery.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jayjaylab.lesson.gallery.OnClickListener;
import com.jayjaylab.lesson.gallery.R;
import com.jayjaylab.lesson.gallery.adapter.ImageAdapter;
import com.jayjaylab.lesson.gallery.adapter.MyAdapter;
import com.jayjaylab.lesson.gallery.adapter.MyAdapterAbove;
import com.jayjaylab.lesson.gallery.adapter.MyData;
import com.jayjaylab.lesson.gallery.model.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Homin on 2016-04-07.
 */
public class Fragment2 extends Fragment {
    final String TAG = Fragment2.class.getSimpleName();
    public static String KEY_IMAGE = "image";

    static String[] imageFiles;
    static Context mContext;

    static int mapSize;
    static HashMap<String, List<Image>> hashMap;
    static ArrayList<String> hashMapValues;
    static String[] folderName;
    static List<Image> folderPath;

    private RecyclerView recyclerViewAbove;
    private RecyclerView recyclerViewMenu;
    private RecyclerView recyclerViewGallery;
    private RecyclerView.Adapter adapterMenu, adapterAbove;
    private ArrayList<MyData> myDataset;
    private File mGalleryFolder;
    private String GALLERY_LOCATION = ".thumbnails";
    private ImageAdapter adapterImage;

//    public static Fragment2 newInstance(String[] imageFiles) {
//        Fragment2 fragment = new Fragment2();
//        Bundle args = new Bundle();
//        args.putStringArray(KEY_IMAGE, imageFiles);
//
//        fragment.setArguments(args);
//        return fragment;
//    }
//

    public static void sortImagePath(Map<String, List<Image>> map) {
        mapSize = map.size();
        hashMap = new HashMap<>();
        hashMap.putAll(map);
        hashMapValues = new ArrayList<>();
        folderName = new String[mapSize];

        int count = 0;
        Iterator<String> iter = map.keySet().iterator();

        while (iter.hasNext()) {
            String keys = iter.next();

            //add keys in the static ArrayList.
            hashMapValues.add(keys);

            //Making string array of folder name.
            String[] it = keys.split("/");
            int a = it.length-1;
            folderName[count] = it[a];


            Log.d("folderName", folderName[count]);
            Log.d("key", keys);
            Log.d("value", "" + map.get(keys));

            count++;
        }

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
//        Bundle args = getArguments();
//        if (args != null) {
//            String[] images = args.getStringArray(KEY_IMAGE);
//            Log.d(TAG, "images : " + images);
//        }

        mContext = getActivity();
        myDataset = new ArrayList<>();

        //"Above" icon button creator using folder name array.
        for (int i = 0; i < mapSize; i++) {
            myDataset.add(new MyData(folderName[i], R.mipmap.ic_launcher));
        }

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

        adapterAbove = new MyAdapterAbove(myDataset);
        adapterMenu = new MyAdapter(myDataset);

//        adapterImage = new ImageAdapter(this, MainActivity.uris);
//        adapterImage = new ImageAdapter(this, imageFiles);

        recyclerViewAbove.setAdapter(adapterAbove);
        recyclerViewMenu.setAdapter(adapterMenu);

        //"Above" icon touch listener.
        recyclerViewAbove.addOnItemTouchListener(new OnClickListener(getActivity(), recyclerViewAbove, new OnClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                folderPath = new ArrayList<>();
                folderPath = hashMap.get(hashMapValues.get(position));

                imageFiles = new String[folderPath.size()];

                for (int i = 0; i < folderPath.size(); i++) {
                    imageFiles[i] = folderPath.get(i).getThumbnail().getPath();

                    Log.d("Path ", imageFiles[i]);
                }

                adapterImage = new ImageAdapter(Fragment2.this, imageFiles);
                recyclerViewGallery.setAdapter(adapterImage);

            }

            @Override
            public void onItemLongClick(View view, int position) {



            }
        }));
    }


    //폴더생성
    private void createImageGallery() {
        File storageDirectory = Environment.getExternalStoragePublicDirectory("/DCIM");
        mGalleryFolder = new File(storageDirectory, GALLERY_LOCATION);
        if (!mGalleryFolder.exists()) {
            mGalleryFolder.mkdirs();
        }
    }

//    폴더안의 모든 파일 구하기[폴더 포함]
//    public String[] getFileList(String string) {
//
//        File fileRoot = new File(string);
//        if (fileRoot.isDirectory() == false) {
//            return null;
//        }
//        String[] fileList = fileRoot.list();
//        for(int i=0 ; i<fileList.length ; i++) {
//            Log.d("File ", fileList[i]);
//        }
//        return fileList;
//    }
}
