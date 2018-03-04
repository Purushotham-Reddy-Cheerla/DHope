package com.poras.passionate.dhope.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.poras.passionate.dhope.FormActivity;
import com.poras.passionate.dhope.R;
import com.poras.passionate.dhope.adapters.DonationAdapter;
import com.poras.passionate.dhope.data.HopeContract;

/**
 * Created by purus on 3/4/2018.
 */

public class DonationFragment extends Fragment implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor> {
    private DonationAdapter adapter;
    private static final int LOADER_ID = 2821;
    private Button btn_donate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_donations, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rv_donations);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new DonationAdapter(getContext());
        recyclerView.setAdapter(adapter);
        getLoaderManager().initLoader(LOADER_ID, null, this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_donate = (Button) view.findViewById(R.id.btn_donate);
        btn_donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent requestIntent = new Intent(getContext(), FormActivity.class);
                requestIntent.putExtra("type", 0);
                startActivity(requestIntent);
            }
        });

    }

    @NonNull
    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new android.support.v4.content.CursorLoader(getContext(),
                HopeContract.HopeEntry.buildMovieUriWithId(0),
                null,
                null,
                null,
                HopeContract.HopeEntry.COLUMN_TIME);
    }

    @Override
    public void onLoadFinished(@NonNull android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        if (data != null) {
            data.moveToFirst();
            adapter.swapCursor(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull android.support.v4.content.Loader<Cursor> loader) {

    }
}
