package com.example.chess05.chess;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageAdapter_Pieces extends BaseAdapter {


    private Context mContext;

    public ImageAdapter_Pieces(Context c) {
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
    public Integer[] mThumbIds = {

        R.drawable.blackrook, R.drawable.blackknight, R.drawable.blackbishop, R.drawable.blackqueen, R.drawable.blackking,
            R.drawable.blackbishop, R.drawable.blackknight, R.drawable.blackrook,

            R.drawable.blackpawn, R.drawable.blackpawn, R.drawable.blackpawn, R.drawable.blackpawn,
            R.drawable.blackpawn, R.drawable.blackpawn, R.drawable.blackpawn, R.drawable.blackpawn,

            0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,

            R.drawable.whitepawn, R.drawable.whitepawn, R.drawable.whitepawn, R.drawable.whitepawn,
            R.drawable.whitepawn, R.drawable.whitepawn, R.drawable.whitepawn, R.drawable.whitepawn,

            R.drawable.whiterook, R.drawable.whiteknight, R.drawable.whitebishop, R.drawable.whitequeen, R.drawable.whiteking,
            R.drawable.whitebishop, R.drawable.whiteknight, R.drawable.whiterook

    };
}
