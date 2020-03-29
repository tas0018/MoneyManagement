package com.example.moneymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private EditText purposeEditText;
    private EditText dateEditText;
    public float currentBalance;
    private TextView currentBalanceLabelTextView;
    private EditText amountEditText;
    private Button addButton;
    private Button subtractButton;
    private TextView historyTextView;
    private TextView transactionTextView;
    public String transactionTextUpdate;
    public String currentBalanceUpdate;
    private TextView currentBalanceTextView;

    DecimalFormat standard = new DecimalFormat("###.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        purposeEditText = (EditText) this.findViewById(R.id.purposeEditText);
        dateEditText = (EditText) this.findViewById(R.id.dateEditText);
        currentBalanceLabelTextView = (TextView) this.findViewById(R.id.currentBalanceLabelTextView);
        amountEditText = (EditText) this.findViewById(R.id.amountEditText);
        addButton = (Button) this.findViewById(R.id.addButton);
        subtractButton = (Button) this.findViewById(R.id.subtractButton);
        historyTextView = (TextView) this.findViewById(R.id.historyTextView);
        transactionTextView = (TextView) this.findViewById(R.id.transactionTextView);
        currentBalanceTextView = (TextView) this.findViewById(R.id.currentBalanceTextView);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = dateEditText.getText().toString();
                String amountString =amountEditText.getText().toString();
                float amountFloat = Float.parseFloat(amountString);
                currentBalance = currentBalance + amountFloat;
                currentBalanceTextView.setText(Float.toString(currentBalance));
                String purpose = purposeEditText.getText().toString();
                String lineToAdd = "Added $" + amountString + " on " + date +" from " + purpose;
                String allLines = transactionTextView.getText().toString();
                String result = lineToAdd +"\n" + allLines;
                transactionTextView.setText(result);
                saveData();
            }
        });

        subtractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = dateEditText.getText().toString();
                String amountString =amountEditText.getText().toString();
                float amountFloat = Float.parseFloat(amountString);
                currentBalance = currentBalance - amountFloat;
                currentBalanceTextView.setText(Float.toString(currentBalance));
                String purpose = purposeEditText.getText().toString();
                String lineToAdd = "Spent $" + amountString + " on " + date +" for " + purpose;
                String allLines = transactionTextView.getText().toString();
                String result = lineToAdd +"\n" + allLines;
                transactionTextView.setText(result);
                saveData();
            }
        });
        loadData();
    }

    public void saveData() {
        SharedPreferences sharedPref = getSharedPreferences("MoneyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("currentBalance", currentBalanceTextView.getText().toString());
        editor.putString("history", transactionTextView.getText().toString());
        editor.apply();

    }

    public void  loadData() {
        SharedPreferences sharedPref = getSharedPreferences("MoneyData", Context.MODE_PRIVATE);
        currentBalance = Float.parseFloat(sharedPref.getString("currentBalance","0"));
        currentBalanceUpdate = sharedPref.getString("currentBalance", "0");
        currentBalanceTextView.setText(currentBalanceUpdate);
        transactionTextUpdate = sharedPref.getString("history", "");
        transactionTextView.setText(transactionTextUpdate);
    }

    //public void updateViews() {
    //    currentBalanceTextView.setText(Float.toString(currentBalance));
    //}
}