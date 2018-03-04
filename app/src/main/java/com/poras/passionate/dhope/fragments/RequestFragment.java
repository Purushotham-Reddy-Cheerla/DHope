package com.poras.passionate.dhope.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
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

public class RequestFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private DonationAdapter adapter;
    private static final int LOADER_ID = 1215;
    private Button btn_request;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_requests, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rv_requests);
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
        btn_request = view.findViewById(R.id.btn_request);
        btn_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent requestIntent = new Intent(getContext(), FormActivity.class);
                requestIntent.putExtra("type", 1);
                startActivity(requestIntent);
            }
        });
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(getContext(), HopeContract.HopeEntry.buildMovieUriWithId(1),
                null,
                null,
                null,
                HopeContract.HopeEntry.COLUMN_TIME);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if (data != null) {
            data.moveToFirst();
            adapter.swapCursor(data);
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
