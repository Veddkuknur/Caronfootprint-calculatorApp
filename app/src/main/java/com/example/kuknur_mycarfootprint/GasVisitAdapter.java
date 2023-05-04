package com.example.kuknur_mycarfootprint;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class GasVisitAdapter extends ArrayAdapter<GasVisit> {
    public GasVisitAdapter(Context context, ArrayList<GasVisit> visits){
        super(context,0, visits);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View view;
        if (convertView == null){
            view = LayoutInflater.from(super.getContext()).inflate(R.layout.content,parent,false);
        }else{
            view =convertView;
        }
        GasVisit visit =super.getItem(position);
        TextView gas_station_name = view.findViewById(R.id.gas_Station_name);
        TextView fuel_used = view.findViewById(R.id.fuel_used);
        TextView date_filled =view.findViewById(R.id.date_filled);
        gas_station_name.setText(visit.getName());
        fuel_used.setText(visit.getType());
        date_filled.setText(visit.getCrb_footprint());
        return view;

    }
}
