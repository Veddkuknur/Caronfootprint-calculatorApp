package com.example.kuknur_mycarfootprint;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class AddVisitFragment extends DialogFragment {
    interface AddVisitDialogListener {
        void addGasVisit(int check, GasVisit gasVisit);
    }
    private AddVisitDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddVisitDialogListener) {
            listener = (AddVisitDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddVisitDialogListener");
        }
    }

    public static AddVisitFragment newInstance(GasVisit visit) {
        Bundle args = new Bundle();
        args.putSerializable("station_name", visit.getName());
        args.putSerializable("date_year", visit.getDate_year());
        args.putSerializable("date_month", visit.getDate_month());
        args.putSerializable("date_day", visit.getDate_day());
        args.putSerializable("fuel_type",visit.getType());
        args.putSerializable("fuel_amount",visit.getAmount());
        args.putSerializable("price_per_litre",visit.getPrice_per_litre());

        args.putSerializable("day",visit.getDate_day());
        args.putSerializable("month",visit.getDate_month());
        args.putSerializable("year",visit.getDate_year());

        AddVisitFragment fragment = new AddVisitFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_gass_vist, null);
        EditText editStationName = view.findViewById(R.id.edit_text_station_name);
        DatePicker visitDate = view.findViewById(R.id.visitDate);
        RadioGroup fuel_type = view.findViewById(R.id.fuel_type);
        EditText editFuelAmount = view.findViewById(R.id.edit_text_fuel_amount);
        EditText editFuelPrice = view.findViewById(R.id.edit_text_fuel_price);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        String Title ="Add a Visit";

        Bundle args= getArguments();
        String text_comment ="Visit added to list";
        if (args!=null){
            editStationName.setText(args.getSerializable("station_name").toString());
            editFuelAmount.setText(args.getSerializable("fuel_amount").toString());
            editFuelPrice.setText(args.getSerializable("price_per_litre").toString());

            visitDate.init(Integer.parseInt(args.getSerializable("year").toString()), Integer.parseInt(args.getSerializable("month").toString()), Integer.parseInt(args.getSerializable("day").toString()), null);
            if (Objects.equals(args.getSerializable("fuel_type").toString(), "Gasoline")) {
                fuel_type.check(R.id.Gasoline);
            }else {
                fuel_type.check(R.id.Diesel);
            }
            Title = "Edit a Visit";
            text_comment ="Visit edited successfully";
        }

        String finalText_comment = text_comment;
        return builder
                .setView(view)
                .setTitle(Title)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Ok", (dialog, which) -> {
                    String StationName = editStationName.getText().toString();
                    //LocalDate VisitDate = VisitDate;
                    String fuel_type_selected = null;
                    int check =0;
                    if (fuel_type.getCheckedRadioButtonId() == -1){
                        Toast.makeText(getContext(), "Please select Fuel Type", Toast.LENGTH_SHORT).show();
                        check+=1;
                    }else{
                        int btn_Id = fuel_type.getCheckedRadioButtonId();
                        RadioButton btn = view.findViewById(btn_Id);
                        fuel_type_selected = btn.getText().toString();
                    }

                    int FuelAmount = Integer.parseInt(editFuelAmount.getText().toString());
                    float FuelPrice = Float.parseFloat(editFuelPrice.getText().toString());
                    int date_year = visitDate.getYear();
                    int date_month = visitDate.getMonth();
                    int date_day = visitDate.getDayOfMonth();

                    if (Objects.equals(StationName, "")){
                        Toast.makeText(getContext(), "Please add station name", Toast.LENGTH_SHORT).show();
                        check+=1;
                    }
                    if (FuelPrice<0f){
                        Toast.makeText(getContext(), "Please enter valid price", Toast.LENGTH_SHORT).show();
                        check+=1;
                    }
                    if(FuelAmount<=0){
                        Toast.makeText(getContext(), "Please enter valid amount", Toast.LENGTH_SHORT).show();
                        check+=1;
                    }
                    if (check==0){
                        Toast.makeText(getContext(), finalText_comment, Toast.LENGTH_SHORT).show();
                        listener.addGasVisit(check,new GasVisit(StationName,date_year,date_month,date_day, fuel_type_selected,FuelAmount, FuelPrice));
                    }else{
                        listener.addGasVisit(check, new GasVisit(StationName,date_year,date_month,date_day, fuel_type_selected,FuelAmount, FuelPrice));
                    }

                })
                .create();
    }
}
