package com.example.mo.test;

import android.provider.BaseColumns;

public class user_schema {
    public static class columns implements BaseColumns {
        public static final String TABLE_NAME = "user_login";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_PASSWORD_HASHED = "password_hashed";
    }
}
