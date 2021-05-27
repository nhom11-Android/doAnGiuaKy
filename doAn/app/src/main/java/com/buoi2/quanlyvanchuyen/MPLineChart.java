package com.buoi2.quanlyvanchuyen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.buoi2.quanlyvanchuyen.bean.ChiTietPhieuVanChuyen;
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
        setActionBar();
        lineChart = findViewById(R.id.lineChart);
        Intent intent = getIntent();
        ArrayList<Integer> doanhThuTheoThang = intent.getIntegerArrayListExtra("doanhThuThang");
        ArrayList<Float> yValues = new ArrayList<Float>();
//        int[] doanhThuTheoThang = new ThongKeDoanhThuTheoThang().doanhThuTheoThang();
        for (int m = 1; m <= 12; m++) {
            yValues.add((float) doanhThuTheoThang.get(m - 1));
        }
//        String xValues[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
//        Float yValues[] = {1.16f , 2.1f, 4f, 5f,7f, 3f, 54f, 24f, 64f, 23f, 45f, 24f};
        drawLineChart(yValues);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Đồ thị doanh thu theo tháng");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

        private void drawLineChart(ArrayList<Float> yValues) {
        ArrayList<Entry> entryList = new ArrayList<>();
        for(int i=0; i<yValues.size(); i++){
            entryList.add(new Entry(i+1,(float)yValues.get(i)));
        }

        LineDataSet lineDataSet = new LineDataSet(entryList,"Doanh thu theo tháng");
        lineDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        lineDataSet.setFillAlpha(110);
        lineDataSet.setLineWidth(3f);
        lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
        lineChart.setVisibleXRangeMaximum(12);
        lineChart.invalidate();
    }
//    private void drawLineChart(Float[] yValues) {
//        ArrayList<Entry> entryList = new ArrayList<>();
//        for (int i = 0; i < yValues.length; i++) {
//            entryList.add(new Entry(i+1, (float) yValues[i]));
//        }
//
//        LineDataSet lineDataSet = new LineDataSet(entryList, "Thống kê theo tháng");
//        lineDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
//        lineDataSet.setFillAlpha(110);
//        lineDataSet.setLineWidth(3f);
//        lineData = new LineData(lineDataSet);
//        lineChart.setData(lineData);
//        lineChart.setVisibleXRangeMaximum(12);
//        lineChart.invalidate();
//    }
}