/*
profile activity that the users can input their personal information and the information will be
stored for displaying later. It also contains user guide link for the situation that the users
forget how to use the app.
 */
package com.example.marketmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    EditText name;
    EditText birth;
    EditText gender;
    EditText address;
    Button save;
    Button guide;
    TextView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        address = findViewById(R.id.profile_address2);
        name = findViewById(R.id.profile_name2);
        birth = findViewById(R.id.profile_birth2);
        gender = findViewById(R.id.profile_gender2);

        name.setText(getNameValue());
        birth.setText(getBirthValue());
        address.setText(getAddressValue());
        gender.setText(getGenderValue());

        //return button
        back = findViewById(R.id.backbtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        save = findViewById(R.id.btnsave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFromAddressET(address.getText().toString());

                saveFromBirthET(birth.getText().toString());
                saveFromGenderET(gender.getText().toString());
                saveFromNameET(name.getText().toString());

            }
        });

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
    method to get value from the shared preference for name
     */
    private String getNameValue() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getString("name", "");
    }

    /*
    method to get text input from the name edit text area
     */
    private void saveFromNameET(String text) {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("name", text);
        editor.apply();
    }

    /*
    method to get value from the shared preference for gender
    */
    private String getGenderValue() {
        SharedPreferences sharedPref1 = getPreferences(Context.MODE_PRIVATE);
        return sharedPref1.getString("gender", "");
    }

    /*
    method to get text input from the gender edit text area
     */
    private void saveFromGenderET(String text) {
        SharedPreferences sharedPref1 = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1 = sharedPref1.edit();
        editor1.putString("gender", text);
        editor1.apply();
    }

    /*
    method to get value from the shared preference for birth date
     */

    private String getBirthValue() {
        SharedPreferences sharedPref2 = getPreferences(Context.MODE_PRIVATE);
        return sharedPref2.getString("birth", "");
    }

    /*
    method to get text input from the birth edit text area
     */
    private void saveFromBirthET(String text) {
        SharedPreferences sharedPref2 = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPref2.edit();
        editor2.putString("birth", text);
        editor2.apply();
    }

    /*
    method to get value from the shared preference for address
    */
    private String getAddressValue() {
        SharedPreferences sharedPref3 = getPreferences(Context.MODE_PRIVATE);
        return sharedPref3.getString("address", "");
    }

    /*
    method to get text input from the address edit text area
     */
    private void saveFromAddressET(String text) {
        SharedPreferences sharedPref3 = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor3 = sharedPref3.edit();
        editor3.putString("address", text);
        editor3.apply();
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