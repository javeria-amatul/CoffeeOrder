/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 * <p>
 * package com.example.android.justjava;
 */
package com.example.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import static android.R.attr.duration;
import static android.R.attr.name;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    int quantity = 2;

    public void increment(View view) {
        if(quantity==100){
            Context context = getApplicationContext();
            CharSequence text = "You cannot order more than 100 coffees";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context,text, duration);
            toast.show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if(quantity==1){
            Context context = getApplicationContext();
            CharSequence text = "You cannot order less than 1 coffee";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context,text, duration);
            toast.show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    private int calculatePrice(boolean hasWhipCream,boolean hasChocolate) {
        int basePrice=5;
       if(hasWhipCream){
            basePrice=1+basePrice;
        }
        if (hasChocolate){
            basePrice=2+basePrice;
        }

        return basePrice*quantity;
    }

    //boolean whipC=false;
    public void submitOrder(View view) {
        final CheckBox whipCreamCheckbox=(CheckBox) findViewById(R.id.whipCream);
           boolean hasWhipCream= whipCreamCheckbox.isChecked();
        final  CheckBox chocolateCheckbox=(CheckBox) findViewById(R.id.chocolate);
            boolean hasChocolate=chocolateCheckbox.isChecked();
        int price=calculatePrice(hasWhipCream,hasChocolate);
        EditText nameFld=(EditText) findViewById(R.id.nameField);
        String name=nameFld.getText().toString();
        String priceMessage= createOrderSummary(name,price,hasWhipCream,hasChocolate);
       // displayMessage(priceMessage);
        //Sends a mail to gmail
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "New Coffee Order for "+name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public String createOrderSummary( String name,int price,boolean addWhipCream, boolean addChocolate){
        String priceMsg=getString(R.string.name)+name+"\n"+
                        getString(R.string.add_choc)+addWhipCream+"\n"+
                        getString(R.string.add_choc)+addChocolate+"\n"+
                        getString(R.string.quantity)+quantity+"\n"+
                        getString(R.string.total)+ calculatePrice(addWhipCream,addChocolate)+"\n"+
                        getString(R.string.thank_you);
        return priceMsg;
    }
    /**
     * This method displays the given quantity value on the screen.
     */

//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }

    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }

}