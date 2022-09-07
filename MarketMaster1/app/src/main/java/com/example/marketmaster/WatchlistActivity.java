package com.example.marketmaster;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marketmaster.data.DatabaseHelper;
import com.example.marketmaster.data.WatchListDB;

import java.util.ArrayList;

/**
 * TODO：我现在的watchDB里存储了所有已经添加到watchlist里的数据，现在是允许duplicate的（这个可能要等到我们在watchlist里添加了delete之后在做）
 * TODO: 现在的任务是把watchDB里的data （ID，Name）转化成listview显示在watchlistactivity的页面上。
 */
public class WatchlistActivity extends AppCompatActivity {
    SQLiteDatabase watchDB;
    SQLiteOpenHelper openHelper;
    public static final String TABLE_NAME = "watchlist";
    Button _btnViewData;
    Cursor data;
    private ListView mLv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist); //link to watchlist interface

        openHelper = new WatchListDB(this);
        watchDB = openHelper.getReadableDatabase(); //get watchDB database
        WatchListDB myDB;
        myDB = new WatchListDB(this);
        mLv1 = findViewById(R.id.watchlist_lv);
        ArrayList<String> mList = new ArrayList<>();
        Cursor mCursor = myDB.showData();
        if(mCursor.getCount()==0){
            Toast.makeText(WatchlistActivity.this,"The Database was empty.", Toast.LENGTH_LONG).show();
        }else{
            while(mCursor.moveToNext()){
                mList.add(mCursor.getString(1));
                ListAdapter mlistAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mList);
                mLv1.setAdapter(mlistAdapter);
            }
        }

        _btnViewData = (Button)findViewById(R.id.view); //register the view button
        _btnViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               data = showData();

                if (data.getCount() == 0) {
                    display("Error", "No Data Found.");
                    return;
                }
                StringBuffer buffer = new StringBuffer(); //construct each line of data
                while (data.moveToNext()) {
                    buffer.append("ID: " + data.getString(0) + "\n");
                    buffer.append("Name: " + data.getString(1) + "\n");
                }
               display("All Stored Data:", buffer.toString()); //display all data
            }
        });
    }
    public Cursor showData(){
        //transmit data to the cursor
        Cursor data = watchDB.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }
    public void display(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}