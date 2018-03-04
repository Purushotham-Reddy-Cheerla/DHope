package com.poras.passionate.dhope.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.poras.passionate.dhope.R;
import com.poras.passionate.dhope.data.HopeContract;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by purus on 3/4/2018.
 */

public class DonationAdapter extends RecyclerView.Adapter<DonationAdapter.DonationViewHolder> {
    private Context mContext;
    private Cursor mCursor;

    public DonationAdapter(Context context) {
        this.mContext = context;
    }

    private static SimpleDateFormat sDateFormat = new SimpleDateFormat("dd MMM");


    private static final long MINUTE_MILLIS = 1000 * 60;
    private static final long HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final long DAY_MILLIS = 24 * HOUR_MILLIS;

    @NonNull
    @Override
    public DonationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.donate_request_list_item, parent, false);
        return new DonationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonationViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        long dateMillis = mCursor.getLong(mCursor.getColumnIndex(HopeContract.HopeEntry.COLUMN_TIME));
        String category = mCursor.getString(mCursor.getColumnIndex(HopeContract.HopeEntry.COLUMN_CATEGORY));
        String quantity = mCursor.getString(mCursor.getColumnIndex(HopeContract.HopeEntry.COLUMN_QUANTITY));
        String date = "";
        long now = System.currentTimeMillis();

        if (now - dateMillis < (DAY_MILLIS)) {
            if (now - dateMillis < (HOUR_MILLIS)) {
                long minutes = Math.round((now - dateMillis) / MINUTE_MILLIS);
                date = String.valueOf(minutes) + "m";
            } else {
                long minutes = Math.round((now - dateMillis) / HOUR_MILLIS);
                date = String.valueOf(minutes) + "h";
            }
        } else {
            Date dateDate = new Date(dateMillis);
            date = sDateFormat.format(dateDate);
        }
        date = "\u2022 " + date;

        holder.category.setText(category);
        holder.quantity.setText(quantity);
        holder.time.setText(date);
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) return 0;
        return mCursor.getCount();
    }

    public void swapCursor(Cursor data) {
        if (data != null) {
            mCursor = data;
            notifyDataSetChanged();
        }
    }

    public class DonationViewHolder extends RecyclerView.ViewHolder {
        private TextView category;
        private TextView quantity;
        private TextView time;

        public DonationViewHolder(View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.item_name);
            quantity = itemView.findViewById(R.id.item_quantity);
            time = itemView.findViewById(R.id.time_stamp);
        }
    }
}
