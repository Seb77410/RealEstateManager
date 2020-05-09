package com.openclassrooms.realestatemanager.ui.activities.LoanCalculator;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.openclassrooms.realestatemanager.R;

import java.util.Objects;

public class LoanCalculatorActivity extends AppCompatActivity {

    //----------------------------------------------------------------------------------------------
    // Data
    //----------------------------------------------------------------------------------------------
    TextInputLayout propertyPrice;
    TextInputLayout personalContribution;
    TextInputLayout loanPeriod;
    TextInputLayout loanRate;
    int price;
    int contribution;
    int periodYear;
    double rate;
    int iMonthlyPayment;
    int iLoanInterest;
    ConstraintLayout resultContainer;
    TextView vMonthlyPayment;
    TextView vTotalInterest;

    //----------------------------------------------------------------------------------------------
    // On Create
    //----------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_calculator);

        propertyPrice = findViewById(R.id.loan_calculator_price);
        personalContribution = findViewById(R.id.loan_calculator_contribution);
        loanPeriod = findViewById(R.id.loan_calculator_period);
        loanRate = findViewById(R.id.loan_calculator_rate);
        resultContainer = findViewById(R.id.loan_calculator_result_container);
        vMonthlyPayment = findViewById(R.id.loan_calculator_result_monthly_payment_content);
        vTotalInterest = findViewById(R.id.loan_calculator_result_interest_content);

        this.configureToolbar();
        resultContainer.setVisibility(View.GONE);
    }

    //----------------------------------------------------------------------------------------------
    // Configure view
    //----------------------------------------------------------------------------------------------
    private void configureToolbar() {
        Toolbar mToolbar = findViewById(R.id.loan_calculator_activity_toolbar);
        setSupportActionBar(mToolbar);
        // Set back stack
        Drawable upArrow = ResourcesCompat.getDrawable(this.getResources(), R.drawable.ic_arrow_back_white_24dp, null);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(upArrow);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    //----------------------------------------------------------------------------------------------
    // Calculate and show loan result
    //----------------------------------------------------------------------------------------------
    public void showResult(View view){
        if (checkForEditTextError()) {
            this.getData();
            this.calculateLoan();
            this.showResult();
        }
    }

    private void getData(){
        price = Integer.parseInt(String.valueOf(Objects.requireNonNull(propertyPrice.getEditText()).getText()));
        contribution = Integer.parseInt(String.valueOf(Objects.requireNonNull(personalContribution.getEditText()).getText()));
        periodYear = Integer.parseInt(String.valueOf(Objects.requireNonNull(loanPeriod.getEditText()).getText()));
        rate = Double.parseDouble(String.valueOf(Objects.requireNonNull(loanRate.getEditText()).getText()));
        Log.e("Loan Calculator", "price = " + price + "$, contribution = " + contribution + "$, period = " + periodYear + "years, rate = " + rate + "%");
    }

    private void calculateLoan() {
        price = price - contribution;
        rate = (float) rate/100;

        iMonthlyPayment = (int) Math.round((((price * ((float)rate/12) * Math.pow(1+((float)rate/12), periodYear*12)))) / (Math.pow((1 + ((float)rate/12)), periodYear*12)-1));
        Log.e("Loan Calculator", "mensualité  = " + iMonthlyPayment);

        iLoanInterest = Math.round(12 * periodYear * iMonthlyPayment - price);
        Log.e("Loan Calculator", "interets  = " + iLoanInterest);
    }

    private void showResult(){
        resultContainer.setVisibility(View.VISIBLE);
        vMonthlyPayment.setText(String.format("%s %s", iMonthlyPayment, " $"));
        vTotalInterest.setText(String.format("%s %s", iLoanInterest, " $"));
    }

    //----------------------------------------------------------------------------------------------
    // Check for edit text content error
    //----------------------------------------------------------------------------------------------

    private boolean checkForEditTextError(){
        editTextContentIsOK(propertyPrice);
        editTextContentIsOK(personalContribution);
        editTextContentIsOK(loanPeriod);
        editTextContentIsOK(loanRate);

        return editTextContentIsOK(propertyPrice) && editTextContentIsOK(personalContribution) && editTextContentIsOK(loanPeriod) &&
                editTextContentIsOK(loanRate);
    }

    private boolean editTextContentIsOK(TextInputLayout textInputLayout){
        String value = String.valueOf(Objects.requireNonNull(textInputLayout.getEditText()).getText());
        if (value.equals("")){
            textInputLayout.setError("Required");
            return false;
        }
        else{
            textInputLayout.setError(null);
            return true;
        }
    }
}
