/*
 * Copyright (c) 2017 by Tran Le Duy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.duy.text.converter.localdata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import static com.duy.text.converter.localdata.DatabaseHelper.TextEntry.COLUMN_CONTENT;
import static com.duy.text.converter.localdata.DatabaseHelper.TextEntry.COLUMN_TIME;
import static com.duy.text.converter.localdata.DatabaseHelper.TextEntry.TABLE_NAME;

/**
 * Created by Duy on 09-Jul-17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_TIME + " LONG PRIMARY KEY, " +
                    COLUMN_CONTENT + " TEXT)";

    private static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "UserDatabase.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void insert(TextItem textItem) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TIME, textItem.getTime());
        contentValues.put(COLUMN_CONTENT, textItem.getText());
        SQLiteDatabase writableDatabase = this.getWritableDatabase();
        writableDatabase.insert(TABLE_NAME, null, contentValues);
    }

    private ArrayList<TextItem> getAll() {
        ArrayList<TextItem> list = new ArrayList<>();

        String[] projection = new String[]{
                COLUMN_TIME,
                COLUMN_CONTENT
        };
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, projection, null, null, null, null, null);
        while (cursor.moveToNext()) {
            long time = cursor.getLong(cursor.getColumnIndex(COLUMN_TIME));
            String content = cursor.getString(cursor.getColumnIndex(COLUMN_CONTENT));
            list.add(new TextItem(time, content));
        }
        return list;
    }

    public void delete(TextItem item) {
        delete(item.getTime());
    }

    public int delete(long time) {
        String selection = COLUMN_TIME + " LIKE ?";
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, selection, new String[]{time + ""});
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_TABLE);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public static class TextEntry {
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_CONTENT = "content";
        public static final String TABLE_NAME = "tbl_custom";
    }
}
