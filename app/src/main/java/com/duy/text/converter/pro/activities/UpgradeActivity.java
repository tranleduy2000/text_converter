/*
 * Copyright (C)  2017-2018 Tran Le Duy
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.duy.text.converter.pro.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.duy.common.activities.ActivityHelper;
import com.duy.common.purchase.InAppPurchaseActivity;
import com.duy.text.converter.R;
import com.duy.text.converter.adapters.PremiumFeatureAdapter;

/**
 * Created by Duy on 25-Dec-17.
 */

public class UpgradeActivity extends InAppPurchaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade);
        ActivityHelper.setupToolbar(this);
        setTitle(getString(R.string.pro_version));
        findViewById(R.id.btn_upgrade).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogUpgrade();
            }
        });
        setResult(RESULT_CANCELED);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new PremiumFeatureAdapter(this));

    }

    @Override
    public void updateUi(boolean premium) {
        super.updateUi(premium);
        if (premium) {
            setResult(RESULT_OK);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_upgrade, menu);
        ;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_upgrade) {
            clickUpgrade();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
