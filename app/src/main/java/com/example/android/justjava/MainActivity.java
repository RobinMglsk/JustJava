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
        display(quantity);
        displayPrice(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        String part1 = "Total: ";
        String part2 = "\nThank you!";
        displayMessage(part1, part2);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        quantity += 1;
        display(quantity);
        displayPrice(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {

        if(quantity > 1) {
            quantity -= 1;
        }
        display(quantity);
        displayPrice(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
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
    private void displayMessage(String part1, String part2) {

        double total_price = price * quantity;
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(part1 + NumberFormat.getCurrencyInstance().format(total_price)+part2);
    }
}