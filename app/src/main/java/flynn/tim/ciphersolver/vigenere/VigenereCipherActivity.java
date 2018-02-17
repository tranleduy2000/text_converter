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

package flynn.tim.ciphersolver.vigenere;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.view.WindowManager;
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

public class VigenereCipherActivity extends BaseActivity {

    private ArrayList<Result> resultsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vigenere_cipher);
        setupToolbar();
        setTitle(R.string.title_vigenere_cipher);

        final ListView listView = findViewById(R.id.listView3);
        final EditText ciphertext = findViewById(R.id.edit_input);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        final EditText keyword = findViewById(R.id.editText4);
        final RadioButton encrypt = findViewById(R.id.radioButton3);
        final RadioButton decrypt = findViewById(R.id.radioButton4);
        final Button solve = findViewById(R.id.button3);
        encrypt.setChecked(true);

        ciphertext.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        ciphertext.setFilters(new InputFilter[]{
                new InputFilter() {
                    public CharSequence filter(CharSequence src, int start, int end, Spanned dst, int dstart, int dend) {
                        if (src.equals("")) { // for backspace
                            return src;
                        }
                        if (src.toString().matches("[a-zA-Z ]+")) {
                            return src;
                        }
                        return "";
                    }
                }
        });

        keyword.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        keyword.setFilters(new InputFilter[]{
                new InputFilter() {
                    public CharSequence filter(CharSequence src, int start, int end, Spanned dst, int dstart, int dend) {
                        if (src.equals("")) { // for backspace
                            return src;
                        }
                        if (src.toString().matches("[a-zA-Z ]+")) {
                            return src;
                        }
                        return "";
                    }
                }
        });

        solve.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                hideKeyboard();
                String result;
                resultsList.clear();
                if (ciphertext.getText().toString().trim().equals("")) {
                    resultsList.add(new Result("No ciphertext entered!", false, true));
                } else if (keyword.getText().toString().trim().equals("")) {
                    resultsList.add(new Result("No keyword entered!", false, true));
                } else {
                    if (encrypt.isChecked()) {
                        result = VigenereCipher.encrypt(ciphertext.getText().toString().toUpperCase().trim(), keyword.getText().toString().trim());
                        resultsList.add(new Result(result, true, false));
                    } else {
                        result = VigenereCipher.decrypt(ciphertext.getText().toString().toUpperCase().trim(), keyword.getText().toString().trim());
                        resultsList.add(new Result(result, true, false));
                    }
                }

                final ResultListAdapter adapter = new ResultListAdapter(VigenereCipherActivity.this, R.layout.list_item_caesar, resultsList);
                listView.setAdapter(adapter);

                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                    public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                                   int pos, long id) {
                        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("label", resultsList.get(pos).getResult());
                        clipboard.setPrimaryClip(clip);
                        Toast.makeText(VigenereCipherActivity.this, resultsList.get(pos).getResult().toUpperCase() + " copied to clipboard",
                                Toast.LENGTH_SHORT).show();

                        return true;
                    }
                });
            }
        });

    }

}
