package com.example.akshar.imagedatabase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Akshar on 9/3/2017.
 */

public class ImageAdepter extends BaseAdapter {
    ArrayList<ImageDataStoreg>arrayList;
    Context context;

    public ImageAdepter(Context context, ArrayList<ImageDataStoreg> arrayList) {
        this.context=context;
        this.arrayList=arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=layoutInflater.inflate(R.layout.my_layout,null);

        TextView txtname=(TextView)view.findViewById(R.id.txtName);
        TextView txtnumber=(TextView)view.findViewById(R.id.txtnumber);
        ImageView imageView=(ImageView)view.findViewById(R.id.ImageView);
        return view;
    }
}
