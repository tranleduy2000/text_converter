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

package flynn.tim.ciphersolver.caesar;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.duy.text.converter.R;
import com.duy.text.converter.core.activities.base.BaseActivity;

import java.util.ArrayList;

import flynn.tim.ciphersolver.MyListAdapter;
import flynn.tim.ciphersolver.Result;

public class CaesarCipherActivity extends BaseActivity {

    private ArrayList<Result> resultsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caesar_cipher);


        final EditText userString = findViewById(R.id.editText3);
        final ListView listview = findViewById((R.id.listView2));
        final RadioButton encrypt = findViewById(R.id.radioButton);
        final RadioButton decrypt = findViewById(R.id.radioButton2);
        final Spinner offset = findViewById(R.id.spinner);
        encrypt.setChecked(true);


        final Button button2 = findViewById(R.id.button2);
        final CaesarCipher c = new CaesarCipher();
        button2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                String result;
                resultsList.clear();
                if (userString.getText().toString().equals("")) {
                    resultsList.add(new Result("No string entered!", false, false));
                } else {

                    if (offset.getSelectedItem().toString().equalsIgnoreCase("All")) {
                        if (encrypt.isChecked()) {
                            for (int i = 1; i <= 26; i++) {
                                //Store the value of the encode with the given offset in result

                                result = CaesarCipher.encode(userString.getText().toString().trim(), i);
                                //Display result with some formatting
                                if (i == 1) {
                                    //text.setText("\n" + "Offset " + i + ":\t" + result);
                                    resultsList.add(new Result("Offset " + i + ":\t" + result, false, false));
                                } else {
                                    //text.setText(text.getText() + "\n\n" + "Offset " + i + ":\t" + result);
                                    resultsList.add(new Result("Offset " + i + ":\t" + result, false, false));
                                }
                                //System.out.println("Offset " + i + ":\t" + result);
                                //Loop again with new offset
                            }
                        } else {
                            for (int i = 1; i <= 26; i++) {
                                //Store the value of the encode with the given offset in result

                                result = CaesarCipher.decode(userString.getText().toString().trim(), i);
                                //Display result with some formatting
                                if (i == 1) {
                                    //text.setText("\n" + "Offset " + i + ":\t" + result);
                                    resultsList.add(new Result("Offset " + i + ":\t" + result, false, false));
                                } else {
                                    //text.setText(text.getText() + "\n\n" + "Offset " + i + ":\t" + result);
                                    resultsList.add(new Result("Offset " + i + ":\t" + result, false, false));
                                }
                            }
                        }
                    } else {
                        if (encrypt.isChecked()) {
                            result = CaesarCipher.encode(userString.getText().toString(), Integer.parseInt(offset.getSelectedItem().toString()));
                            resultsList.add(new Result(result, true, false));
                        } else {
                            result = CaesarCipher.decode(userString.getText().toString(), Integer.parseInt(offset.getSelectedItem().toString()));
                            resultsList.add(new Result(result, true, false));
                        }
                    }
                }

                final MyListAdapter adapter = new MyListAdapter(getApplicationContext(), R.layout.list_item_caesar, resultsList);
                listview.setAdapter(adapter);

                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        if (!resultsList.get(position).getEx() && resultsList.get(position).getChecked() == false) {
                            resultsList.get(position).setEx(true);
                        } else if (resultsList.get(position).getEx()) {
                            resultsList.get(position).setEx(false);
                            resultsList.get(position).setChecked(true);
                        } else if (resultsList.get(position).getChecked()) {
                            resultsList.get(position).setChecked(false);
                        }
                        adapter.updateList(resultsList);
                    }
                });

                listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                    public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                                   int pos, long id) {
                        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        String string = resultsList.get(pos).getResult().toUpperCase();

                        if (offset.getSelectedItem().toString().equalsIgnoreCase("All")) {
                            String[] parts = string.split(":");
                            String part1 = parts[0];
                            String part2 = parts[1];
                            ClipData clip = ClipData.newPlainText("label", part2);
                            clipboard.setPrimaryClip(clip);
                            Toast.makeText(getApplicationContext(), part2 + " copied to clipboard",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            ClipData clip = ClipData.newPlainText("label", resultsList.get(pos).getResult().toUpperCase());
                            clipboard.setPrimaryClip(clip);
                            Toast.makeText(getApplicationContext(), resultsList.get(pos).getResult().toUpperCase() + " copied to clipboard",
                                    Toast.LENGTH_SHORT).show();
                        }


                        return true;
                    }
                });
            }
        });
    }


}
