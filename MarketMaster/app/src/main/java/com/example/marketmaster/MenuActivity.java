/*
Menu activity contains a logo image and a bottom menu set which contains four buttons and direct
the user to four different pages.
 */
package com.example.marketmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    private Button mDisplayBtn;
    private Button mSearchBtn;
    private Button mWatchlistBtn;
    private Button mProfileBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mDisplayBtn = findViewById(R.id.menu_display);

        mDisplayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, DisplayActivity.class);
                startActivity(intent);
            }
        });

        mSearchBtn = findViewById(R.id.menu_search);
        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        mWatchlistBtn = findViewById(R.id.menu_mylist);
        mWatchlistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, WatchlistActivity.class);
                startActivity(intent);
            }
        });

        mProfileBtn = findViewById(R.id.menu_profile);
        mProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}