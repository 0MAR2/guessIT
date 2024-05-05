package com.example.guessit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Dbhelper extends SQLiteOpenHelper {
    public Dbhelper(Context context) {
        super(context, "Users", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
            DB.execSQL("create Table Userdetails(usename TEXT primary key,winStreak INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        DB.execSQL("drop Table if exists Userdetails");
    }
    public Boolean insertuserdata(String username,int winStreak){
        SQLiteDatabase DB=this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("username:",username);
        contentValues.put("winstreak:",winStreak);
        long result= DB.insert("Userdetails",null,contentValues);
        if (result == -1) {
            return false;
        }else{
            return true;
        }
    }
    public Boolean updateuserdata(String username,int winStreak){
        SQLiteDatabase DB=this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("winstreak:",winStreak);
        Cursor cursor=DB.rawQuery("Select * from Userdetails where name = ?",new String[]{username});
        if(cursor.getCount()>0){
            long result= DB.update("Userdetails" ,contentValues,"username=?",new String[]{username});
            if (result == -1) {
                return false;
            }else{
                return true;
            }
        }else{
            return false;
        }

    }
    public Boolean deleteuserdata(String username){
        SQLiteDatabase DB=this.getWritableDatabase();
        Cursor cursor=DB.rawQuery("Select * from Userdetails where name = ?",new String[]{username});
        if(cursor.getCount()>0){
            long result= DB.delete("Userdetails" ,"username=?",new String[]{username});
            if (result == -1) {
                return false;
            }else{
                return true;
            }
        }else{
            return false;
        }

    }
    public Cursor getdata(){
        SQLiteDatabase DB=this.getWritableDatabase();
        Cursor cursor=DB.rawQuery("Select * from Userdetails",null);
        return cursor;
    }
}
