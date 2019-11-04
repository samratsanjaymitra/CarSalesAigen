package com.example.carsales.DbTables;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import com.example.carsales.POJO.ImagePOJO;
import com.example.carsales.POJO.LoginPOJO;


public class DbCreation extends SQLiteOpenHelper {


    SQLiteDatabase db;
    ArrayList<LoginPOJO> loginList = new ArrayList<>();

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "carSales";
    private static final String TABLE_LOGIN = "login_details";
    private static final String TABLE_CARIMAGE = "car_image";
    private static final String TABLE_CARDETAILS = "car_details";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";


    public DbCreation(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table = "CREATE TABLE IF NOT EXISTS " + TABLE_LOGIN + "("
                + EMAIL + " varchar(200) PRIMARY KEY," + PASSWORD + " varchar(200)"
                + ")";

        db.execSQL(create_table);

        String insertSQL = "INSERT INTO " + TABLE_LOGIN + "\n" +
                "(email,password)\n" +
                "VALUES \n" +
                "(?, ?);";


        String email = "test@aigen.tech";
        String password = "AigenTech";
        db.execSQL(insertSQL, new String[]{email, password});


    }

    public boolean tableCarDetailsCreation() {
        try {
            db = this.getWritableDatabase();
            String create_carImage = "CREATE TABLE IF NOT EXISTS " + TABLE_CARDETAILS + "("
                    + " id varchar(200), carName varchar(200),url varchar(200)"
                    + ")";

            db.execSQL(create_carImage);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean checkForTableExists(String tableName) {
        db = this.getWritableDatabase();
        String sql = "SELECT name FROM sqlite_master WHERE type='table' AND name='" + tableName + "'";
        Cursor mCursor = db.rawQuery(sql, null);
        if (mCursor.getCount() > 0) {
            return true;
        }
        mCursor.close();
        return false;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARIMAGE);
        onCreate(db);
    }


    public ArrayList<LoginPOJO> getLoginDetails() {


        ArrayList<LoginPOJO> loginList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_LOGIN;
        db = this.getWritableDatabase();


        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                LoginPOJO login = new LoginPOJO();
                login.setEmail(cursor.getString(0));
                login.setPassword(cursor.getString(1));

                loginList.add(login);
            } while (cursor.moveToNext());
        }


        return loginList;


    }

    public void addCarDetails(int id, String carName, String url) {
        db = this.getWritableDatabase();

        String insertCarImage = "INSERT INTO " + TABLE_CARDETAILS + "\n" +
                "(id,carName,url)\n" +
                "VALUES \n" +
                "(?, ?, ?);";

        String carID = String.valueOf(id);

        db.execSQL(insertCarImage, new String[]{carID, carName, url});


    }

    public ArrayList<ImagePOJO> getCarDetails() {


        ArrayList<ImagePOJO> carList = new ArrayList<>();

        try {
            String selectIdQuery = "SELECT distinct(id) FROM " + TABLE_CARDETAILS;


            db = this.getWritableDatabase();


            Cursor cursor = db.rawQuery(selectIdQuery, null);
            if (cursor.moveToFirst()) {
                do {

                    String id = (cursor.getString(0));
                    String selectQuery = "SELECT * FROM " + TABLE_CARDETAILS + " where id =" + id;
                    Cursor cursorNew = db.rawQuery(selectQuery, null);
                    if (cursorNew.moveToFirst()) {
                        ImagePOJO imagePOJO = new ImagePOJO();
                        imagePOJO.setId(cursorNew.getString(0));
                        imagePOJO.setCarName(cursorNew.getString(1));
                        imagePOJO.setImageURL(cursorNew.getString(2));
                        carList.add(imagePOJO);

                    }
                } while (cursor.moveToNext());

            }

        } catch (Exception e) {
            Log.e("ssd", e.toString());
        }

        return carList;

    }


    public int getCarId() {
        int carId = 102;
        try {
            String selectQuery = "SELECT max(id) FROM " + TABLE_CARDETAILS;

            db = this.getWritableDatabase();
            String id = "";
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {

                    id = (cursor.getString(0));

                } while (cursor.moveToNext());

            }

            carId = Integer.parseInt(id);

        } catch (Exception e) {
            Log.e("ssd", e.toString());
        }

        return carId;
    }

    public ArrayList<String> getCarDetails(String id) {


        ArrayList<String> carURL = new ArrayList<>();

        try {
            String selectQuery = "SELECT distinct(url) FROM " + TABLE_CARDETAILS + " where id =" + id;

            db = this.getWritableDatabase();

            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {

                    String url = (cursor.getString(0));
                    carURL.add(url);

                } while (cursor.moveToNext());

            }
        } catch (Exception e) {
            Log.e("ssd", e.toString());
        }

        return carURL;

    }

}

