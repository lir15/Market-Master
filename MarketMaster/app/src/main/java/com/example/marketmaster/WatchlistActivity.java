/*
Watchlist activity that contains the list of stocks that were selected by the users. The watchlist
can be updated by holding the items in the list. Also, by pressing the item, the user can view the
detail display of the stock.
 */

package com.example.marketmaster;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.marketmaster.data.WatchListDB;

import java.util.ArrayList;
import java.util.List;

public class WatchlistActivity extends AppCompatActivity {
    SQLiteDatabase watchDB;
    SQLiteOpenHelper openHelper;
    public static final String TABLE_NAME = "watchlist";
    Button _btnViewData, _btnChange;
    Cursor data;
    ListView mLv1;
    TextView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist); //link to watchlist interface
        openHelper = new WatchListDB(this);
        watchDB = openHelper.getReadableDatabase(); //get watchDB database

        WatchListDB myDB;
        myDB = new WatchListDB(this);

        //return button
        back = findViewById(R.id.watch_tv);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mLv1 = (ListView) findViewById(R.id.watchlist_lv); // set up listview
        ArrayList<String> mList = new ArrayList<>();
        Cursor mCursor = myDB.showData();
        if (mCursor.getCount() == 0) {
            Toast.makeText(WatchlistActivity.this, "Your watch list is empty.", Toast.LENGTH_LONG).show();
        } else {
            while (mCursor.moveToNext()) {
                mList.add(mCursor.getString(1));
                ListAdapter mlistAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mList);

                mLv1.setAdapter(mlistAdapter);
                mLv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                     //   Intent displayDetail = new Intent(context, destinationClass);
                        String selectedFromList = (String) (mLv1.getItemAtPosition(position));
                        //display(selectedFromList, "!");
                        Class destinationClass = Display_detail.class;
                        Intent intent = new Intent(view.getContext(), destinationClass);
                        intent.putExtra(Intent.EXTRA_TEXT, selectedFromList);
                        startActivity(intent);
                    }
                });
                mLv1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        String selectedFromList = (String) (mLv1.getItemAtPosition(position));
                       // delete(selectedFromList);
                        watchDB.delete(TABLE_NAME,"Name = ?", new String[] {selectedFromList});
                        display("Deleting...", "Done !");
                        finish();
                        startActivity(getIntent());
                        // Tried to set up a alert message here but did not work as expected.

                        /*AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                        alert.setMessage("Are you sure you want to delete this ?");
                        alert.setCancelable(false);
                        alert.setPositiveButton("Yes, I know what I am doing.", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                              finish();
                              startActivity(getIntent());
                            }
                        });
                        alert.setNegativeButton("No.", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(getIntent());
                            }
                        });*/
                        return true;
                    }
                });

            }
        }


        _btnViewData = (Button) findViewById(R.id.view); //register the view button
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

    /*
    method to show data
     */
    public Cursor showData() {
        //transmit data to the cursor
        Cursor data = watchDB.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }

    /*
    method to display the existed items in the database
     */
    public void display(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }


    //delete the stock when clicked on the button
    public void myClickHandler(View v)
    {

        //reset all the listView items background colours
        //before we set the clicked one..
        /*  for (int i=0; i < mLv1.getChildCount(); i++)
        {
            mLv1.getChildAt(i).setBackgroundColor(Color.BLUE);
        }
        //get the row the clicked button is in
        LinearLayout vwParentRow = (LinearLayout)v.getParent();

        TextView child = (TextView)vwParentRow.getChildAt(0);
        Button btnChild = (Button)vwParentRow.getChildAt(1);
        btnChild.setText(child.getText());
        btnChild.setText("I've been clicked!");

        int c = Color.CYAN;

        vwParentRow.setBackgroundColor(c);
        vwParentRow.refreshDrawableState();*/
        display("Hey", " There");
    }


   /* private class MyListAdaoter extends ArrayAdapter<String>{
        private int layout;
        public MyListAdaoter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            layout = resource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.thumbnail = ()

            }
            return super.getView(position, convertView, parent);
        }
    }
    public class ViewHolder {
        ImageView thumbnail;
        TextView titile;
        Button button;
    }*/
}