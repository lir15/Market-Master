package com.example.marketmaster.utilities;

import com.example.marketmaster.data.ChartElement;
import com.example.marketmaster.data.Market;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class JsonUtils {

    // parse date from api
    public static ArrayList<Market> getDataListMarkets(HttpResponse<JsonNode> results){
        final String J_SYM = "exchange";
        final String J_fEN = "fullExchangeName";

        ArrayList resultsMarkets = new ArrayList();
        try {
            JSONArray array =  results.getBody().getObject()
                    .getJSONObject("marketSummaryResponse").getJSONArray("result");

            for (int i = 0; i < array.length(); i++){
                JSONObject jsonObject = array.getJSONObject(i);
                resultsMarkets.add(new Market(jsonObject.getString(J_SYM), jsonObject.getString(J_fEN)));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resultsMarkets;
    }

   // parse elements to chart
    public static ArrayList<ChartElement> getDataListChart(HttpResponse<JsonNode> results){
        final String J_SYM = "exchange";
        final String J_fEN = "fullExchangeName";

        ArrayList<ChartElement> resultsChart = new ArrayList();
        try {
            JSONArray array =  results.getBody().getObject()
                    .getJSONObject("chart").getJSONArray("result");

            JSONArray stamp = array.getJSONObject(0).getJSONArray("timestamp");


           for (int i = 0; i < stamp.length(); i++){
                JSONArray adj = array.getJSONObject(0).getJSONObject("indicators")
                        .getJSONArray("adjclose")
                        .getJSONObject(0)
                        .getJSONArray("adjclose");

                resultsChart.add(new ChartElement(stamp.getString(i), adj.getString(i)));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return resultsChart;
    }

}
