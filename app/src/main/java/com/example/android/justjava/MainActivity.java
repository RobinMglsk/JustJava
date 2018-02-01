package com.example.android.justjava;

import android.content.Intent;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;
    double price = 4.95;
    double priceWhippedCream = 1.00;
    double priceChocolate = 2.00;
    String[] emailAddress = {"justjava@mglsk.be"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayQuantiy(quantity);
        displayPrice(quantity);
    }


    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        boolean[] extraOptions;
        extraOptions = new boolean[2];
        extraOptions[0] = false; //WhippedCream
        extraOptions[1] = false; //Chocolate

        CheckBox hasWhippedCream = (CheckBox) findViewById(R.id.WhippedCream);
        extraOptions[0] = hasWhippedCream.isChecked();

        CheckBox hasChocolate = (CheckBox) findViewById(R.id.Chocolate);
        extraOptions[1] = hasChocolate.isChecked();

        // Calculate price
        double totalPrice = calculatePrice(quantity, extraOptions);
        Log.v("MainActivity", "The price is " + totalPrice);


        // Get Name
        EditText nameField = (EditText) findViewById(R.id.nameField);
        String name = nameField.getText().toString();

        if (name.length() == 0) {
            String error = getResources().getString(R.string.error_name);
            displayToast(error);
        } else {
            String msg = createOrderSummary(totalPrice, quantity, name, extraOptions);
            String subject = getResources().getString(R.string.email_subject) + name;
            sendEmail(msg, subject);
        }

        //displayMessage(msg);

    }

    /***
     *  This method creates an order summary
     *  @param totalPrice The total price
     *  @param quantity The quantity of cups
     *  @param name Customer name
     *  @param options Extra options
     *
     *  @return String order summary
     */
    private String createOrderSummary(double totalPrice, int quantity, String name, boolean[] options) {

        String msg = getResources().getString(R.string.nameField)+": " + name + "\n";
        msg += getResources().getString(R.string.form_quantity)+": " + quantity + "\n";
        if (options[0]) {
            msg += getResources().getString(R.string.toppings_optionOne)+": "+getResources().getString(R.string.boolean_yes)+"\n";
        } else {
            msg += getResources().getString(R.string.toppings_optionOne)+": "+getResources().getString(R.string.boolean_no)+"\n";
        }
        if (options[0]) {
            msg += getResources().getString(R.string.toppings_optionTwo)+": "+getResources().getString(R.string.boolean_yes)+"\n";
        } else {
            msg += getResources().getString(R.string.toppings_optionTwo)+": "+getResources().getString(R.string.boolean_no)+"\n";
        }
        msg += getResources().getString(R.string.form_total)+": " + NumberFormat.getCurrencyInstance().format(totalPrice);

        return msg;

    }

    /**
     * Calculate price
     *
     * @param quantity Quantity of cups
     * @param options  The extra options
     * @return price of cups
     */

    private double calculatePrice(int quantity, boolean[] options) {

        double basePrice = price;
        if (options[0]) {
            basePrice += priceWhippedCream;
        }

        if (options[1]) {
            basePrice += priceChocolate;
        }

        return quantity * basePrice;
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity < 100) {
            quantity += 1;
        }else{
            displayToast(getResources().getString(R.string.error_quantityMax));
        }
        displayQuantiy(quantity);
        displayPrice(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {

        if (quantity > 1) {
            quantity -= 1;
        }else{
            displayToast(getResources().getString(R.string.error_quantityMin));
        }
        displayQuantiy(quantity);
        displayPrice(quantity);
    }


    /**
     * This method is called when a toppings checkbox is clicked.
     */
    public void checkboxChecked(View view) {
        displayPrice(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantiy(int quantity) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + quantity);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {

        boolean[] extraOptions;
        extraOptions = new boolean[2];
        extraOptions[0] = false; //WhippedCream
        extraOptions[1] = false; //Chocolate

        CheckBox hasWhippedCream = (CheckBox) findViewById(R.id.WhippedCream);
        extraOptions[0] = hasWhippedCream.isChecked();

        CheckBox hasChocolate = (CheckBox) findViewById(R.id.Chocolate);
        extraOptions[1] = hasChocolate.isChecked();

        // Calculate price
        double total_price = calculatePrice(quantity, extraOptions);
        Log.v("MainActivity", "The price is " + total_price);

        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(NumberFormat.getCurrencyInstance().format(total_price));
    }

    /**
     * This method displays the given text on the screen.
     * @param msg Message to display
     */
    private void displayMessage(String msg) {

        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(msg);

    }

    /**
     * Display a toast message
     * @param msg Message to display
     */

    private void displayToast(String msg) {
        Toast toastMsg = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toastMsg.show();
    }

    /**
     *  Send email with order
     *  @param msg Message to send
     *  @param subject Subject
     */
    private void sendEmail(String msg, String subject){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, emailAddress);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, msg);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     *  Send email with order
     *  @param msg Message to send
     */
    private void sendEmail(String msg){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, emailAddress);
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava Order!");
        intent.putExtra(Intent.EXTRA_TEXT, msg);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}