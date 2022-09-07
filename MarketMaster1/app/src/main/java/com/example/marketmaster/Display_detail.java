package com.example.marketmaster;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import com.example.marketmaster.data.DatabaseHelper;
import com.example.marketmaster.data.WatchListDB;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.example.marketmaster.data.ChartElement;
import com.example.marketmaster.databinding.ActivityDisplayDetailBinding;
import com.example.marketmaster.utilities.JsonUtils;
import com.example.marketmaster.utilities.NetworkUtils;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;



public class Display_detail extends AppCompatActivity {
    SQLiteDatabase watchDB;
    SQLiteOpenHelper openHelper;
  //  WatchListDB watchDB;
    Button _btnAdd, _btnViewData;
    EditText _txtEmail, _txtPass;
    Cursor cursor;

    private static final String TAG = "Display_detail";
    public static final String TABLE_NAME = "watchlist";
    private String symbol;
    private LineChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDisplayDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_display_detail);
        openHelper = new WatchListDB(this);
        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
                symbol = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
                ChartElement element = new ChartElement(symbol);
                binding.setElement(element);
                loadData();
            }
        }
       // watchDB = new WatchListDB(this);
        _btnAdd = (Button)findViewById(R.id.watch);
        _btnViewData = (Button)findViewById(R.id.view);
        watchDB = openHelper.getWritableDatabase();

        _btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               boolean insert= insertdata(symbol);
                if (insert) {
                    Toast.makeText(Display_detail.this, "Successfully Inserted!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Display_detail.this, "The Stock is already in your list. Go check it out :)", Toast.LENGTH_LONG).show();
                }
            }
        });

        _btnViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor data = showData();

                if (data.getCount() == 0) {
                    display("Error", "No Data Found.");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (data.moveToNext()) {
                    buffer.append("ID: " + data.getString(0) + "\n");
                    buffer.append("Name: " + data.getString(1) + "\n");
                }
                display("All Stored Data:", buffer.toString());
            }
        });
    }


    public boolean insertdata(String fname){
        ContentValues contentValues = new ContentValues();
        contentValues.put(WatchListDB.COL_2, fname);
        long id = watchDB.insert(WatchListDB.TABLE_NAME, null, contentValues);
        return id != -1;
    }

    public Cursor showData(){
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


    private void loadData(){
        new FetchSpecific().execute();
    }

    /**
     * Setting chart's view
     * @param data
     */
    private void chartView(ArrayList<ChartElement>data){
        chart = findViewById(R.id.chart);
        chart.setBackgroundColor(Color.WHITE);
        // disable description text
        chart.getDescription().setEnabled(false);

        // enable touch gestures
        chart.setTouchEnabled(true);

        // set listeners
        chart.setDrawGridBackground(false);

        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);

        // force pinch zoom along both axis
        chart.setPinchZoom(true);

        XAxis xAxis;
        {   // // X-Axis Style // //
            xAxis = chart.getXAxis();
            xAxis.enableGridDashedLine(10f, 10f, 0f);
        }

        YAxis yAxis;
        {   // // Y-Axis Style // //
            yAxis = chart.getAxisLeft();
            chart.getAxisRight().setEnabled(false);
            yAxis.enableGridDashedLine(10f, 10f, 0f);
        }

        {   // // Create Limit Lines // //
            yAxis.setDrawLimitLinesBehindData(true);
            xAxis.setDrawLimitLinesBehindData(true);
        }

        setData(data);

        // draw pts over time
        chart.animateX(1500);

        // get the legend
        Legend l = chart.getLegend();

        // draw legend
        l.setForm(LegendForm.LINE);
    }

    /**
     * Data provider for chart
     * Setting view
     */
    private void setData(ArrayList<ChartElement> data) {

        ArrayList<Entry> values = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            try {
                if (data.get(i).getValue() != null) {
                    values.add(new Entry(i, Float.parseFloat(data.get(i).getValue()),
                            getResources().getDrawable(R.drawable.login_btn)));
                }
            }catch (Exception e){
                Log.getStackTraceString(e);
            }
        }

        LineDataSet set1;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            set1.notifyDataSetChanged();
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, symbol);

            set1.setDrawIcons(false);

            // draw dashed line
            set1.enableDashedLine(10f, 5f, 0f);

            // black lines and points
            set1.setColor(Color.BLACK);
            set1.setCircleColor(Color.BLACK);

            // line thickness and point size
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);

            // draw points as solid circles
            set1.setDrawCircleHole(false);

            // customize legend entry
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            // text size of values
            set1.setValueTextSize(9f);

            // draw selection line as dashed
            set1.enableDashedHighlightLine(10f, 5f, 0f);

            // set the filled area
            set1.setDrawFilled(true);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return chart.getAxisLeft().getAxisMinimum();
                }
            });

            // set color of filled area
            if (Utils.getSDKInt() >= 18) {
                set1.setFillDrawable(ContextCompat.getDrawable(this, R.drawable.fade_orange));
            } else {
                set1.setFillColor(Color.BLACK);
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1); // add the data sets

            // create a data object with the data sets
            LineData lineData = new LineData(dataSets);

            // set data
            chart.setData(lineData);
        }
    }

    private class FetchSpecific extends AsyncTask<String, Void, ArrayList<ChartElement>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<ChartElement> doInBackground(String... params) {
            HttpResponse<JsonNode> results = NetworkUtils
                    .responseFromApi("https://apidojo-yahoo-finance-v1.p.rapidapi.com/market/get-charts?comparisons=%5EGDAXI%2C%5EFCHI&region=US&lang=en&symbol="
                            +symbol+"&interval=1d&range=5d");
            assert results != null;
            return JsonUtils.getDataListChart(results);
        }

        @Override
        protected void onPostExecute(ArrayList<ChartElement>data) {
            if(data != null){
                chartView(data);
            }else {
                Log.e(TAG, "NULL DATA");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.refresh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            loadData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}