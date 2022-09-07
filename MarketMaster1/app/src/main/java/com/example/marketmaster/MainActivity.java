package com.example.marketmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

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
    }
}