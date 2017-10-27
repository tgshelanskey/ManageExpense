package com.example.jasper.manageexpense;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by woodw on 10/26/2017.
 */

//shelanskey US12: Create Histogram
public class MonthlyBudget extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.monthly_budget, container, false);

        GraphView graph = (GraphView) view.findViewById(R.id.graph);
        DBHelper db = new DBHelper(getContext());
        List<Monthly_Budget_list> budgetList = db.getMonthlyHistory();
        List<String> labels = new ArrayList<String>();

        BarGraphSeries<DataPoint> series = new BarGraphSeries<>();
        BarGraphSeries<DataPoint> series2 = new BarGraphSeries<>();
        int column = 0;
        for (Monthly_Budget_list budget : budgetList){
            labels.add(budget.getCategory());
            //labels.add(budget.getCategory() + "Max");
            DataPoint point = new DataPoint(column, budget.getAmountSpent());
            DataPoint point2 = new DataPoint(column, budget.getBudgetAmount());
            series.appendData(point,true,budgetList.size()*2);
            series2.appendData(point2,true,budgetList.size()*2);

            column++;
        }
        series.setSpacing(50);
        series2.setSpacing(50);
        graph.addSeries(series);
        graph.addSeries(series2);

        StaticLabelsFormatter formatter = new StaticLabelsFormatter(graph);
        String stringLabel[] = labels.toArray(new String[labels.size()]);
        formatter.setHorizontalLabels(stringLabel);
        graph.getGridLabelRenderer().setLabelFormatter(formatter);
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
           public int get(DataPoint data) {
                return Color.rgb((int) data.getX() * 255 / 4, (int) Math.abs(data.getY() * 255 / 6), 100);
            }
        });
        //graph.getViewport().setScrollable(true); // enables horizontal scrolling
        //graph.getViewport().setScrollableY(true); // enables vertical scrolling
        //graph.getViewport().setScalable(true); // enables horizontal zooming and scrolling
        //graph.getViewport().setScalableY(true); // enables vertical zooming and scrolling
        return view;
    }
}
