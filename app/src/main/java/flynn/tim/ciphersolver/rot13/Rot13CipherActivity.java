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

package flynn.tim.ciphersolver.rot13;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.duy.text.converter.R;
import com.duy.text.converter.core.activities.base.BaseActivity;

import java.util.ArrayList;

import flynn.tim.ciphersolver.ResultListAdapter;
import flynn.tim.ciphersolver.Result;
import flynn.tim.ciphersolver.caesar.CaesarCipher;


public class Rot13CipherActivity extends BaseActivity {

    private ArrayList<Result> resultsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rot13_cipher);
        setupToolbar();
        setTitle(R.string.title_rot_13_cipher);

        final EditText ciphertext = findViewById(R.id.editText2);
        final Button solve = findViewById(R.id.button3);
        final ListView listview = findViewById(R.id.listView3);
        final RadioButton encrypt = findViewById(R.id.radioButton3);
        encrypt.setChecked(true);

        solve.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                hideKeyboard();
                String result;
                resultsList.clear();
                if (ciphertext.getText().toString().equals("")) {
                    resultsList.add(new Result("No ciphertext entered!", false, true));
                } else {
                    result = CaesarCipher.encode(ciphertext.getText().toString().toUpperCase(), 13);
                    resultsList.add(new Result(result, true, false));
                }


                final ResultListAdapter adapter = new ResultListAdapter(Rot13CipherActivity.this, R.layout.list_item_caesar, resultsList);
                listview.setAdapter(adapter);

                listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                    public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                                   int pos, long id) {
                        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("label", resultsList.get(pos).getResult());
                        clipboard.setPrimaryClip(clip);
                        Toast.makeText(Rot13CipherActivity.this, resultsList.get(pos).getResult().toUpperCase() + " copied to clipboard",
                                Toast.LENGTH_SHORT).show();

                        return true;
                    }
                });
            }
        });
    }
}
