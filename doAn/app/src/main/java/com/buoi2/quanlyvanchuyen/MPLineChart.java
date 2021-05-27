package com.buoi2.quanlyvanchuyen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class MPLineChart extends AppCompatActivity {

    LineChart lineChart;
    LineData lineData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_p_line_chart);

        lineChart=findViewById(R.id.lineChart);

        float yValues [] = {10,50,30,40,20,60};
        String xValues [] = {"cat", "dog", "chick", "cow", "duck", "pig"};
        drawLineChart(yValues, xValues);
    }

    private void drawLineChart(float[] yValues, String[] xValues) {
        ArrayList<Entry> yData = new ArrayList<>();
        for(int i=0; i<yValues.length; i++){
            yData.add(new Entry(yValues[i], i));
        }
        ArrayList<String> xData = new ArrayList<>();
        for(int i=0; i<xValues.length; i++){
            xData.add(xValues[i]);
        }

        LineDataSet lineDataSet = new LineDataSet(yData,"Line Chart");
        lineDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        lineDataSet.setFillAlpha(110);
        lineDataSet.setLineWidth(3f);
        lineData = new LineData(xData, lineDataSet);
        lineChart.setData(lineData);
        lineChart.setVisibleXRangeMaximum(10);
        lineChart.invalidate();
    }
}