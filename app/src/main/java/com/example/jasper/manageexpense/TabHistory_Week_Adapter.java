package com.example.jasper.manageexpense;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.example.com.example.utilities.DateUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Techsoft-003 on 3/16/2017.
 */

public class TabHistory_Week_Adapter extends BaseAdapter{
    Context context;
    List<TabHistory_Week_List> listWeek;


    public TabHistory_Week_Adapter(Context context, List<TabHistory_Week_List> listWeek){
        this.context = context;
        this.listWeek = listWeek;

    }

    @Override
    public int getCount() {
        return listWeek.size();
    }

    @Override
    public Object getItem(int position) {
        return listWeek.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = View.inflate(context, R.layout.tab_history_week_list, null);

        TextView txtName = (TextView)view.findViewById(R.id.tbhw_name);
        TextView txtDate = (TextView)view.findViewById(R.id.tbhw_date);
        TextView txtAmount = (TextView)view.findViewById(R.id.tbhw_amount);
        TextView txtNote = (TextView)view.findViewById(R.id.tbhw_note);
        TextView txtCurrency = (TextView)view.findViewById(R.id.tbhw_currency); //pGhale: configures the textView for currency
        TextView txtPayment = (TextView)view.findViewById(R.id.tbhw_paymenttype); //pGhale US11: configures the textview for payment type
        TextView txtLocation = (TextView)view.findViewById(R.id.tbhw_location); //pGhale US10: configures the textview for location

        txtName.setText(listWeek.get(position).getName());
        DecimalFormat precision = new DecimalFormat("0.00");
        txtAmount.setText(precision.format(listWeek.get(position).getAmount()));

        //Shelanskey US8 - convert Date Object to text
        txtDate.setText(DateUtil.convertDateToText(listWeek.get(position).getDate()));
        txtNote.setText(listWeek.get(position).getNote());
        txtCurrency.setText(listWeek.get(position).getCurrency()); //pGhale: sets the currency text
        txtPayment.setText(listWeek.get(position).getPayment()); //pGhale US11: sets the payment text
        txtLocation.setText(listWeek.get(position).getLocation()); //pGhale US10:sets the location text

        return view;
    }
    }


