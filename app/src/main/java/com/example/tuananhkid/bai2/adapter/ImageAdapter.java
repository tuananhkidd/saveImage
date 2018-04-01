package com.example.tuananhkid.bai2.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.tuananhkid.bai2.R;
import com.example.tuananhkid.bai2.models.Image;

import java.util.List;

/**
 * Created by TuanAnhKid on 3/6/2018.
 */

public class ImageAdapter extends ArrayAdapter<Image> {
    private Context context;
    private int layout;
    private List<Image> lsImgUrl;

    public ImageAdapter(@NonNull Context context, int resource, List<Image> lsImgUrl) {
        super(context, resource, lsImgUrl);
        this.context = context;
        this.layout = resource;
        this.lsImgUrl = lsImgUrl;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Viewholder viewholder = null;
        if (convertView == null) {
            viewholder = new Viewholder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_imager, null, false);
            viewholder.img = convertView.findViewById(R.id.img);

            convertView.setTag(viewholder);
        } else {
            viewholder = (Viewholder) convertView.getTag();
        }

        viewholder.img.setImageURI(Uri.parse(lsImgUrl.get(position).getUrl()));
        viewholder.img.setAlpha(lsImgUrl.get(position).getAlpha());

        return convertView;
    }

    class Viewholder {
        ImageView img;
    }
}
