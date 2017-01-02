package com.jiazhou.homeauto.homeauto.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jiazhou.homeauto.homeauto.R;
import com.jiazhou.homeauto.homeauto.utility.ControlPraser;

/**
 * Created by lijiazhou on 15/11/16.
 */
public class CalculatorDialog extends Dialog {

    Button acButton;
    Button minusButton;
    Button reminderButton;
    Button multipleButton;
    Button divideButton;
    Button plusButton;
    Button reduceButton;
    Button equalButton;

    Button num1;
    Button num2;
    Button num3;
    Button num4;
    Button num5;
    Button num6;
    Button num7;
    Button num8;
    Button num9;
    Button num0;
    Button numDot;

    TextView calnum;
    View exit;

    Double formerNum;
    String calOperator;
    boolean operatorClicked;
    boolean resulted;
    boolean hasDot;

    public  CalculatorDialog(Context context) {
        super(context, R.style.Dialog);
        setContentView(R.layout.dialog_calculator);
        setCancelable(false);
        acButton = ControlPraser.PraserControl(this, R.id.buttonClear);
        minusButton = ControlPraser.PraserControl(this, R.id.buttonMinus);
        reminderButton = ControlPraser.PraserControl(this, R.id.reminder);
        multipleButton = ControlPraser.PraserControl(this, R.id.buttonMultiple);
        divideButton = ControlPraser.PraserControl(this, R.id.buttonDivide);
        plusButton = ControlPraser.PraserControl(this, R.id.buttonPlus);
        reduceButton = ControlPraser.PraserControl(this, R.id.buttomReduce);
        equalButton = ControlPraser.PraserControl(this, R.id.buttonEqual);

        num0 = ControlPraser.PraserControl(this, R.id.num0);
        num1 = ControlPraser.PraserControl(this, R.id.num1);
        num2 = ControlPraser.PraserControl(this, R.id.num2);
        num3 = ControlPraser.PraserControl(this, R.id.num4);
        num5 = ControlPraser.PraserControl(this, R.id.num5);
        num6 = ControlPraser.PraserControl(this, R.id.num6);
        num7 = ControlPraser.PraserControl(this, R.id.num7);
        num8 = ControlPraser.PraserControl(this, R.id.num8);
        num9 = ControlPraser.PraserControl(this, R.id.num9);
        num4 = ControlPraser.PraserControl(this, R.id.num4);
        numDot = ControlPraser.PraserControl(this, R.id.numDot);

        calnum = ControlPraser.PraserControl(this, R.id.calculatorNumber);

        exit = ControlPraser.PraserControl(this, R.id.calculatorExit);

        acButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hasDot = false;
                operatorClicked = false;
                resulted = false;
                calnum.setText("0");
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        final View.OnClickListener operatorListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null == calOperator || calOperator.isEmpty()){
                    calOperator = ((Button)v).getText().toString().trim();
                }

                formerNum = Double.valueOf(calnum.getText().toString().trim());
                operatorClicked = true;
            }
        };

        View.OnClickListener numListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(resulted){
                    calnum.setText("0");
                    resulted = !resulted;
                }

                if(operatorClicked){
                    calnum.setText("0");
                    operatorClicked = !operatorClicked;
                }

                if(calnum.getText().toString().equals("0")){
                   calnum.setText("");
                   if(((Button)v).getText().toString().equals(".")) {
                       if(hasDot)
                           return;
                       hasDot = !hasDot;
                       calnum.setText("0.");
                   }
                }

                String num = calnum.getText().toString() + ((Button)v).getText().toString();
                calnum.setText(num);
            }
        };

        reduceButton.setOnClickListener(operatorListener);
        plusButton.setOnClickListener(operatorListener);
        multipleButton.setOnClickListener(operatorListener);
        divideButton.setOnClickListener(operatorListener);

        num1.setOnClickListener(numListener);
        num2.setOnClickListener(numListener);
        num3.setOnClickListener(numListener);
        num4.setOnClickListener(numListener);
        num5.setOnClickListener(numListener);
        num6.setOnClickListener(numListener);
        num7.setOnClickListener(numListener);
        num8.setOnClickListener(numListener);
        num9.setOnClickListener(numListener);
        num0.setOnClickListener(numListener);
        numDot.setOnClickListener(numListener);

        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double number = Double.valueOf(calnum.getText().toString());
                if(number == 0)
                    return;
                if(number > 0)
                    calnum.setText("-" + calnum.getText());
                else {
                    calnum.setText(calnum.getText().toString().substring(1));
                }
            }
        });

        reminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double number = Double.valueOf(calnum.getText().toString())/100;
                calnum.setText(String.valueOf(number));
            }
        });


        equalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double result = null;
                Double currentNumber = Double.valueOf(calnum.getText().toString());
                if(formerNum != null){
                    hasDot = false;
                    switch (calOperator){
                        case "+": result = formerNum + currentNumber; break;
                        case "-": result = formerNum - currentNumber; break;
                        case "*": result = formerNum * currentNumber; break;
                        case "/": result = formerNum / currentNumber; break;
                    }
                }

                if(result != null) {
                    calnum.setText(result.toString());
                    formerNum = null;
                    resulted = true;
                }
            }
        });
    }
}
