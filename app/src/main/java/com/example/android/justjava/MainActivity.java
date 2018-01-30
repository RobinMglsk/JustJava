package com.example.android.justjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

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
        String msg = createOrderSummary(totalPrice, quantity, "Robin Migalski");
        displayMessage(msg);
    }

    /***
     *  This method creates an order summary
     *  @param totalPrice The total price
     *  @param quantity The quantity of cups
     *  @param name Customer name
     *
     *  @return String order summary
     */
    private String createOrderSummary(double totalPrice, int quantity, String name){

        String msg = "Name: " + name + "\n";
        msg += "Quantity: " + quantity + "\n";
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
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(total_price));
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String msg) {

        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(msg);
    }
}