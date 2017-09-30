package com.example.jasper.manageexpense;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.com.example.utilities.CurrencyHandler;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Jasper on 2/27/2017.
 */

public class Setting extends Activity implements AdapterView.OnItemSelectedListener{
    RelativeLayout relativeLayout;
    Spinner spinnerCurrency;

    @Override
    public void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        DBHelper dbHelper = new DBHelper(getApplicationContext());
        String currencyType = dbHelper.getSetting("CURRENCY");
        int preferredCurrency = CurrencyHandler.lookupPosition(currencyType);
        spinnerCurrency = (Spinner) findViewById(R.id.spinnerPreferredCurrency); //pGhale
        spinnerCurrency.setOnItemSelectedListener(this);
        spinnerCurrency.setSelection(preferredCurrency);

        Button btnBack = (Button)findViewById(R.id.btn_settings_back);
        Button editExpense = (Button)findViewById(R.id.btn_settings_editExp);
        Button addCategory = (Button)findViewById(R.id.btn_settings_addCat);
        relativeLayout = (RelativeLayout) findViewById(R.id.content_main);
        //final EditText editText = (EditText)findViewById(R.id.editText_settings);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        editExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Update_AddExpense.class);
                startActivity(i);
            }
        });

        addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Tab1.class);
                startActivity(i);
            }
        });

        String currency = spinnerCurrency.getSelectedItem().toString(); //pGhale
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        String label = parent.getItemAtPosition(position).toString();
        String currencyType = spinnerCurrency.getSelectedItem().toString(); //pGhale
        dbHelper.updateSetting("CURRENCY", currencyType);
        Toast.makeText(parent.getContext(), "Selected Currency: " + currencyType + " ", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(parent.getContext(), "Please select currency", Toast.LENGTH_LONG).show();
    }
}

