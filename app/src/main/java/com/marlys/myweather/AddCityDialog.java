package com.marlys.myweather;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.marlys.myweather.R;
import com.marlys.myweather.listener.DialogClickListener;

public class AddCityDialog extends AppCompatDialogFragment {
    private EditText addCity;
    private DialogClickListener dialogClickListener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_city_dialog, null);
        builder.setView(view)
                .setTitle(getString(R.string.menu_add_city))
                .setNegativeButton(getString(R.string.cancle), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton(getString(R.string.add_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                            String cityName = addCity.getText().toString();
                            dialogClickListener.applyText(cityName);
                    }
                });
        addCity = view.findViewById(R.id.city_add_text);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dialogClickListener = (DialogClickListener) context;
    }
}
