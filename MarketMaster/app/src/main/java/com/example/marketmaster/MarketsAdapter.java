/*
Markets adapter is the helper activity for the display activity. The adapter set the values for the
recyclerview to display the list of stocks.
 */
package com.example.marketmaster;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.marketmaster.data.Market;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MarketsAdapter extends RecyclerView.Adapter<MarketsAdapter.MarketsAdapterViewHolder> {

    ArrayList<Market> data;

    /*
    method to set data
     */
    public void setData(ArrayList<Market> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    private final MarketAdapterOnClickHandler mClickHandler;

    public MarketsAdapter(MarketAdapterOnClickHandler mClickHandler) {
        this.mClickHandler = mClickHandler;
    }

    public interface MarketAdapterOnClickHandler {
        void onClick(Market market);
    }

    public class MarketsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mMarketTextView;

        public MarketsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            mMarketTextView = itemView.findViewById(R.id.tv_market_data);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            mClickHandler.onClick(data.get(pos));
        }
    }

    @NonNull
    @Override
    public MarketsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context c = parent.getContext();
        int layoutid = R.layout.market_list_item;
        LayoutInflater in = LayoutInflater.from(c);
        boolean shouldAttachToParentImmediately = false;

        View view = in.inflate(layoutid, parent, shouldAttachToParentImmediately);
        return new MarketsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MarketsAdapterViewHolder holder, int position) {
        holder.mMarketTextView.setText(data.get(position).toString());
    }

    @Override
    public int getItemCount() {
        if (data == null)
            return 0;
        return data.size();
    }
}
