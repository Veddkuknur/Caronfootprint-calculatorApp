package com.example.kuknur_mycarfootprint;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.concurrent.atomic.AtomicReference;

public class DeleteVisit extends DialogFragment {

    interface DeleteVisitDialogListener {
        void flag(Boolean deleteVisit);
    }
    private DeleteVisitDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof DeleteVisitDialogListener) {
            listener = (DeleteVisitDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddVisitDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_delete_visit, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        return builder
                .setView(view)
                .setTitle("Delete Visit")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Confirm", (dialog, which) -> {
                    Toast.makeText(getContext(), "Visit Deleted", Toast.LENGTH_SHORT).show();
                    listener.flag(true);
                })
                .create();
    }
}
