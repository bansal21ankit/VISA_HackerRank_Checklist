package com.droidrank.checklist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

class ChecklistHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "Checklist.db";
    private static final int DB_VERSION = 1;

    private static final String TABLE_CHECKLIST = "Checklist";
    private static final String CHECKLIST_COL_ITEM_NAME = "itemName";
    private static final String CHECKLIST_COL_ITEM_CHECKED = "isChecked";
    private static final String CHECKLIST_COL_ID = "_id";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_CHECKLIST + " (" +
                    CHECKLIST_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    CHECKLIST_COL_ITEM_CHECKED + " INTEGER DEFAULT 0," +
                    CHECKLIST_COL_ITEM_NAME + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_CHECKLIST;

    ChecklistHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
    }

    boolean addItem(String item) {
        SQLiteDatabase db = null;
        try {
            db = getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(CHECKLIST_COL_ITEM_NAME, item);

            long id = db.insert(TABLE_CHECKLIST, null, values);
            return id != -1;
        }
        // Catch exception
        catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        }
        // Do cleanup
        finally {
            if (db != null) db.close();
        }
    }

    List<Item> getItems() {
        SQLiteDatabase db = null;
        try {
            db = getReadableDatabase();
            Cursor cursor = db.query(TABLE_CHECKLIST, null, null, null, null, null, CHECKLIST_COL_ITEM_CHECKED + " ASC, " + CHECKLIST_COL_ITEM_NAME + " ASC");
            List<Item> items = new ArrayList<>();

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                long id = cursor.getLong(cursor.getColumnIndex(CHECKLIST_COL_ID));
                String itemName = cursor.getString(cursor.getColumnIndex(CHECKLIST_COL_ITEM_NAME));
                int checked = cursor.getInt(cursor.getColumnIndex(CHECKLIST_COL_ITEM_CHECKED));
                items.add(new Item(id, itemName, checked));
                cursor.moveToNext();
            }

            cursor.close();
            return items;
        }
        // Catch exception
        catch (SQLiteException e) {
            e.printStackTrace();
            return null;
        }
        // Do cleanup
        finally {
            if (db != null) db.close();
        }
    }

    boolean updateSelection(long id, boolean checked) {
        SQLiteDatabase db = null;
        try {
            db = getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(CHECKLIST_COL_ITEM_CHECKED, checked);

            String selection = CHECKLIST_COL_ID + " = ?";
            String[] selectionArgs = new String[]{String.valueOf(id)};

            int count = db.update(TABLE_CHECKLIST, values, selection, selectionArgs);
            return count != 0;
        }
        // Catch exception
        catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        }
        // Do cleanup
        finally {
            if (db != null) db.close();
        }
    }

    boolean exists(String item) {
        SQLiteDatabase db = null;
        try {
            db = getReadableDatabase();

            String selection = CHECKLIST_COL_ITEM_NAME + " = ?";
            String[] selectionArgs = new String[]{item};

            Cursor cursor = db.query(TABLE_CHECKLIST, null, selection, selectionArgs, null, null, null, "1");
            int count = cursor.getCount();

            cursor.close();
            return count != 0;
        }
        // Catch exception
        catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        }
        // Do cleanup
        finally {
            if (db != null) db.close();
        }
    }

    boolean delete(long id) {
        SQLiteDatabase db = null;
        try {
            db = getWritableDatabase();

            String whereClause = CHECKLIST_COL_ID + " = ?";
            String[] whereArgs = new String[]{String.valueOf(id)};

            int count = db.delete(TABLE_CHECKLIST, whereClause, whereArgs);
            return count != 0;
        }
        // Catch exception
        catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        }
        // Do cleanup
        finally {
            if (db != null) db.close();
        }
    }
}
