/*
Main activity is the front page for our project. It contains a logo and two buttons. The user guide
is included in the front page.
 */
package com.example.marketmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private Button guide;
    private Button loginBtnView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginBtnView = findViewById(R.id.btn_1);
        loginBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirecting to Login page
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        ImageView myImage = (ImageView) findViewById(R.id.my_image_view);
        myImage.setImageResource(R.drawable.logo);

        guide = findViewById(R.id.btnguide);
        guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go back to registration
                display("1. Please use the display button to see the chart of stock trends\n" +
                        "2. Press the add button if you want to keep monitoring the stock\n" + " and save it to your watch list\n" +
                        "3. Hold on to the stock in the watchlist to delete it from the list");
            }
        });
    }

    /*
    method to display the user guide
     */
    public void display(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("User Guide");
        builder.setMessage(message);
        builder.show();
    }
}