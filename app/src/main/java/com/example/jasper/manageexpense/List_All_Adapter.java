package com.example.jasper.manageexpense;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Techsoft - 001 on 5/11/2017.
 */

public class List_All_Adapter extends BaseAdapter {

    Context context;
    List<List_All> listAlls;
    String currencyType;

    public List_All_Adapter (Context context, List<List_All> listAlls, String currencyType){
        this.context = context;
        this.listAlls = listAlls;
        this.currencyType = currencyType;

    }

    @Override
    public int getCount() {
        return listAlls.size();
    }

    @Override
    public Object getItem(int position) {
        return listAlls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = View.inflate(context, R.layout.overview_list, null);
        TextView txtName = (TextView) view.findViewById(R.id.txtOverview_name);
        TextView txtAmount = (TextView) view.findViewById(R.id.txtOverview_amount);
        ProgressBar progressBar = (ProgressBar)view.findViewById(R.id.progressBar);

        txtName.setText(listAlls.get(position).getName());
        DecimalFormat precision = new DecimalFormat("0.00");
        txtAmount.setText(precision.format(listAlls.get(position).getAmount()) + currencyType);
        progressBar.setProgress(txtAmount.getAutoLinkMask());
        progressBar.setProgress(0);
        progressBar.setMax(100);

        return view;
    }
}
