/*
 * Copyright (c) 2017 by Tran Le Duy
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

package com.duy.sharedcode.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.duy.sharedcode.ClipboardUtil;
import com.duy.sharedcode.codec.ASCIITool;
import com.duy.sharedcode.codec.Base32Tool;
import com.duy.sharedcode.codec.Base64Tool;
import com.duy.sharedcode.codec.BinaryTool;
import com.duy.sharedcode.codec.EncodeMethod;
import com.duy.sharedcode.codec.HexTool;
import com.duy.sharedcode.codec.MorseTool;
import com.duy.sharedcode.codec.OctalTool;
import com.duy.sharedcode.codec.RandomCaseTool;
import com.duy.sharedcode.codec.ReverserTool;
import com.duy.sharedcode.codec.SubScriptText;
import com.duy.sharedcode.codec.SupScriptText;
import com.duy.sharedcode.codec.URLTool;
import com.duy.sharedcode.codec.UpperLowerTool;
import com.duy.sharedcode.codec.UpsideDownTool;
import com.duy.sharedcode.codec.ZalgoBigTool;
import com.duy.sharedcode.codec.ZalgoMiniTool;
import com.duy.sharedcode.codec.ZalgoNormalTool;
import com.duy.sharedcode.view.BaseEditText;
import com.duy.textconverter.sharedcode.R;


/**
 * TextFragment
 * Created by DUy on 06-Feb-17.
 */

public class CodecFragment extends Fragment {
    private static final String TAG = CodecFragment.class.getSimpleName();
    private static final String KEY = CodecFragment.class.getSimpleName();
    private static final String KEY_TEXT = "KEY_TEXT";
    private View mRootView;
    private Context mContext;
    private BaseEditText mInput, mOutput;
    private Spinner mChoose;
    private TextWatcher mOutputWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (mOutput.isFocused()) convert(false);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private TextWatcher mInputWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (mInput.isFocused()) convert(true);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private View imgShareIn, imgShareOut, imgCopyIn, imgCopyOut;

    public static CodecFragment newInstance(String init) {
        CodecFragment fragment = new CodecFragment();
        Bundle bundle = new Bundle();
        if (init != null) {
            bundle.putString(Intent.EXTRA_TEXT, init);
        }
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_codec, container, false);
        return mRootView;
    }


    @Override
    public void onDestroyView() {

        mInput.removeTextChangedListener(mInputWatcher);
        mOutput.removeTextChangedListener(mOutputWatcher);

        super.onDestroyView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mInput = mRootView.findViewById(R.id.edit_input);
        mOutput = mRootView.findViewById(R.id.edit_output);
        mInput.addTextChangedListener(mInputWatcher);
        mOutput.addTextChangedListener(mOutputWatcher);

        mChoose = mRootView.findViewById(R.id.spinner_choose);

        imgCopyIn = mRootView.findViewById(R.id.img_copy);
        imgShareIn = mRootView.findViewById(R.id.img_share);

        imgShareIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doShareText(mInput);
            }
        });

        imgCopyIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardUtil.setClipboard(mContext, mInput.getText().toString());
            }
        });

        imgCopyOut = mRootView.findViewById(R.id.img_copy_out);
        imgShareOut = mRootView.findViewById(R.id.img_share_out);

        imgShareOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doShareText(mOutput);
            }
        });
        imgCopyOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardUtil.setClipboard(mContext, mOutput.getText().toString());
            }
        });

        String[] data = getResources().getStringArray(R.array.codec_methods);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, data);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        mChoose.setAdapter(adapter);
        mChoose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (mInput.hasFocus()) {
                    convert(true);
                } else {
                    convert(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void doShareText(EditText editText) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, editText.getText().toString());
        intent.setType("text/plain");
        getActivity().startActivity(intent);
    }

    private void convert(boolean to) {
        int index = mChoose.getSelectedItemPosition();
        EncodeMethod encodeMethod = EncodeMethod.values()[index];
        String inp = mInput.getText().toString();
        String out = mOutput.getText().toString();
        switch (encodeMethod) {
            case ASCII:
                if (to) mOutput.setText(new ASCIITool().encode(inp));
                else mInput.setText(new ASCIITool().decode(out));
                break;
            case OCTAL:
                if (to) mOutput.setText(new OctalTool().encode(inp));
                else mInput.setText(new OctalTool().decode(out));
                break;
            case BINARY:
                if (to) mOutput.setText(new BinaryTool().encode(inp));
                else mInput.setText(new BinaryTool().decode(out));
                break;
            case HEX:
                if (to) mOutput.setText(new HexTool().encode(inp));
                else mInput.setText(new HexTool().decode(out));
                break;
            case UPPER:
                if (to) mOutput.setText(UpperLowerTool.upperText(inp));
                else mInput.setText(UpperLowerTool.lowerText(out));
                break;
            case LOWER:
                if (to) mOutput.setText(UpperLowerTool.lowerText(inp));
                else mInput.setText(UpperLowerTool.upperText(out));
                break;
            case REVERSER:
                if (to) mOutput.setText(ReverserTool.reverseText(inp));
                else mInput.setText(ReverserTool.reverseText(out));
                break;
            case UPSIDE_DOWNSIDE:
                if (to) mOutput.setText(UpsideDownTool.textToUpsideDown(inp));
                else mInput.setText(UpsideDownTool.upsideDownToText(out));
                break;
            case SUPPER_SCRIPT:
                if (to) mOutput.setText(new SupScriptText().encode(inp));
                else mInput.setText(new SupScriptText().decode(out));
                break;
            case SUB_SCRIPT:
                if (to) mOutput.setText(new SubScriptText().encode(inp));
                else mInput.setText(new SubScriptText().decode(out));
                break;
            case MORSE_CODE:
                if (to) mOutput.setText(new MorseTool().encode(inp));
                else mInput.setText(new MorseTool().decode(out));
                break;

            case ZALGO_MINI:
                if (to) mOutput.setText(new ZalgoMiniTool().encode(inp));
                else {
                    Toast.makeText(mContext, "Don't support decode zalgo", Toast.LENGTH_SHORT).show();
                }
                break;
            case ZALGO_NORMAL:
                if (to) mOutput.setText(new ZalgoNormalTool().encode(inp));
                else {
                    Toast.makeText(mContext, "Don't support decode zalgo", Toast.LENGTH_SHORT).show();
                }
                break;
            case ZALGO_BIG:
                if (to) mOutput.setText(new ZalgoBigTool().encode(inp));
                else {
                    Toast.makeText(mContext, "Don't support decode zalgo", Toast.LENGTH_SHORT).show();
                }
                break;
            case BASE32:
                if (to) mOutput.setText(new Base32Tool().encode(inp));
                else mInput.setText(new Base32Tool().decode(out));
                break;
            case BASE_64:
                if (to) mOutput.setText(new Base64Tool().encode(inp));
                else mInput.setText(new Base64Tool().decode(out));
                break;
            case URL:
                if (to) mOutput.setText(new URLTool().encode(inp));
                else mInput.setText(new URLTool().decode(out));
                break;
            case RANDOM_CASE:
                if (to) mOutput.setText(new RandomCaseTool().encode(inp));
                else {
                    Toast.makeText(mContext, "You can't decode random case", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        //reset cursor
        mInput.setSelection(mInput.getText().toString().length());
        mOutput.setSelection(mOutput.getText().toString().length());
    }

    @Override
    public void onPause() {
        super.onPause();
        save();
    }

    @Override
    public void onResume() {
        super.onResume();
        restore();
    }

    public void save() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        sharedPreferences.edit().putString(KEY + getArguments().getInt(KEY_TEXT), mInput.getText().toString()).apply();
    }

    public void restore() {
        String text = getArguments().getString(Intent.EXTRA_TEXT, "");
        if (!text.isEmpty()) {
            mInput.setText(text);
        } else {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
            mInput.setText(sharedPreferences.getString(KEY + getArguments().getInt(KEY_TEXT), ""));
        }
        convert(true);
    }


}
