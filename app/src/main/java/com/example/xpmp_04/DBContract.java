package com.example.xpmp_04;

import android.provider.BaseColumns;

public final class DBContract {

    private DBContract() {}

    public static class BeerEntry implements BaseColumns {
        public static final String TABLE_NAME = "beers";
        public static final String COLUMN_NAME_PIVO = "pivo";
        public static final String COLUMN_NAME_STUPEN = "stupen";
        public static final String COLUMN_NAME_AMOUNT = "amount";
    }
}
