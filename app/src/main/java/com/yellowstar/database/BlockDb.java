package com.yellowstar.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class BlockDb extends SQLiteOpenHelper
{
    public static final String tableName = "BlockDb";
    static String DATABASE_NAME="BlockTable";
    public static final String KEY_ID = "Id";
    public static final String col1 = "DistrictId";
    public static final String col2 = "District";

    public BlockDb(Context _cntxt)
    {
        super(_cntxt, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String sql="CREATE TABLE "+tableName+" ("+KEY_ID+" INTEGER PRIMARY KEY, "+col1+" TEXT, "+col2+" TEXT)";
        db.execSQL(sql);
        System.out.println("Table is CreaTED");
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2)
    {

    }

    public long insert(String ID, String NAME)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(col1, ID);
        contentValues.put(col2, NAME);
        long cnt = 	db.insert(tableName, null, contentValues);
        return cnt;
    }

    // code to get all contacts in a list view
    public List<Data> getAllData() {
        List<Data> dataList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + tableName;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        /* looping through all rows and adding to list */
        if (cursor.moveToFirst())
        {
            do
            {
                Data dataB = new Data();
                dataB.setID(cursor.getString(1));
                dataB.setNAME(cursor.getString(2));

                dataList.add(dataB);
            }
            while (cursor.moveToNext());
        }
        // return contact list
        return dataList;
    }

    public void delrow( String ID)
    {
        String whr = col1+"=?";
        String[] str= {ID};
        SQLiteDatabase db= getWritableDatabase();
        db.delete(tableName, whr, str);
    }
    public void delall()
    {
        SQLiteDatabase db =getWritableDatabase();
        db.delete(tableName, null, null);
    }
}


