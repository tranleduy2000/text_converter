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
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.duy.common.ads.AdsManager;
import com.duy.common.views.ViewUtils;
import com.duy.text.converter.R;
import com.duy.text.converter.core.activities.base.BaseActivity;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class FrequencyActivity extends BaseActivity implements TextWatcher {
    private BarChart mChartView;
    private EditText mInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frequency);
        setupToolbar();
        setTitle(R.string.title_frequency_analysis);

        mChartView = findViewById(R.id.chart_view);
        mInput = findViewById(R.id.edit_input);
        mInput.addTextChangedListener(this);

        AdsManager.loadAds(this, findViewById(R.id.container_ad), findViewById(R.id.ad_view));
    }

    private void drawChart() {
        Legend legend = mChartView.getLegend();
        legend.setEnabled(false);

        mChartView.setDescription("");
        mChartView.setScaleYEnabled(false);
        mChartView.setDoubleTapToZoomEnabled(false);
        mChartView.setDrawHighlightArrow(true);
        mChartView.animateY(1000, Easing.EasingOption.Linear);

        YAxis leftAxis = mChartView.getAxisLeft();
        leftAxis.setEnabled(false);
        YAxis rightAxis = mChartView.getAxisRight();
        rightAxis.setEnabled(false);
        HashMap<Character, Integer> map = FrequencyAnalysis.analyze(mInput.getText().toString().toUpperCase());
        XAxis xaxis = mChartView.getXAxis();
        xaxis.setLabelsToSkip(0);
        xaxis.setAvoidFirstLastClipping(true);
        xaxis.setSpaceBetweenLabels(10);
        List<BarEntry> entryList = new ArrayList<>();
        ArrayList<String> xVals = new ArrayList<>();
        Iterator it = map.entrySet().iterator();

        int x = 0;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (!pair.getKey().toString().equals(" ")) {
                xVals.add(pair.getKey().toString());
                BarEntry e = new BarEntry(Float.parseFloat(pair.getValue().toString()), x);
                entryList.add(e);
                x = x + 1;
            }
            it.remove(); // avoids a ConcurrentModificationException
        }

        //Create FrequencyPair list for alphabetical sorting
        ArrayList<FrequencyPair> fplist = new ArrayList<>();

        for (int i = 0; i < xVals.size(); i++) {
            fplist.add(new FrequencyPair(xVals.get(i), Math.round(entryList.get(i).getVal())));
        }

        Collections.sort(fplist, new Comparator<FrequencyPair>() {
            @Override
            public int compare(FrequencyPair lhs, FrequencyPair rhs) {
                return lhs.getCharacter().compareTo(rhs.getCharacter());
            }
        });

        //Sorted lists
        ArrayList<String> alphaXVals = new ArrayList<>();
        ArrayList<BarEntry> alphaEntryList = new ArrayList<>();

        //Iterate and populate sorted lists
        for (int j = 0; j < fplist.size(); j++) {
            alphaXVals.add(fplist.get(j).getCharacter());
            alphaEntryList.add(new BarEntry(fplist.get(j).getValue(), j));
        }

        //Create BarDataSet using sorted list
        BarDataSet dataSet = new BarDataSet(alphaEntryList, "Frequency");
        dataSet.setColor(ViewUtils.getAccentColor(this));

        BarData data = new BarData(alphaXVals, dataSet);
        data.setValueTextColor(ViewUtils.getColorFromAttr(this, android.R.attr.textColorPrimary));

        //Set data to the chart
        mChartView.setData(data);
        mChartView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
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
