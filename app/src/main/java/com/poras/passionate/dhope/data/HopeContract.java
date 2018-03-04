package com.poras.passionate.dhope.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by purus on 3/4/2018.
 */

public class HopeContract {
    public static final String CONTENT_AUTHORITY = "com.poras.passionate.dhope";

    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_HOPE = "dhope";


    public static final class HopeEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().
                appendPath(PATH_HOPE).
                build();

        public static final String TABLE_NAME = "dhope";

        public static final String COLUMN_CATEGORY = "category";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_LAT = "lat";
        public static final String COLUMN_LANG = "lang";

        public static Uri buildMovieUriWithId(int pType) {
            return CONTENT_URI.buildUpon().appendPath(String.valueOf(pType)).build();
        }
    }
}
