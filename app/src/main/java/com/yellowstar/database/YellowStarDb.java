package com.yellowstar.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by indiaweb on 9/11/2017.
 */

/**
 * Created by indiaweb on 6/13/2017.
 */
public class YellowStarDb extends SQLiteOpenHelper
{
    public static final String tableName = "YellowStarDb";
    static String DATABASE_NAME="YellowStarTable";
    public static final String KEY_ID = "Id";
    public static final String col1 = "userId";
    public static final String col2 = "Location";
    public static final String col3 = "State";
    public static final String col4 = "District";
    public static final String col5 = "Block";
    public static final String col6 = "Panchayat";
    public static final String col7 = "Ward";
    public static final String col8 = "Device";
    public static final String col9 = "Pole";
    public static final String col10 = "Latitude_Longitude";
    public static final String col11 = "DateTime";
    public static final String col12 = "Luminary";
    public static final String col13 = "Battery";
    public static final String col14 = "Panel";
    public static final String col15 = "ImgInfo";
    public static final String col16 = "Photo";
    public static final String col17 = "Remark";

    public YellowStarDb(Context _cntxt)
    {
        super(_cntxt, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String sql="CREATE TABLE "+tableName+" ("+KEY_ID+" INTEGER PRIMARY KEY, "+col1+" TEXT, "+col2+" TEXT, "+col3+" TEXT, "+col4+" TEXT, "+col5+" TEXT, "+col6+" TEXT, "+col7+" TEXT, "+col8+" TEXT, "+col9+" TEXT, "+col10+" TEXT, "+col11+" TEXT, "+col12+" TEXT, "+col13+" TEXT, "+col14+" TEXT, "+col15+" TEXT, "+col16+" BLOB, "+col17+" TEXT)";
        db.execSQL(sql);
        System.out.println("Table is CreaTED");
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2)
    {

    }

    public long insertContact(String USERID, String USERLOCATION, String USERSTATE, String USERDISTRICT, String USERBLOCK
            , String USERPANCHAYAT, String USERWARD, String USERDEVICE, String POLENUM, String USERLATLNG, String DATETIME,
                              String SCANLUMINARY, String SCANBATTERY, String SCANPANEL, String INFOIMAGE, byte[] image,String REMARK) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(col1, USERID);
        contentValues.put(col2, USERLOCATION);
        contentValues.put(col3, USERSTATE);
        contentValues.put(col4, USERDISTRICT);
        contentValues.put(col5, USERBLOCK);
        contentValues.put(col6, USERPANCHAYAT);
        contentValues.put(col7, USERWARD);
        contentValues.put(col8, USERDEVICE);
        contentValues.put(col9, POLENUM);
        contentValues.put(col10, USERLATLNG);
        contentValues.put(col11, DATETIME);
        contentValues.put(col12, SCANLUMINARY);
        contentValues.put(col13, SCANBATTERY);
        contentValues.put(col14, SCANPANEL);
        contentValues.put(col15, INFOIMAGE);
        contentValues.put(col16, image);
        contentValues.put(col17, REMARK);

        long cnt = 	db.insert(tableName, null, contentValues);
        return cnt;
    }


    // code to get all contacts in a list view
    public List<Usermodel> getAllData() {
        List<Usermodel> contactList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + tableName;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        /* looping through all rows and adding to list */
        if (cursor.moveToFirst())
        {
            do
            {
                Usermodel contact = new Usermodel();
                contact.setId(cursor.getString(1));
                contact.setLocation(cursor.getString(2));
                contact.setDateTime(cursor.getString(11));
                contact.setPole(cursor.getString(9));
                contact.setLuminary(cursor.getString(12));
                contact.setBattery(cursor.getString(13));
                contact.setPanel(cursor.getString(14));
                contact.setPhoto(cursor.getBlob(16));
                contact.setDistrict(cursor.getString(4));
                contact.setBlock(cursor.getString(5));
                contact.setPanchayat(cursor.getString(6));
                contact.setWard(cursor.getString(7));
                contact.setRemark(cursor.getString(17));
                contactList.add(contact);
            }
            while (cursor.moveToNext());
        }
        // return contact list
        return contactList;
    }


    public ArrayList<String> GETID()
    {
        ArrayList<String> temp = new ArrayList<String>();

        SQLiteDatabase db= getReadableDatabase();

        String nam []={col1};

        Cursor cursor =db.query(tableName, nam, null, null, null, null, null);
        while(cursor.moveToNext())//row wise row
        {
            temp.add(cursor.getString(0));

        }
        return temp;
    }

    public ArrayList<String> GETLOCATION()
    {
        ArrayList<String> temp = new ArrayList<String>();

        SQLiteDatabase db= getReadableDatabase();

        String nam []={col2};

        Cursor cursor =db.query(tableName, nam, null, null, null, null, null);
        while(cursor.moveToNext())//row wise row
        {
            temp.add(cursor.getString(0));

        }
        return temp;
    }
    public ArrayList<String> GETLUM()
    {
        ArrayList<String> temp = new ArrayList<String>();

        SQLiteDatabase db= getReadableDatabase();

        String nam []={col12};

        Cursor cursor =db.query(tableName, nam, null, null, null, null, null);
        while(cursor.moveToNext())//row wise row
        {
            temp.add(cursor.getString(0));

        }
        return temp;
    }
    public ArrayList<String> GETBATTERY()
    {
        ArrayList<String> temp = new ArrayList<String>();

        SQLiteDatabase db= getReadableDatabase();

        String nam []={col13};

        Cursor cursor =db.query(tableName, nam, null, null, null, null, null);
        while(cursor.moveToNext())//row wise row
        {
            temp.add(cursor.getString(0));

        }
        return temp;
    }
    public ArrayList<String> GETPANEL()
    {
        ArrayList<String> temp = new ArrayList<String>();

        SQLiteDatabase db= getReadableDatabase();

        String nam []={col14};

        Cursor cursor =db.query(tableName, nam, null, null, null, null, null);
        while(cursor.moveToNext())//row wise row
        {
            temp.add(cursor.getString(0));

        }
        return temp;
    }


    public ArrayList<String> GETDATE()
    {
        ArrayList<String> temp = new ArrayList<String>();

        SQLiteDatabase db= getReadableDatabase();

        String nam []={col11};

        Cursor cursor =db.query(tableName, nam, null, null, null, null, null);
        while(cursor.moveToNext())//row wise row
        {
            temp.add(cursor.getString(0));

        }
        return temp;
    }

    public Bitmap getImage(int i)
    {
        SQLiteDatabase db= getReadableDatabase();
        String qu = "select "+col16+"  from "+tableName+" where "+KEY_ID+"=" + i ;

        Cursor cur = db.rawQuery(qu, null);

        if (cur.moveToFirst()){
            byte[] imgByte = cur.getBlob(0);
            cur.close();
            return BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
        }
        if (cur != null && !cur.isClosed()) {
            cur.close();
        }

        return null ;
    }

    public ArrayList<Bitmap> GETIMAGE()
    {
        ArrayList<Bitmap> temp = new ArrayList<Bitmap>();

        SQLiteDatabase db= getReadableDatabase();

        String nam []={col16};

        Cursor cursor =db.query(tableName, nam, null, null, null, null, null);

        if(cursor!=null) {
            try {
                while (cursor.moveToNext()) {

                    byte[] b = cursor.getBlob(Integer.parseInt(col16));
                    cursor.close();

                    ByteArrayInputStream inputStream = new ByteArrayInputStream(b);
                    Bitmap bit = BitmapFactory.decodeStream(inputStream);
                    temp.add(bit);
                }
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
        return temp;
    }

    public ArrayList<String> CAT_NAME()
    {
        ArrayList<String> temp = new ArrayList<String>();

        SQLiteDatabase db= getReadableDatabase();

        String nam []={col2};

        Cursor cursor =db.query(tableName, nam, null, null, null, null, null);
        while(cursor.moveToNext())//row wise row
        {
            temp.add(cursor.getString(0));

        }
        return temp;
    }

    public ArrayList<String> SUBCAT_ID()
    {
        ArrayList<String> temp = new ArrayList<String>();

        SQLiteDatabase db= getReadableDatabase();

        String nam []={col3};

        Cursor cursor =db.query(tableName, nam, null, null, null, null, null);
        while(cursor.moveToNext())//row wise row
        {
            temp.add(cursor.getString(0));

        }
        return temp;
    }
    public ArrayList<String> SUBCAT_NAME()
    {
        ArrayList<String> temp = new ArrayList<String>();

        SQLiteDatabase db= getReadableDatabase();

        String nam []={col4};

        Cursor cursor =db.query(tableName, nam, null, null, null, null, null);
        while(cursor.moveToNext())//row wise row
        {
            temp.add(cursor.getString(0));

        }
        return temp;
    }

    public ArrayList<String> PRO_ID()
    {
        ArrayList<String> temp = new ArrayList<String>();

        SQLiteDatabase db= getReadableDatabase();

        String nam []={col5};

        Cursor cursor =db.query(tableName, nam, null, null, null, null, null);
        while(cursor.moveToNext())//row wise row
        {
            temp.add(cursor.getString(0));

        }
        return temp;
    }
    public ArrayList<String> PRO_NAME()
    {
        ArrayList<String> temp = new ArrayList<String>();

        SQLiteDatabase db= getReadableDatabase();

        String nam []={col6};

        Cursor cursor =db.query(tableName, nam, null, null, null, null, null);
        while(cursor.moveToNext())//row wise row
        {
            temp.add(cursor.getString(0));

        }
        return temp;
    }
    public ArrayList<String> IMAGE1()
    {
        ArrayList<String> temp = new ArrayList<String>();

        SQLiteDatabase db= getReadableDatabase();

        String nam []={col7};

        Cursor cursor =db.query(tableName, nam, null, null, null, null, null);
        while(cursor.moveToNext())//row wise row
        {
            temp.add(cursor.getString(0));

        }
        return temp;
    }

    public ArrayList<String> IMAGE2()
    {
        ArrayList<String> temp = new ArrayList<String>();

        SQLiteDatabase db= getReadableDatabase();

        String nam []={col8};

        Cursor cursor =db.query(tableName, nam, null, null, null, null, null);
        while(cursor.moveToNext())//row wise row
        {
            temp.add(cursor.getString(0));

        }
        return temp;
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


