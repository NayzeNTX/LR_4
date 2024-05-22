package com.example.lr4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLHelper extends SQLiteOpenHelper{
    public SQLHelper(Context context){
        super(context, "Routes", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database){
        database.execSQL("create table table1 " +
                "(_id integer primary key autoincrement, route text not null, " +
                "departureDate text not null, arrivalDate text not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){
        database.execSQL("DROP TABLE IF EXISTs table1");
        onCreate(database);
    }

    public Cursor getFullTable(){
        SQLiteDatabase database = this.getReadableDatabase();
        return database.query("table1", new String[] {"_id", "route", "departureDate", "arrivalDate"},
                null, null, null, null, null);
    }

    public void addTrainRoute(String route, String departureDate, String arrivalDate) {
        SQLiteDatabase db = getWritableDatabase();
        int lastId = 0;
        Cursor cursor = db.rawQuery("SELECT MAX(_id) FROM table1", null);
        if (cursor.moveToFirst()) {
            lastId = cursor.getInt(0);
        }
        cursor.close();
        db.execSQL("INSERT INTO table1 (_id, route, departureDate, arrivalDate) VALUES (?, ?, ?, ?)", new String[]{String.valueOf(lastId + 1), route, departureDate, arrivalDate});
        db.close();
    }

    public void updateRoute(int id, String newRoute, String newDepartureDate, String newArrivalDate){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("route", newRoute);
        cv.put("departureDate", newDepartureDate);
        cv.put("arrivalDate", newArrivalDate);
        db.update("table1", cv, "_id = ?", new String[]{Integer.toString(id)});
    }

    public void deleteRoute(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("table1", "_id = ?", new String[]{Integer.toString(id)});
        db.execSQL("UPDATE table1 SET _id = _id - 1 WHERE _id > ?", new String[]{Integer.toString(id)});
    }
}
