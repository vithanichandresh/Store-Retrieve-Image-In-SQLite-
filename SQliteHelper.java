package com.example.akshar.imagedatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by Akshar on 9/3/2017.
 */

public class SQliteHelper extends SQLiteOpenHelper {
    String TBname="Storege",id="id",name="name",number="number",image="image";
    SQLiteDatabase sqLiteDatabase;
    public SQliteHelper(Context context) {
        super(context, "Images", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    String sql=" create table "+TBname+"(" +id+ " integer primary key autoincrement ," +name+" text ," +number+" text ," +image+ " text)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertdata(ImageDataStoreg imageDataStoreg){
        sqLiteDatabase=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(name,imageDataStoreg.getName());
        contentValues.put(number,imageDataStoreg.getNumber());
        contentValues.put(image,imageDataStoreg.getImage());
        sqLiteDatabase.insert(TBname,null,contentValues);
        sqLiteDatabase.close();
    }
    public ArrayList<ImageDataStoreg> displayData() {
        ArrayList<ImageDataStoreg> arrayList = new ArrayList<>();
        sqLiteDatabase = getReadableDatabase();
        String sql = "select * from Storege";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        Log.d(TAG, "displayData: "+cursor.toString());
        while (cursor.moveToNext()) {
            ImageDataStoreg studBean = new ImageDataStoreg();
            studBean.setId(cursor.getInt(0)+"");
            studBean.setName(cursor.getString(1));
            studBean.setNumber(cursor.getString(2));
            studBean.setImage(cursor.getString(3));
            arrayList.add(studBean);
        }
        sqLiteDatabase.close();
        return arrayList;
    }

    public void updatedata(ImageDataStoreg imageDataStoreg){
        sqLiteDatabase=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(name,imageDataStoreg.getName());
        contentValues.put(number,imageDataStoreg.getNumber());
        contentValues.put(image,imageDataStoreg.getImage());
        sqLiteDatabase.update(TBname,contentValues,id+"="+imageDataStoreg.getId(),null);
        sqLiteDatabase.close();
    }
    public void deleteData(String delId) {
        sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(TBname, id + "=" + delId, null);
        sqLiteDatabase.close();
    }

}
