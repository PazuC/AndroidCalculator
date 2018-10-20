package com.example.pazu.myapplication2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private TextView tv_show;
    private String display = "";
    private String value1 = "", value2 = "";
    private String result = "";
    private boolean isOperator = false;
    private boolean afterEqual = false;
    private String currentOperator = "";
    private String _result;
    private String _value2;
    private String _currentOperator;
    private boolean value1Dot = false;
    private boolean value2Dot = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        tv_show = (TextView) findViewById(R.id.tv_show);
    }

    protected void onClickNumber(View v) {
        if (result != "") {
            originalState();
        }

        if (afterEqual == true && isOperator == false) {
            originalState();
        }

        Button b = (Button) v;
        display += b.getText();
        tv_show.setText(display);

        if (!isOperator) {
            value1 += b.getText();
        }

        if (isOperator) {
            value2 += b.getText();
        }
    }

    protected void onClickOperator(View v) {
        Button b = (Button) v;
        afterEqual = false;
        if (value2.equals(".")) {
            value2 = "";
            display = value1 + currentOperator;
            tv_show.setText(display);
            value2Dot = false;
            return;
        }
        if (display == "") {
            return;
        }
        if (value1.equals(".")) {
            return;
        }
        if (isOperator) {

            if (value2 != "") { //if there exist a complete formulae, calculate
                result = calculate(value1, value2, currentOperator);
                value2 = "";
                display = result;
                display += b.getText();
                tv_show.setText(display);
                value1 = result;
                isOperator = true;
                value2Dot = false;
                currentOperator = b.getText().toString();
                result = "";
            }
            if (value2 == "") { //replace the old operator
                display = display.replace(display.charAt(display.length() - 1), b.getText().charAt(0));
                tv_show.setText(display);
                currentOperator = b.getText().toString();
                value2Dot = false;
            }
        }

        if (!isOperator) {
            display += b.getText();
            tv_show.setText(display);
            isOperator = true;
            currentOperator = b.getText().toString();
        }
    }

    protected void originalState() {
        result = "";
        display = "";
        tv_show.setText(display);
        value1 = "";
        value2 = "";
        isOperator = false;
        afterEqual = false;
        currentOperator = "";
        value1Dot = false;
        value2Dot = false;
    }

    protected void onClickClear(View v) {
        if (isOperator && value2 != "") {
            display = value1 + currentOperator;
            tv_show.setText(display);
            value2 = "";
            value2Dot = false;
        } else {
            originalState();
        }
    }

    protected void onClickEqual(View v) {
        if (afterEqual) {

            value1 = calculate(value1, _value2, _currentOperator);
            display = value1;
            tv_show.setText(display);
            return;
        }

        if (!isOperator) {
            return;
        }

        if (value2 == "") {
            return;
        }

        if (value2.equals(".")) {
            value2 = "";
            display = value1 + currentOperator;
            tv_show.setText(display);
            value2Dot = false;
            return;
        }

        String _result = calculate(value1, value2, currentOperator);
        _value2 = value2;
        _currentOperator = currentOperator;
        originalState();
        afterEqual = true;
        value1 = _result;
        display = value1;
        tv_show.setText(display);
    }

    private String calculate(String a, String b, String op) {
        switch (op) {
            case "＋":
                if ((Double.valueOf(a) + Double.valueOf(b)) % 1 == 0) {
                    return "" + (int) (Double.valueOf(a) + Double.valueOf(b));
                }
                return Double.toString(Double.valueOf(a) + Double.valueOf(b));
            case "－":
                if ((Double.valueOf(a) - Double.valueOf(b)) % 1 == 0) {
                    return "" + (int) (Double.valueOf(a) - Double.valueOf(b));
                }
                return Double.toString(Double.valueOf(a) - Double.valueOf(b));
            case "×":
                if ((Double.valueOf(a) * Double.valueOf(b)) % 1 == 0) {
                    return "" + (int) (Double.valueOf(a) * Double.valueOf(b));
                }
                return Double.toString(Double.valueOf(a) * Double.valueOf(b));
            case "÷":
                try {
                    if ((Double.valueOf(a) / Double.valueOf(b)) % 1 == 0) {
                        return "" + (int) (Double.valueOf(a) / Double.valueOf(b));
                    }
                    return Double.toString(Double.valueOf(a) / Double.valueOf(b));
                } catch (Exception e) {
                    Log.d("Calc", e.getMessage());
                }
            default:
                return "-1";
        }
    }

    protected void onClickDot(View v) {
        Button b = (Button) v;
        if (afterEqual) {
            originalState();
        }

        if (!isOperator && !value1Dot) {
            value1 += b.getText();
            display += b.getText();
            tv_show.setText(display);
            value1Dot = true;
        }
        if (isOperator && !value2Dot) {
            value2 += b.getText();
            display += b.getText();
            tv_show.setText(display);
            value2Dot = true;
        }
    }
}