/*
 * Copyright (c) 2018 by Tran Le Duy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package flynn.tim.ciphersolver.frequency;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.duy.common.ads.AdsManager;
import com.duy.common.views.ViewUtils;
import com.duy.text.converter.R;
import com.duy.text.converter.activities.base.BaseActivity;
import com.duy.text.converter.pro.license.Premium;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;


public class FrequencyActivity extends BaseActivity implements TextWatcher {
    private BarChart mChart;
    private EditText mInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frequency);
        setupToolbar();
        setTitle(R.string.title_frequency_analysis);

        mChart = findViewById(R.id.chart_view);
        initChart();

        mInput = findViewById(R.id.edit_input);
        mInput.addTextChangedListener(this);

        if (Premium.isPremium(this)){
            View containerAd = findViewById(R.id.container_ad);
            if (containerAd != null) containerAd.setVisibility(View.GONE);
        }else {
            AdsManager.loadAds(this, findViewById(R.id.container_ad), findViewById(R.id.ad_view));
        }
    }

    private void initChart() {
        mChart.setDescription("");

        mChart.getAxisLeft().setDrawLabels(false);
        mChart.getAxisRight().setDrawLabels(false);

        mChart.getXAxis().setTextColor(ViewUtils.getColorFromAttr(this, android.R.attr.textColorPrimary));
        mChart.getXAxis().setLabelsToSkip(0);

        mChart.setPinchZoom(false);
        mChart.setDoubleTapToZoomEnabled(false);
    }

    private void drawChart() {


        HashMap<Character, Integer> analyze = FrequencyAnalysis.analyze(mInput.getText().toString());
        ArrayList<String> labels = getLabels(analyze);

        ArrayList<BarEntry> yVals = new ArrayList<>();
        for (Map.Entry<Character, Integer> entry : analyze.entrySet()) {
            yVals.add(new BarEntry(entry.getValue(), labels.indexOf(entry.getKey() + "")));
        }

        BarDataSet dataSet = new BarDataSet(yVals, "");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return String.valueOf((int) value);
            }
        });

        BarData barData = new BarData(labels, dataSet);
        barData.setValueTextColor(ViewUtils.getColorFromAttr(this, android.R.attr.textColorPrimary));

        setData(barData);
    }

    private void setData(BarData barData) {
        mChart.setData(barData);
        mChart.animateY(500);
        //mChart.invalidate();
    }

    private ArrayList<String> getLabels(HashMap<Character, Integer> analyze) {
        ArrayList<String> labels = new ArrayList<>();
        for (Map.Entry<Character, Integer> entry : analyze.entrySet()) {
            labels.add(String.valueOf(entry.getKey()));
        }
        Collections.sort(labels, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        return labels;
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        drawChart();
    }
}
