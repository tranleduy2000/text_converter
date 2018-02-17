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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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


public class FrequencyActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frequency);
        setupToolbar();
        setTitle(R.string.title_frequency_analysis);


        final FrequencyAnalysis fa = new FrequencyAnalysis();
        final BarChart chart = findViewById(R.id.chart);
        final EditText userString = findViewById(R.id.editText5);
        final Button button2 = findViewById(R.id.button4);

        button2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                hideKeyboard();

                Legend legend = chart.getLegend();
                legend.setEnabled(false);
                chart.setDescription("");
                chart.setScaleYEnabled(false);
                chart.setDoubleTapToZoomEnabled(false);
                chart.setDrawHighlightArrow(true);
                chart.animateY(1000, Easing.EasingOption.Linear);
                YAxis leftAxis = chart.getAxisLeft();
                leftAxis.setEnabled(false);
                YAxis rightAxis = chart.getAxisRight();
                rightAxis.setEnabled(false);
                HashMap<Character, Integer> map = fa.analyze(userString.getText().toString().toUpperCase());
                XAxis xaxis = chart.getXAxis();
                xaxis.setLabelsToSkip(0);
                xaxis.setAvoidFirstLastClipping(true);
                xaxis.setSpaceBetweenLabels(10);
                List<BarEntry> entryList = new ArrayList<BarEntry>();
                ArrayList<String> xVals = new ArrayList<String>();
                Iterator it = map.entrySet().iterator();

                int x = 0;

                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    if (pair.getKey().toString().equals(" ")) {
                        //xVals.add("{space}");
                    } else {
                        xVals.add(pair.getKey().toString());
                        BarEntry e = new BarEntry(Float.parseFloat(pair.getValue().toString()), x);
                        entryList.add(e);
                        x = x + 1;
                    }
                    it.remove(); // avoids a ConcurrentModificationException
                }

                //Create FrequencyPair list for alphabetical sorting
                List<FrequencyPair> fplist = new ArrayList<FrequencyPair>();

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
                List<String> alphaXVals = new ArrayList<String>();
                List<BarEntry> alphaEntryList = new ArrayList<BarEntry>();

                //Iterate and populate sorted lists
                for (int j = 0; j < fplist.size(); j++) {
                    alphaXVals.add(fplist.get(j).getCharacter());
                    alphaEntryList.add(new BarEntry(fplist.get(j).getValue(), j));
                }

                //Create BarDataSet using sorted list
                BarDataSet lds = new BarDataSet(alphaEntryList, "Frequency");
                lds.setColors(new int[]{R.color.accent_material_light}, FrequencyActivity.this);
                BarData data = new BarData(alphaXVals, lds);

                //Set data to the chart
                chart.setData(data);
            }
        });
    }


}
