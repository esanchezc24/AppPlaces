package com.example.appplaces.view.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.appplaces.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FotoAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<String> lisFotos;

    public FotoAdapter(Context context, ArrayList<String> lisFotos) {
        this.context = context;
        this.lisFotos = lisFotos;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_fotos, null);
        ImageView imageView = view.findViewById(R.id.imageView);
        String url = lisFotos.get(position);
        imageView.setImageResource(R.drawable.googleg_standard_color_18);
        Picasso.with(context).load(url).into(imageView);
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return lisFotos.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return o == view;
    }
}
