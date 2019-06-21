package com.example.appplaces.view.home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appplaces.R;
import com.example.appplaces.entity.Place;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {
    private ArrayList<Place> lisPlaces;

    public MainAdapter(ArrayList<Place> lisPlaces) {
        this.lisPlaces = lisPlaces;
    }

    @Override
    public MainAdapter.MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_place, parent, false));
    }

    @Override
    public void onBindViewHolder(MainAdapter.MainViewHolder holder, int position) {
        Place place = lisPlaces.get(position);
//        holder.imgFoto.setImageResource(place.getArrayFotos().get(0).substring("url"));
        holder.tvDescription.setText(place.getDescription());
        holder.tvUser.setText(place.getUser().getName());
    }

    @Override
    public int getItemCount() {
        return lisPlaces.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFoto;
        TextView tvDescription;
        TextView tvUser;
        public MainViewHolder(View itemView) {
            super(itemView);
            imgFoto = (ImageView) itemView.findViewById(R.id.imgFoto);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            tvUser = (TextView) itemView.findViewById(R.id.tvUser);
        }
    }
}
