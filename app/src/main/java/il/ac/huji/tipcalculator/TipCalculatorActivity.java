package il.ac.huji.tipcalculator;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TipCalculatorActivity extends AppCompatActivity {

    EditText mEditText;
    TextView mTextView;
    CheckBox mCheckBox;

    double bill = 0;
    double percentage = 12;
    double tip = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_calculator);

        mEditText = (EditText)findViewById(R.id.editText);
        mEditText.setFilters(new InputFilter[] {new NumberOfDigits(4,2)});
        final Button mButton = (Button)findViewById(R.id.button);
        mTextView = (TextView)findViewById(R.id.textView2);

        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calculate();
            }
        });
    }

    private void calculate() {
        bill = Double.parseDouble(mEditText.getText().toString());
        tip = (bill * percentage) / 100;
        mCheckBox = (CheckBox)findViewById(R.id.checkBox);
        boolean round = mCheckBox.isChecked();
        if (round) {
            tip = (int)Math.ceil(tip);
        }
        mTextView.setText("Tip:  $"+String.format("%.2f", tip));
    }

    public class NumberOfDigits implements InputFilter {

        Pattern mPattern;

        public NumberOfDigits(int beforeDecimalDigits,int afterDecimalDigits) {
            mPattern=Pattern.compile("^[0-9]{0," +
                    (beforeDecimalDigits-1) +
                    "}+((\\.[0-9]{0," +
                    (afterDecimalDigits-1) +
                    "})?)||(\\.)?");
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            Matcher matcher=mPattern.matcher(dest);
            if(!matcher.matches())
                return "";
            return null;
        }

    }

}
