package com.example.jasper.manageexpense;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


import android.app.Activity;
import android.app.LocalActivityManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorTreeAdapter;
import android.widget.TabHost;


import org.w3c.dom.Text;

import java.util.Date;

public class CreateReport extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_report);


        DBHelper dbHelper = new DBHelper(this);
        String detailFromMethod = dbHelper.getReportData();

        TextView t=(TextView)findViewById(R.id.reportText);
        t.setText(detailFromMethod);
    }
}

