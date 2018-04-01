package com.example.tuananhkid.bai2.activity;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tuananhkid.bai2.R;
import com.example.tuananhkid.bai2.Utils;
import com.example.tuananhkid.bai2.adapter.ImageAdapter;
import com.example.tuananhkid.bai2.models.Image;

import java.util.ArrayList;
import java.util.List;

public class ShowImageActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ImageView img_ys;
    Gallery gal_img;
    List<Image> lsImgUrl;
    ImageAdapter adapter;
    int position;
    ArrayList<String> lsUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);

        getDataIntent();
        initWidget();
        initData();
    }

    public void initWidget() {
        img_ys = findViewById(R.id.img_ys);
        gal_img = findViewById(R.id.gal_img);
    }

    @SuppressLint("NewApi")
    public void initData() {
        lsImgUrl = new ArrayList<>();
        for (int i=0;i<lsUrl.size();i++) {
            if(i!=position){
                lsImgUrl.add(new Image(lsUrl.get(i), 0.2f));

            }else {
                lsImgUrl.add(new Image(lsUrl.get(i), 1f));
            }
        }
        adapter = new ImageAdapter(this, -2, lsImgUrl);
        gal_img.setAdapter(adapter);
        gal_img.setOnItemClickListener(this);

        Uri uri = Uri.parse(lsImgUrl.get(position).getUrl());
        img_ys.setImageURI(uri);
        gal_img.setSelection(position);
    }

    public void getDataIntent() {
        if (getIntent().getExtras() != null) {
            position = Integer.parseInt(getIntent().getStringExtra("URL"));
            lsUrl = getIntent().getStringArrayListExtra("lsURL");
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        // img_ys.setImageResource(lsUrlImg.get(i));
        Uri uri = Uri.parse(lsImgUrl.get(i).getUrl());
        lsImgUrl.get(i).setAlpha(1);
        for (int j=0;j<lsUrl.size();j++) {
            if(i!=j){
                lsImgUrl.get(j).setAlpha(0.1f);
            }
        }
        adapter.notifyDataSetChanged();
        img_ys.setImageURI(uri);
    }
}
