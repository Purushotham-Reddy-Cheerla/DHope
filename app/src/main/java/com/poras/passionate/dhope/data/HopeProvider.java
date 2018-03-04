package com.poras.passionate.dhope.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by purus on 3/4/2018.
 */

public class HopeProvider extends ContentProvider {

    private static final int HOPE_LIST = 21;
    private static final int HOPE_LIST_WITH_TYPE = 28;


    private static final UriMatcher mUriMatcher = getUriMatcher();
    private HopeDbHelper mOpenHelper;

    private static UriMatcher getUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String contentAuthority = HopeContract.CONTENT_AUTHORITY;
        matcher.addURI(contentAuthority, HopeContract.PATH_HOPE, HOPE_LIST);
        matcher.addURI(contentAuthority, HopeContract.PATH_HOPE + "/#", HOPE_LIST_WITH_TYPE);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new HopeDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase database = mOpenHelper.getReadableDatabase();
        Cursor pCursor;

        switch (mUriMatcher.match(uri)) {
            case HOPE_LIST:
                pCursor = database.query(HopeContract.HopeEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder + " DESC");
                break;
            case HOPE_LIST_WITH_TYPE:
                String id = uri.getPathSegments().get(1);
                pCursor = database.query(HopeContract.HopeEntry.TABLE_NAME,
                        projection,
                        HopeContract.HopeEntry.COLUMN_TYPE + " = ? ",
                        new String[]{id},
                        null,
                        null,
                        sortOrder + " DESC");
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        pCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return pCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int match = mUriMatcher.match(uri);
        Uri returnUri;
        switch (match) {
            case HOPE_LIST:
                long id = db.insert(HopeContract.HopeEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(HopeContract.HopeEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
