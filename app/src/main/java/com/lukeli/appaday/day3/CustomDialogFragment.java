package com.lukeli.appaday.day3;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

public class CustomDialogFragment extends DialogFragment{
    private enum CustomColor {
        RED(R.id.make_red, R.string.red_string, Color.RED),
        WHITE(R.id.make_white, R.string.white_string, Color.WHITE),
        BLUE(R.id.make_blue, R.string.blue_string, Color.BLUE);
        protected int id, color_int, color_string_id;
        CustomColor(int r_id, int color_string_name_id, int color_id) {
            this.id = r_id;
            this.color_string_id = color_string_name_id;
            this.color_int = color_id;
        }

        protected String color_name(Context ctx) {
            return ctx.getString(color_string_id);
        }
    }
    private CustomColor customColor;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle args = getArguments();
        if (args != null) {
            int arg_id = args.getInt("color_id");
            for(CustomColor customC : CustomColor.values()){
                if(customC.id == arg_id){
                    customColor = customC;
                }
            }
        }

        AlertDialog.Builder theDialog = new AlertDialog.Builder(getActivity());

        theDialog.setTitle(getString(R.string.change_color_prompt));
        String change_string = getString(R.string.really_change_color);
        theDialog.setMessage(String.format(change_string, customColor.color_name(getActivity())));

        theDialog.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().getWindow().getDecorView().setBackgroundColor(customColor.color_int);
            }
        });

        theDialog.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), getString(R.string.no_change), Toast.LENGTH_SHORT).show();
            }
        });


        return theDialog.create();
    }

    public static CustomDialogFragment newInstance(int r_color_id) {
        CustomDialogFragment customFragment = new CustomDialogFragment();

        Bundle args = new Bundle();
        args.putInt("color_id", r_color_id);
        customFragment.setArguments(args);
        return customFragment;
    }
}
