/*
Search activity that the users can input the name of the stock they want to keep monitoring and
the search page will direct the users to the display detail pages. Also, the users can add their
favorable stocks to the watchlist.
 */
package com.example.marketmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity {
    private SearchView searchView;
    private ListView listView;
    // Show the list of stocks available
    private final String[] mStrings = {"CME CME","CBT","CMX COMEX",
            "NYM NY Mercantile", "CCY CCY", "NYB NYBOT", "WCB CHicago Options",
            "CCC CCC", "NIM", "FGI FTSE Index", "OSA Osaka", "DJI DJI", "SNP"};
    private TextView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        listView = (ListView) findViewById(R.id.lv);
        final ArrayAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mStrings);
        listView.setAdapter(adapter);
        //filter the list view
        listView.setTextFilterEnabled(true);
        searchView = (SearchView) findViewById(R.id.sv);
        searchView.setIconifiedByDefault(false);
        //set the button
        searchView.setSubmitButtonEnabled(true);
        //set default hint
        searchView.setQueryHint("Enter the stock name here:");

        //return button
        back = findViewById(R.id.search_tv);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //add your code here
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)){
                    listView.clearTextFilter();
                }else{
                    listView.setFilterText(newText);
                }
                return true;
            }
        });
        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object string = adapter.getItem(position);
                searchView.setQuery(string.toString(),true);
                String selectedFromList = (String) (listView.getItemAtPosition(position));
                //display(selectedFromList, "!");
                Class destinationClass = Display_detail.class;
                Intent intent = new Intent(view.getContext(), destinationClass);
                intent.putExtra(Intent.EXTRA_TEXT, selectedFromList);
                startActivity(intent);
            }
        });
    }
}