package com.jayjaylab.lesson.gallery.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.jayjaylab.lesson.gallery.R;
import com.jayjaylab.lesson.gallery.fragment.Fragment1;
import com.jayjaylab.lesson.gallery.fragment.Fragment2;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    public static Uri[] uris;
    public static String[] imagePath;
    final String TAG = MainActivity.class.getSimpleName();

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
        fragment_second = new Fragment2();
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

    private LoaderManager.LoaderCallbacks<Cursor> loaderCallbacks1 = new LoaderManager
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
            data.moveToFirst();

            uris = new Uri[data.getCount()];
            imagePath = new String[data.getCount()];

            int id;
            int count = 0;
//            Map<String, Integer> mapDirectory = new HashMap<>();
            Set<String> setDirectory = new HashSet<>();


            while (data.moveToNext()) {
                //making image path to array
                id = data.getInt(data.getColumnIndex(MediaStore.Images.Media._ID));
                String path = data.getString( data.getColumnIndex(MediaStore.Images.Media.DATA) );
                File file = new File(path);
                Log.d(TAG, "id : " + id + ", path : " + path + ", parent : "+ file
                        .getParent());
//                mapDirectory.put(file.getParent(), mapDirectory.get(fil))
                setDirectory.add(file.getParent());

                imagePath[count] = path;

                id = data.getInt(0);
                Uri uri = Uri.parse(
                        MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI + "/" + id);
                uris[count] = uri;
                count++;
            }
            data.close();

            Log.d(TAG, "!!!!!!!!");
            Log.d(TAG, "# : " + setDirectory.size());
            for(String path :setDirectory) {
                Log.d(TAG, "path : " + path);
            }
            Log.d(TAG, "!!!!!!!!");
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    };


    private LoaderManager.LoaderCallbacks<Cursor> loaderCallbacks = new LoaderManager.LoaderCallbacks<Cursor>() {

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
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
            data.moveToFirst();

            uris = new Uri[data.getCount()];
            imagePath = new String[data.getCount()];

            Set<String> setDirectory = new HashSet<>();
            String path;
            int id, imageId;
            int count = 0;
            while (data.moveToNext()) {
                //making image path to array
                path = data.getString(data.getColumnIndex(MediaStore.Images.Thumbnails.DATA) );
                id = data.getInt(data.getColumnIndex(MediaStore.Images.Thumbnails._ID));
                imageId = data.getInt(data.getColumnIndex(MediaStore.Images.Thumbnails
                        .IMAGE_ID));
                Log.d(TAG, "id : " + id + ", imageId : " + imageId);

                // TODO: 2016. 5. 14. get original path from thumbnail id or imageid.
                

                imagePath[count] = path;
                Uri uri = Uri.parse(
                        MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI + "/" + id);


                uris[count] = uri;
                count++;
            }
            data.close();

            Log.d(TAG, "!!!!!!!!");
            Log.d(TAG, "# : " + setDirectory.size());
            for(String path1 :setDirectory) {
                Log.d(TAG, "path : " + path1);
            }
            Log.d(TAG, "!!!!!!!!");
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
        getLoaderManager().initLoader(0, null, loaderCallbacks);
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
/*  Uri -> Path
    public String getPathFromUri(Uri uri){
        Cursor cursor = getContentResolver().query(uri, null, null, null, null );
        cursor.moveToNext();
        String path = cursor.getString( cursor.getColumnIndex( "_data" ) );
        cursor.close();

        return path;
    }*/
}
