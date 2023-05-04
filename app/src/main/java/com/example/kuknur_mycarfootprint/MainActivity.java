package com.example.kuknur_mycarfootprint;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements AddVisitFragment.AddVisitDialogListener,DeleteVisit.DeleteVisitDialogListener{
    private ArrayList<GasVisit> dataList;
    private ListView stationVisits;
    private GasVisitAdapter visitAdapter;
    private int pos;
    private Float TotalFuelCost;
    private float TotalCarbonFootprint;

    @Override
    public void addGasVisit(int check, GasVisit gasVisit) {
        if (check==0) {
            if (pos < 0) {
                visitAdapter.add(gasVisit);
                visitAdapter.notifyDataSetChanged();
            } else {
                dataList.get(pos).setName(gasVisit.getName());
                dataList.get(pos).setAmount(gasVisit.getAmount());
                dataList.get(pos).setType(gasVisit.getType());
                dataList.get(pos).setFill_date(gasVisit.getFill_date());
                dataList.get(pos).setPrice_per_litre(gasVisit.getPrice_per_litre());
                visitAdapter = new GasVisitAdapter(this, dataList);
                stationVisits.setAdapter(visitAdapter);
            }
        }
        Float t_fuelcost = CalculateFuelCosts(dataList);
        int t_carbonCost = Math.round(Carbonfootprint(dataList));
        TextView total_fuel_cost = findViewById(R.id.total_fuel_cost);
        TextView total_CFP =findViewById(R.id.total_CFP);
        total_fuel_cost.setText("Total Fuel Cost: "+String.format("%.2f", t_fuelcost));
        total_CFP.setText("Total Carbon footprint: "+String.valueOf(t_carbonCost));
    }
    @Override
    public void flag(Boolean deleteVisit){
        if (deleteVisit){
            dataList.remove(pos);
            Float t_fuelcost = CalculateFuelCosts(dataList);
            int t_carbonCost = Math.round(Carbonfootprint(dataList));
            TextView total_fuel_cost = findViewById(R.id.total_fuel_cost);
            TextView total_CFP =findViewById(R.id.total_CFP);
            total_fuel_cost.setText("Total Fuel Cost: "+String.format("%.2f", t_fuelcost));
            total_CFP.setText("Total Carbon footprint: "+String.valueOf(t_carbonCost));
            visitAdapter.notifyDataSetChanged();
        }
    }
    public Float CalculateFuelCosts(ArrayList<GasVisit> dataList){
        TotalFuelCost = 0f;
        for (int i = 0; i < dataList.toArray().length; i++) {
            TotalFuelCost = TotalFuelCost +(dataList.get(i).getPrice_per_litre()*dataList.get(i).getAmount());
        }
        return TotalFuelCost;
    }
    public Float Carbonfootprint (ArrayList<GasVisit> dataList){
        TotalCarbonFootprint = 0f;
        for (int i = 0; i < dataList.toArray().length; i++) {
            if(Objects.equals(dataList.get(i).getType(), "Gasoline")){
            TotalCarbonFootprint = TotalCarbonFootprint+(dataList.get(i).getAmount()*2.32f);
        }else{
                TotalCarbonFootprint = TotalCarbonFootprint +(dataList.get(i).getAmount()*2.69f);
            }
        }
        return TotalCarbonFootprint;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataList = new ArrayList<>();
        stationVisits = findViewById(R.id.visit_list);
        visitAdapter = new GasVisitAdapter(this, dataList);
        stationVisits.setAdapter(visitAdapter);

        FloatingActionButton fab = findViewById(R.id.button_add_visit);
        fab.setOnClickListener(v -> {
            pos =-1;
            new AddVisitFragment().show(getSupportFragmentManager(), "Add Visit");

        });

        stationVisits.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
                GasVisit visit1 = dataList.get(position);
                AddVisitFragment.newInstance(visit1).show(getSupportFragmentManager(), "Add Visit");
            }
        });
        
        stationVisits.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
                new DeleteVisit().show(getSupportFragmentManager(),"Delete Visit");
                return true;
            }
        });
    }

}