package com.example.manipal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper {

    public static final String DBNAME = "Login.db";

    public DBhelper(Context context) {
        super(context, "Login.db" , null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table users(user TEXT primary key,Password TEXT)");
        MyDB.execSQL("CREATE TABLE appointments(doctor TEXT , user TEXT, appointmentDate TEXT, otherData TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
        MyDB.execSQL("drop TABLE if exists users");
        MyDB.execSQL("DROP TABLE IF EXISTS appointments");
        onCreate(MyDB);

    }

    public Boolean insertData(String user, String Password)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("user",user);
        contentValues.put("Password",Password);
        long result= MyDB.insert("users",null,contentValues);
        return result != -1;
    }

    public  Boolean checkuser(String user){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where user = ?",new String[] {user});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Boolean checkuserPassword(String user, String Password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where user = ? and Password = ?", new String[] {user,Password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }
    public Boolean insertAppointment(String doctor, String user, String appointmentDate, String otherData) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("doctor", doctor);
        contentValues.put("user", user);
        contentValues.put("appointmentDate", appointmentDate);
        contentValues.put("otherData", otherData);
        long result = MyDB.insert("appointments", null, contentValues);
        return result != -1;
    }
   public Cursor getAppointmentsForDoctor(String doctor) {
     SQLiteDatabase MyDB = this.getWritableDatabase();
     return MyDB.rawQuery("SELECT * FROM appointments WHERE doctor = ?", new String[]{doctor});
}

}
