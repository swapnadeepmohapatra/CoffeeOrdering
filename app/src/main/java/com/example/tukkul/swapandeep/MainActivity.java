package com.example.tukkul.swapandeep;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {

        if (quantity == 10) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have more than 10 coffees", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }

        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {

        if (quantity == 1) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        EditText nameField = findViewById(R.id.name_field);
        String name = nameField.getText().toString();


        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, name);

        displayMessage(priceMessage);

        Toast.makeText(this, "Order Summary Ready", Toast.LENGTH_SHORT).show();
    }

    public void sendMail(View view){
        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkbox);
        Boolean hasChocolate = chocolateCheckBox.isChecked();

        EditText nameField = findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price,hasWhippedCream, hasChocolate, name);

        Toast.makeText(this, "Order Placed", Toast.LENGTH_SHORT).show();


        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee Order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        intent.putExtra(Intent.EXTRA_EMAIL,new String[] { "swapnadeep456@gmail.com" });
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }


    /**
     * Calculates the price of the order.
     *
     * @param addChocolate is whether or not the user wants chocolate topping
     * @param addWhippedCream is whether or not the user wants whipped cream toppings
     * @return total price
     */

    public int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        // Price of 1 cup of coffee
        int basePrice =5;

        // Add $1 if the user wants whipped cream
        if (addWhippedCream){
            basePrice = basePrice + 1;
        }

        // Add $2 if the user wants chocolate
        if (addChocolate){
            basePrice = basePrice + 2;
        }

        // Calculate the total order price by multiplying by quantity
        return quantity * basePrice;
    }

    /**
     * create summary of the order
     * @param name of the customer
     * @param addChocolate is whether or not the user wants chocolate topping
     * @param addWhippedCream is whether or not the user wants whipped cream toppings
     * @param price of the order
     * @return text summary
     */

    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String name) {
        String priceMessage = "Name : " + name;
        priceMessage += "\nWhipped Cream Topping : " + addWhippedCream;
        priceMessage += "\nChocolate Topping : " + addChocolate;
        priceMessage = priceMessage + "\nQuantity : " + quantity;
        priceMessage = priceMessage + "\nThe Price is : ₹ " + price;
        priceMessage = priceMessage + "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen
     */
    private void displayQuantity(int quantity){
        TextView quantityTextView = findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + quantity);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = findViewById(
                R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}