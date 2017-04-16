package teach.duy.com.texttool.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import teach.duy.com.texttool.view.BaseEditText;

import teach.duy.com.texttool.BuildConfig;
import teach.duy.com.texttool.R;
import teach.duy.com.texttool.utils.ASCIITool;
import teach.duy.com.texttool.utils.BinaryTool;
import teach.duy.com.texttool.utils.ClipboardManager;
import teach.duy.com.texttool.utils.HexTool;
import teach.duy.com.texttool.utils.OctalTool;
import teach.duy.com.texttool.utils.ReverserTool;
import teach.duy.com.texttool.utils.UpperLowerTool;
import teach.duy.com.texttool.utils.UpsideDownTool;

import static teach.duy.com.texttool.fragment.TextFragment.TextType.ASCII;
import static teach.duy.com.texttool.fragment.TextFragment.TextType.BINARY;
import static teach.duy.com.texttool.fragment.TextFragment.TextType.HEX;
import static teach.duy.com.texttool.fragment.TextFragment.TextType.KEY_TEXT;
import static teach.duy.com.texttool.fragment.TextFragment.TextType.LOWER;
import static teach.duy.com.texttool.fragment.TextFragment.TextType.OCTAL;
import static teach.duy.com.texttool.fragment.TextFragment.TextType.REVERSER;
import static teach.duy.com.texttool.fragment.TextFragment.TextType.UPPER;
import static teach.duy.com.texttool.fragment.TextFragment.TextType.UPSIDE_DOWNSIDE;

/**
 * TextFragment
 * Created by DUy on 06-Feb-17.
 */

public class TextFragment extends Fragment {
    private static final String TAG = TextFragment.class.getSimpleName();
    private static final String KEY = TextFragment.class.getSimpleName();
    private View mRootView;
    private Context mContext;
    private BaseEditText mInput, mOutput;
    private Button btnTo, btnPre;
    private View imgShareIn, imgShareOut, imgCopyIn, imgCopyOut;

    public static TextFragment newInstance(int ID) {
        TextFragment fragment = new TextFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_TEXT, ID);
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
        mRootView = inflater.inflate(R.layout.content_convert_text, container, false);
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mInput = (BaseEditText) mRootView.findViewById(R.id.edit_input);
        mOutput = (BaseEditText) mRootView.findViewById(R.id.edit_output);

        mInput.addTextChangedListener(new TextWatcher() {
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
        });
        mOutput.addTextChangedListener(new TextWatcher() {
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
        });

        imgCopyIn = mRootView.findViewById(R.id.img_copy);
        imgShareIn = mRootView.findViewById(R.id.img_share);

        imgShareIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, mInput.getText().toString());
                intent.setType("text/plain");
                getActivity().startActivity(intent);
            }
        });
        imgCopyIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager.setClipboard(mContext, mInput.getText().toString());
            }
        });
        imgCopyOut = mRootView.findViewById(R.id.img_copy_out);
        imgShareOut = mRootView.findViewById(R.id.img_share_out);

        imgShareOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, mOutput.getText().toString());
                intent.setType("text/plain");
                getActivity().startActivity(intent);
            }
        });
        imgCopyOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager.setClipboard(mContext, mOutput.getText().toString());
            }
        });
        mInput.setHint("Enter here ...");
        convert(true);

        if (BuildConfig.DEBUG) {
            mInput.setText("abcdefghijklmnopqrstuvwxyz'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
        }
    }

    private void convert(boolean to) {
        Log.d(TAG, "convert: ");
        int key = getArguments().getInt(KEY_TEXT);
        String inp = mInput.getText().toString();
//        if (inp.isEmpty()) inp = mInput.getHint().toString();
        String out = mOutput.getText().toString();
        switch (key) {
            case ASCII:
                if (to) {
                    mOutput.setText(ASCIITool.textToAscii(inp));
                } else {
                    mInput.setText(ASCIITool.asciiToText(out));
                }
                break;
            case OCTAL:
                if (to) {
                    mOutput.setText(OctalTool.textToOctal(inp));
                } else {
                    mInput.setText(OctalTool.octalToText(out));
                }
                break;
            case BINARY:
                if (to) {
                    mOutput.setText(BinaryTool.textToBinary(inp));
                } else {
                    mInput.setText(BinaryTool.binaryToText(out));
                }
                break;
            case HEX:
                if (to) {
                    mOutput.setText(HexTool.textToHex(inp));
                } else {
                    mInput.setText(HexTool.hexToTex(out));
                }
                break;
            case UPPER:
                if (to) {
                    mOutput.setText(UpperLowerTool.upperText(inp));
                } else {
                    mInput.setText(UpperLowerTool.lowerText(out));
                }
                break;
            case LOWER:
                if (to) {
                    mOutput.setText(UpperLowerTool.lowerText(inp));
                } else {
                    mInput.setText(UpperLowerTool.upperText(out));
                }
                break;
            case REVERSER:
                if (to) {
                    mOutput.setText(ReverserTool.reverseText(inp));
                } else {
                    mInput.setText(ReverserTool.reverseText(out));
                }
                break;
            case UPSIDE_DOWNSIDE:
                if (to) {
                    mOutput.setText(UpsideDownTool.upsideDownText(inp));
                } else {
                    mInput.setText(UpsideDownTool.upsideDownText(out));
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
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        mInput.setText(sharedPreferences.getString(KEY + getArguments().getInt(KEY_TEXT), ""));
    }

    public static final class TextType {
        static final int OCTAL = 7;
        static final String KEY_TEXT = "KEY_TEXT";
        static final int BINARY = 1;
        static final int HEX = 2;
        static final int UPPER = 3;
        static final int LOWER = 8;
        static final int ASCII = 4;
        static final int REVERSER = 5;
        static final int UPSIDE_DOWNSIDE = 6;
    }
}
