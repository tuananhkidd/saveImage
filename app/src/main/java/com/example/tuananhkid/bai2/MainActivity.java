package com.example.tuananhkid.bai2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tuananhkid.bai2.activity.ShowImageActivity;
import com.example.tuananhkid.bai2.adapter.ImageAdapter;
import com.example.tuananhkid.bai2.models.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageAdapter adapter;
    GridView grd_img;
    List<Image> lsImgUrl;
    private static final int REQUEST_PERMISSIONS = 100;
    ArrayList<String> lsUrl;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lsImgUrl = new ArrayList<>();

        saveImageInternalStorage();
        readImageFromInternalStorage();

        if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE))) {

            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);
            }
        } else {
            lsUrl = Utils.getAllImagesPath(this);
            for (String url : lsUrl) {
                lsImgUrl.add(new Image(url, 1f));
            }
            // adapter.notifyDataSetChanged();
        }

        initData();

        grd_img.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, ShowImageActivity.class);
                intent.putExtra("URL", i + "");
                intent.putStringArrayListExtra("lsURL", lsUrl);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSIONS: {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults.length > 0 && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        ArrayList<String> lsUrl = Utils.getAllImagesPath(this);
                        lsImgUrl.clear();
                        for (String url : lsUrl) {
                            lsImgUrl.add(new Image(url, 1f));
                        }
                        adapter.notifyDataSetChanged();
                        Log.e("Else", lsImgUrl.size() + "");
                    } else {
                        Toast.makeText(MainActivity.this, "The app was not allowed to read or write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

    public void initData() {


        grd_img = findViewById(R.id.grd_img);

        adapter = new ImageAdapter(this, -1, lsImgUrl);

        grd_img.setAdapter(adapter);
    }

    public void saveImageInternalStorage() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a1);
        ContextWrapper wrapper = new ContextWrapper(getApplicationContext());
        File file = wrapper.getDir("Images", MODE_PRIVATE);
        file = new File(file, "a1" + ".jpg");

        try {
            OutputStream os = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readImageFromInternalStorage() {
        try {
            ContextWrapper cw = new ContextWrapper(this);
            File directory = cw.getDir("Images", MODE_PRIVATE);
            File f = new File(directory, "a1.jpg");
            Log.i("bitmap123", f.toString());
            lsImgUrl.add(new Image(f.toString(), 1f));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
