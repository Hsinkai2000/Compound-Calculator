package com.app.teampayup.compoundcalculator;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.appyvet.materialrangebar.RangeBar;
import com.dd.processbutton.FlatButton;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.text.BreakIterator;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    final Context context = this;
    FlatButton buttonCalculate;
    MaterialEditText ETPrincipal, ETInterest;
    TextView durationNumber, finalValue, baseAmount, interest, effectiveAmt, calculationPeriod, pressToList;
    Spinner compoundSpinner, durationSpinner;
    RangeBar durationSlider;
    List<Interest> interestList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pressToList = findViewById(R.id.pressToList);
        baseAmount = findViewById(R.id.baseAmount);
        interest = findViewById(R.id.interestRate);
        effectiveAmt = findViewById(R.id.effectiveAnuual);
        calculationPeriod = findViewById(R.id.calculationPeriod);
        finalValue = findViewById(R.id.finalBalance);
        buttonCalculate = findViewById(R.id.buttonCalculate);
        durationNumber = findViewById(R.id.durationNumber);
        ETPrincipal = findViewById(R.id.ETPrincipal);
        ETInterest = findViewById(R.id.ETInterest);
        durationSlider = findViewById(R.id.durationSlider);
        //SET COMPOUND SPINNER & INTEREST SPINNER
        compoundSpinner = findViewById(R.id.compoundSpinner);
        durationSpinner = findViewById(R.id.durationSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> compoundAdapter = ArrayAdapter.createFromResource(this,
                R.array.compoundInterval, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> durationAdapter = ArrayAdapter.createFromResource(this,
                R.array.duration, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        durationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        compoundAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        durationSpinner.setAdapter(durationAdapter);
        compoundSpinner.setAdapter(compoundAdapter);



        durationSlider.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex,
                                              int rightPinIndex, String leftPinValue, String rightPinValue) {
                durationNumber.setText(rightPinValue);
            }

        });
        //when calculate is hit
        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateCompoundInterest();
            }
        });

        //when duration number is pressed
        durationNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // custom dialog
                final android.app.Dialog dialog = new android.app.Dialog(context);
                dialog.setContentView(R.layout.custom_alert);
                dialog.setTitle("Change Duration");
                FlatButton buttonAccept = dialog.findViewById(R.id.button_accept);
                FlatButton buttonReject = dialog.findViewById(R.id.button_cancel);
                // if button is clicked, close the custom dialog
                buttonReject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                buttonAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MaterialEditText newDuration = dialog.findViewById(R.id.ETDuration);
                        durationNumber.setText(newDuration.getText());
                        durationSlider.setSeekPinByValue(Float.valueOf(String.valueOf(newDuration.getText())));
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });

        pressToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToTables = new Intent(getApplicationContext(), TableView.class);
                goToTables.putExtra("interestList", (Serializable) interestList);
                goToTables.putExtra("durationInterval", (String) durationNumber.getText());
                goToTables.putExtra("compoundInterval", (String) compoundSpinner.getSelectedItem());
                startActivity(goToTables);

            }
        });

    }

    protected void calculateCompoundInterest() {
        interestList = new ArrayList<Interest>();
        Double finalBalance = 0.00;
        Double rate = 0.00;
        Double power = 0.0;
        if (!String.valueOf(ETPrincipal.getText()).isEmpty() || !String.valueOf(ETInterest.getText()).isEmpty()){
            //get all values
            Double principal = Double.valueOf(String.valueOf(ETPrincipal.getText()));
            Double interestRate = Double.valueOf(String.valueOf(ETInterest.getText()));
            String compoundInterval = String.valueOf(compoundSpinner.getSelectedItem());
            String durationRate = String.valueOf(durationSpinner.getSelectedItem());
            double duration = Double.valueOf(String.valueOf(durationNumber.getText()));

            Log.d("TESTINGNUMBERS", "Princiapl :" + String.valueOf(principal));
            Log.d("TESTINGNUMBERS", "Interest Rate :" + String.valueOf(interestRate));
            Log.d("TESTINGNUMBERS", "compound Interval : " + String.valueOf(compoundInterval));
            Log.d("TESTINGNUMBERS", "duration Rate :" + String.valueOf(durationRate));
            Log.d("TESTINGNUMBERS", "duration :" + String.valueOf(duration));


            interestRate = interestRate / 100;
            if (durationRate.equals("Years")) {
                switch (compoundInterval) {
                    case "Yearly":
                        rate = interestRate;
                        power = duration;
                        break;
                    case "Half-Yearly":
                        rate = interestRate / 2;
                        power = duration * 2;
                        break;
                    case "Quarterly":
                        rate = interestRate / 4;
                        power = duration * 4;
                        break;
                    case "Monthly":
                        rate = interestRate / 12;
                        power = duration * 12;
                        break;
                    case "Daily":
                        rate = interestRate / 365;
                        power = duration * 365;
                        break;
                }
            } else if (durationRate.equals("Months")) {
                switch (compoundInterval) {
                    case "Yearly":
                        rate = interestRate;
                        power = duration / 12;
                        break;
                    case "Half-Yearly":
                        rate = interestRate / 2;
                        power = duration * 2 / 12;
                        break;
                    case "Quarterly":
                        rate = interestRate / 4;
                        power = duration * 4 / 12;
                        break;
                    case "Monthly":
                        rate = interestRate / 12;
                        power = duration * 12 / 12;
                        break;
                    case "Daily":
                        rate = interestRate / 365;
                        power = duration * 365 / 12;
                        break;
                }
            }
            DecimalFormat newPrincipalFormat = new DecimalFormat("0.00");
            String newPrincipal = newPrincipalFormat.format(principal);
            baseAmount.setText("$" + String.valueOf(newPrincipal));
            double totalInterest = 0;
            double each = 0;
            Interest interest1 = new Interest(each, totalInterest, principal);
            interestList.add(interest1);
            for (int i = 1; i <= power; i++) {

                principal = principal * (1 + rate);
                Log.d("CHECKER123", String.valueOf(principal));
                each = principal * rate;
                totalInterest += each;
                Interest interest2 = new Interest(each, totalInterest, principal);
                interestList.add(interest2);
                Log.d("CHECKER123", String.valueOf(interestList));
            }

            DecimalFormat df = new DecimalFormat("0.00");
            String newfinalBalance = df.format(principal);
            finalValue.setText("");
            finalValue.setText("$" + newfinalBalance);
            interest.setText(String.format("%.2f", interestRate * 100) + "%");
            effectiveAmt.setText(String.format("%.2f", Math.exp(interestRate * 100) - 1));

            //show proceed to list
            pressToList.setVisibility(View.VISIBLE);

            //DecimalFormat df2 = new DecimalFormat("0##");
            //String newDuration = df.format(duration);
            calculationPeriod.setText(String.valueOf(durationNumber.getText()) + "\t" + String.valueOf(durationSpinner.getSelectedItem()));
        }
    }
}
