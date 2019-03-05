package com.example.chess05.chess;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(92, 92));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0, 100, 0, 100);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    // references to our images
    private Integer[] mThumbIds = {


            /*R.drawable.whitesquare, R.drawable.bluesquare,  R.drawable.whitesquare, R.drawable.bluesquare,
            R.drawable.whitesquare, R.drawable.bluesquare,  R.drawable.whitesquare, R.drawable.bluesquare,


            R.drawable.bluesquare,  R.drawable.whitesquare, R.drawable.bluesquare,  R.drawable.whitesquare,
            R.drawable.bluesquare,  R.drawable.whitesquare, R.drawable.bluesquare,  R.drawable.whitesquare,

            R.drawable.whitesquare, R.drawable.bluesquare,  R.drawable.whitesquare, R.drawable.bluesquare,
            R.drawable.whitesquare, R.drawable.bluesquare,  R.drawable.whitesquare, R.drawable.bluesquare,

            R.drawable.bluesquare,  R.drawable.whitesquare, R.drawable.bluesquare,  R.drawable.whitesquare,
            R.drawable.bluesquare,  R.drawable.whitesquare, R.drawable.bluesquare,  R.drawable.whitesquare,

            R.drawable.whitesquare, R.drawable.bluesquare,  R.drawable.whitesquare, R.drawable.bluesquare,
            R.drawable.whitesquare, R.drawable.bluesquare,  R.drawable.whitesquare, R.drawable.bluesquare,

            R.drawable.bluesquare,  R.drawable.whitesquare, R.drawable.bluesquare,  R.drawable.whitesquare,
            R.drawable.bluesquare,  R.drawable.whitesquare, R.drawable.bluesquare,  R.drawable.whitesquare,

            R.drawable.whitesquare, R.drawable.bluesquare,  R.drawable.whitesquare, R.drawable.bluesquare,
            R.drawable.whitesquare, R.drawable.bluesquare,  R.drawable.whitesquare, R.drawable.bluesquare,

            R.drawable.bluesquare,  R.drawable.whitesquare, R.drawable.bluesquare,  R.drawable.whitesquare,
            R.drawable.bluesquare,  R.drawable.whitesquare, R.drawable.bluesquare,  R.drawable.whitesquare,*/

           /* R.drawable.graysquare, R.drawable.bluesquare, R.drawable.graysquare, R.drawable.bluesquare,
            R.drawable.graysquare, R.drawable.bluesquare, R.drawable.graysquare, R.drawable.bluesquare,

            R.drawable.bluesquare, R.drawable.graysquare, R.drawable.bluesquare, R.drawable.graysquare,
            R.drawable.bluesquare, R.drawable.graysquare, R.drawable.bluesquare, R.drawable.graysquare,

            R.drawable.graysquare, R.drawable.bluesquare, R.drawable.graysquare, R.drawable.bluesquare,
            R.drawable.graysquare, R.drawable.bluesquare, R.drawable.graysquare, R.drawable.bluesquare,

            R.drawable.bluesquare, R.drawable.graysquare, R.drawable.bluesquare, R.drawable.graysquare,
            R.drawable.bluesquare, R.drawable.graysquare, R.drawable.bluesquare, R.drawable.graysquare,

            R.drawable.graysquare, R.drawable.bluesquare, R.drawable.graysquare, R.drawable.bluesquare,
            R.drawable.graysquare, R.drawable.bluesquare, R.drawable.graysquare, R.drawable.bluesquare,

            R.drawable.bluesquare, R.drawable.graysquare, R.drawable.bluesquare, R.drawable.graysquare,
            R.drawable.bluesquare, R.drawable.graysquare, R.drawable.bluesquare, R.drawable.graysquare,

            R.drawable.graysquare, R.drawable.bluesquare, R.drawable.graysquare, R.drawable.bluesquare,
            R.drawable.graysquare, R.drawable.bluesquare, R.drawable.graysquare, R.drawable.bluesquare,

            R.drawable.bluesquare, R.drawable.graysquare, R.drawable.bluesquare, R.drawable.graysquare,
            R.drawable.bluesquare, R.drawable.graysquare, R.drawable.bluesquare, R.drawable.graysquare,*/

           R.drawable.graysquare, R.drawable.darkgraysquare, R.drawable.graysquare, R.drawable.darkgraysquare,
            R.drawable.graysquare, R.drawable.darkgraysquare, R.drawable.graysquare, R.drawable.darkgraysquare,

            R.drawable.darkgraysquare, R.drawable.graysquare, R.drawable.darkgraysquare, R.drawable.graysquare,
            R.drawable.darkgraysquare, R.drawable.graysquare, R.drawable.darkgraysquare,  R.drawable.graysquare,

            R.drawable.graysquare, R.drawable.darkgraysquare, R.drawable.graysquare, R.drawable.darkgraysquare,
            R.drawable.graysquare, R.drawable.darkgraysquare, R.drawable.graysquare, R.drawable.darkgraysquare,

            R.drawable.darkgraysquare, R.drawable.graysquare, R.drawable.darkgraysquare, R.drawable.graysquare,
            R.drawable.darkgraysquare, R.drawable.graysquare, R.drawable.darkgraysquare,  R.drawable.graysquare,

            R.drawable.graysquare, R.drawable.darkgraysquare, R.drawable.graysquare, R.drawable.darkgraysquare,
            R.drawable.graysquare, R.drawable.darkgraysquare, R.drawable.graysquare, R.drawable.darkgraysquare,

            R.drawable.darkgraysquare, R.drawable.graysquare, R.drawable.darkgraysquare, R.drawable.graysquare,
            R.drawable.darkgraysquare, R.drawable.graysquare, R.drawable.darkgraysquare,  R.drawable.graysquare,

            R.drawable.graysquare, R.drawable.darkgraysquare, R.drawable.graysquare, R.drawable.darkgraysquare,
            R.drawable.graysquare, R.drawable.darkgraysquare, R.drawable.graysquare, R.drawable.darkgraysquare,

            R.drawable.darkgraysquare, R.drawable.graysquare, R.drawable.darkgraysquare, R.drawable.graysquare,
            R.drawable.darkgraysquare, R.drawable.graysquare, R.drawable.darkgraysquare,  R.drawable.graysquare,

    };
}
