package com.jayjaylab.lesson.gallery.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.jayjaylab.lesson.gallery.OnLoadListener;
import com.jayjaylab.lesson.gallery.R;
import com.jayjaylab.lesson.gallery.fragment.Fragment1;
import com.jayjaylab.lesson.gallery.fragment.Fragment2;
import com.jayjaylab.lesson.gallery.model.Image;
import com.jayjaylab.lesson.gallery.model.Thumbnail;

import java.io.File;
<<<<<<< HEAD
import java.util.HashSet;
import java.util.Set;
=======
import java.util.*;
>>>>>>> origin/master

public class MainActivity extends AppCompatActivity implements OnLoadListener {
    final String TAG = MainActivity.class.getSimpleName();
    final int LOADER_ID_THUMBNAIL = 0;
    final int LOADER_ID_IMAGE = 1;

    // new ones.
    SparseArray<Image> arrayImage;
    Thumbnail[] arrayThumbnails;

    private static int msn = 1;
    private final int MY_PERMISSION_REQUEST_STORAGE = 100;

    FragmentManager fragmentManager;
    Fragment1 fragment_first;
    Fragment2 fragment_second;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "externalCacheDir : " + getExternalCacheDir()
                + ", internalCacheDir : " + getCacheDir());


        checkPermission();

        fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragment_first = new Fragment1();
        fragment_second = Fragment2.newInstance(null);
        fragmentTransaction.replace(R.id.main_layout, fragment_first);
        fragmentTransaction.commit();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (msn == 1) {
                    fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.main_layout, fragment_second);
                    fragmentTransaction.commit();

                    msn++;

                } else if (msn == 2) {
                    fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.main_layout, fragment_first);
                    fragmentTransaction.commit();

                    msn--;
                }
            }


        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private LoaderManager.LoaderCallbacks<Cursor> loaderCallbacksForOriginalImages = new LoaderManager
            .LoaderCallbacks<Cursor>() {

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            String[] projection = {MediaStore.Images.Media._ID,
                    MediaStore.Images.Media.DATA};

            CursorLoader cursorLoader = new CursorLoader(getApplicationContext(),
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection, null, null, null);

            return cursorLoader;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            Log.d(TAG, "count : " + data.getCount());

            int id;
            int count = 0;
            String path;
            SparseArray<Image> sparseArrayImage = new SparseArray<>(data.getCount());

            data.moveToFirst();
            while (data.moveToNext()) {
                // making image path to array
                id = data.getInt(data.getColumnIndex(MediaStore.Images.Media._ID));
                path = data.getString( data.getColumnIndex(MediaStore.Images.Media.DATA));
                sparseArrayImage.append(id, new Image(id, path));
                count++;
            }
            data.close();

            if(sparseArrayImage != null && sparseArrayImage.size() > 0) {
                onLoadOriginalImages(sparseArrayImage);
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    };


    private LoaderManager.LoaderCallbacks<Cursor> loaderCallbacksForThumbnails = new LoaderManager.LoaderCallbacks<Cursor>() {

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            Log.d(TAG, "id : " + id);

            String[] projection = {MediaStore.Images.Thumbnails._ID,
                MediaStore.Images.Thumbnails.DATA,
                MediaStore.Images.Thumbnails.IMAGE_ID};

            CursorLoader cursorLoader = new CursorLoader(getApplicationContext(),
                    MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
                    projection, null, null, null);

            return cursorLoader;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            Log.d(TAG, "count : " + data.getCount());
            
            Thumbnail[] thumbnails = new Thumbnail[data.getCount()];
            String path;
            int id, imageId;
            int count = 0;

            data.moveToFirst();
            while (data.moveToNext()) {
                //making image path to array
                path = data.getString(data.getColumnIndex(MediaStore.Images.Thumbnails.DATA) );
                id = data.getInt(data.getColumnIndex(MediaStore.Images.Thumbnails._ID));
                imageId = data.getInt(data.getColumnIndex(MediaStore.Images.Thumbnails
                        .IMAGE_ID));
<<<<<<< HEAD
                Log.d(TAG, "id : " + id + ", imageId : " + imageId +", path : "+ path);

                // TODO: 2016. 5. 14. get original path from thumbnail id or imageid.
                

                imagePath[count] = path;
                Uri uri = Uri.parse(
                        MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI + "/" + id);


                uris[count] = uri;
=======
//                Log.d(TAG, "id : " + id + ", imageId : " + imageId + ", path : " + path);
                thumbnails[count] = new Thumbnail(id, imageId, path);
>>>>>>> origin/master
                count++;
            }
            data.close();

            if(thumbnails != null && thumbnails.length > 0) {
                onLoadThumbnails(thumbnails);
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    };

    @TargetApi(Build.VERSION_CODES.M)

    private void checkPermission() {
        Log.i(TAG, "CheckPermission : " + ActivityCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Explain to the user why we need to write the permission.
                Toast.makeText(this, "Read/Write external storage", Toast.LENGTH_SHORT).show();
            }

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSION_REQUEST_STORAGE);

            // MY_PERMISSION_REQUEST_STORAGE is an
            // app-defined int constant

        } else {
            Log.e(TAG, "permission deny");

            getImageUriInBackground();
        }
    }

    void getImageUriInBackground() {
<<<<<<< HEAD
        getLoaderManager().initLoader(0, null, loaderCallbacks1);
=======
        getLoaderManager().initLoader(LOADER_ID_THUMBNAIL, null, loaderCallbacksForThumbnails);
        getLoaderManager().initLoader(LOADER_ID_IMAGE, null, loaderCallbacksForOriginalImages);
>>>>>>> origin/master
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    getImageUriInBackground();

                    // permission was granted, yay! do the
                    // calendar task you need to do.

                } else {

                    Log.d(TAG, "Permission always deny");

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                break;
        }
    }

    @Override
    public void onLoadThumbnails(Thumbnail[] thumbnails) {
        Log.d(TAG, "arrayThumbnails : " + thumbnails + ", # : " + thumbnails.length);
        this.arrayThumbnails = thumbnails;
        createMapOfDirectoryImages1(arrayImage, arrayThumbnails);
    }

    @Override
    public void onLoadOriginalImages(SparseArray<Image> sparseArray) {
        Log.d(TAG, "images : " + sparseArray + ", # : " + sparseArray.size());
        this.arrayImage = sparseArray;
        createMapOfDirectoryImages1(arrayImage, arrayThumbnails);
    }

    synchronized Map<String, List<Image>> createMapOfDirectoryImages1(
            SparseArray<Image> arrayImage, Thumbnail[] thumbnails) {
        if (arrayImage == null || thumbnails == null)
            return null;

        Map<String, List<Image>> map = new HashMap<>();

        for(Thumbnail thumbnail : thumbnails) {
            Log.d(TAG, "thumbnail : " + thumbnail);
            // FIXME: 2016. 5. 17. why is thumbnail null????
            if(thumbnail != null) {
                Image originalImage = arrayImage.get(thumbnail.getImageId());
                if(originalImage != null) {
                    Log.d(TAG, "originalImage : " + originalImage);
                    originalImage.setThumbnail(thumbnail);
                    String parent = new File(originalImage.getPath()).getParent();
                    if (map.containsKey(parent)) {
                        List<Image> list = map.get(parent);
                        list.add(originalImage);
                    } else {
                        List<Image> list = new ArrayList<>(10);
                        list.add(originalImage);
                        map.put(parent, list);
                    }
                }
            }
        }

        Set<Map.Entry<String, List<Image>>> entrySet = map.entrySet();
        for(Map.Entry entry : entrySet) {
            Log.d(TAG, "key : " + entry.getKey() + ", values : " + entry.getValue());
        }


        return map;
    }
}
