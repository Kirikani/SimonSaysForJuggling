package com.nagisa.simonsaysforjuggling;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class CheckBoxes extends LinearLayout {

    public CheckBoxes(Context context, AttributeSet attrs) {
        super(context, attrs);
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, wazaname);

        ArrayList<CheckBox> CB=new ArrayList<CheckBox>();
        //   CB.add()
        CheckBox CB0 = new CheckBox(context);
        //for(i=0;i<4/*wazaname.size()*/;i++) {
        // CBwaza[i].setText(wazaname.get(i));

        CB0.setText("カスケード");
        layout.addView(CB0, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        //}
      /*  CheckBox CB1 = new CheckBox(context);
        CB1.setText(wazaname.get(1));
        layout.addView(CB1, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        CheckBox CB2 = new CheckBox(context);
        CB2.setText(wazaname.get(2));
        layout.addView(CB2, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        CheckBox CB3 = new CheckBox(context);
        CB3.setText(wazaname.get(3));
        layout.addView(CB3, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));*/
       // setContentView(layout);
    }
}
