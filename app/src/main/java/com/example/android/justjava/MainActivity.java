package com.example.android.justjava;

import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;
    double price = 4.95;

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
        double totalPrice = calculatePrice(quantity);
        Log.v("MainActivity","The price is "+totalPrice);

        boolean[] extraOptions;
        extraOptions = new boolean[1];
        extraOptions[0] = false; //WhippedCream

        CheckBox hasWhippedCream = (CheckBox) findViewById(R.id.WhippedCream);
        extraOptions[0] = hasWhippedCream.isChecked();




        String msg = createOrderSummary(totalPrice, quantity, "Robin Migalski", extraOptions);
        displayMessage(msg);

    }

    /***
     *  This method creates an order summary
     *  @param totalPrice The total price
     *  @param quantity The quantity of cups
     *  @param name Customer name
     *  @param extra options Customer name
     *
     *  @return String order summary
     */
    private String createOrderSummary(double totalPrice, int quantity, String name, boolean[] options){

        String msg = "Name: " + name + "\n";
        msg += "Quantity: " + quantity + "\n";
        if(options[0]){
            msg += "Add whipped cream: Yes\n";
        }else{
            msg += "Add whipped cream: No\n";
        }
        msg += "Total: " + NumberFormat.getCurrencyInstance().format(totalPrice)  + "\n";
        msg += "Thank you!";

        return msg;

    }

    /**
     *  Calculate price
     *  @param quantity Quantity of cups
     *  @return price of cups
     */

    private double calculatePrice(int quantity){
        return quantity*price;
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        quantity += 1;
        displayQuantiy(quantity);
        displayPrice(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {

        if(quantity > 1) {
            quantity -= 1;
        }
        displayQuantiy(quantity);
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

        double total_price = price * number;
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(NumberFormat.getCurrencyInstance().format(total_price));
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String msg) {

        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(msg);
        Toast toastMsg = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toastMsg.setMargin(10,0);
        toastMsg.show();

    }
}