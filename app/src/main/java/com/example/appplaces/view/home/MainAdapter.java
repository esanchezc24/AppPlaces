package com.example.appplaces.view.home;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appplaces.R;
import com.example.appplaces.entity.Place;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {
    private ArrayList<Place> lisPlaces;
    ViewPager viewPager;
    private FotoAdapter fotoAdapter;
    Context context;

    public MainAdapter(ArrayList<Place> lisPlaces,Context context) {
        this.lisPlaces = lisPlaces;
        this.context = context;

    }

    @Override
    public MainAdapter.MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_place, parent, false));
    }

    @Override
    public void onBindViewHolder(MainAdapter.MainViewHolder holder, int position) {
        Place place = lisPlaces.get(position);
        fotoAdapter = new FotoAdapter(context,(ArrayList<String>) lisPlaces.get(position).getArrayFotos());
        viewPager.setAdapter(fotoAdapter);
//        holder.imgFoto.setImageResource(R.drawable.ic_launcher_background);
//        Picasso.with(context).load(lisPlaces.get(position).getArrayFotos().get(0)).into(holder.imgFoto);
        holder.tvDescription.setText(place.getDescription());
        holder.tvUser.setText(place.getUser().getName());
    }

    @Override
    public int getItemCount() {
        return lisPlaces.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
//        ImageView imgFoto;
        TextView tvDescription;
        TextView tvUser;
        public MainViewHolder(View itemView) {
            super(itemView);
//            imgFoto = (ImageView) itemView.findViewById(R.id.imgFoto);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            tvUser = (TextView) itemView.findViewById(R.id.tvUser);
            viewPager = (ViewPager) itemView.findViewById(R.id.imgFoto);
        }
    }
}
