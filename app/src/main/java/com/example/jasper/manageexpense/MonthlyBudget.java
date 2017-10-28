package com.example.jasper.manageexpense;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
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
        labels.add(" ");

        BarGraphSeries<DataPoint> series = new BarGraphSeries<>();
        BarGraphSeries<DataPoint> series2 = new BarGraphSeries<>();
        int column = 1;
        for (Monthly_Budget_list budget : budgetList){
            labels.add(budget.getCategory());

            DataPoint point = new DataPoint(column, budget.getAmountSpent());
            DataPoint point2 = new DataPoint(column, budget.getBudgetAmount());
            series.appendData(point,true,budgetList.size());
            series2.appendData(point2,true,budgetList.size());

            column++;
        }
        labels.add(" ");
        series.setSpacing(10);
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.RED);
        series2.setSpacing(10);
        series2.setDrawValuesOnTop(true);
        series2.setValuesOnTopColor(Color.BLUE);
        graph.addSeries(series);
        graph.addSeries(series2);

        StaticLabelsFormatter formatter = new StaticLabelsFormatter(graph);
        String stringLabel[] = labels.toArray(new String[labels.size()]);
        formatter.setHorizontalLabels(stringLabel);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(column);


        graph.getGridLabelRenderer().setLabelFormatter(formatter);
        graph.getGridLabelRenderer().setNumHorizontalLabels((labels.size()));
        graph.getGridLabelRenderer().setHorizontalLabelsAngle(30);


        series.setColor(Color.BLUE);
        series2.setColor(Color.RED);



        series.setTitle("Spent");
        series2.setTitle("Budget");
        graph.setTitle("Expenses vs Budget");
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        return view;
    }
}
