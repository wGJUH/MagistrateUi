package com.example.wgjuh.magistrateprojectui;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by wGJUH on 18.02.2017.
 */

public class SqlHelper extends SQLiteOpenHelper {

    public static SqlHelper instance;
    public static final String DB_LOCATION = "/data/data/" + BuildConfig.APPLICATION_ID + "/databases/";
    public static final String DB_NAME = "Magistrate.db";
    public static final int DB_VERSION = 1;
    private Context context;
    private SQLiteDatabase database;
    public static SqlHelper getInstance(Context context){
        if(instance != null)
            return instance;
        else instance = new SqlHelper(context);
        return instance;
    }


    private SqlHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
        if (isDatabaseExists()) {
            System.out.println("Database exists");
            opendatabase();

            // checkVersion();
        } else {
            System.out.println("Database doesn't exist");
            try {
                createdatabase();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void opendatabase() throws SQLException {
        //Open the database
        String mypath = DB_LOCATION + DB_NAME;
        database = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);

    }
    public boolean isDatabaseExists() {
        boolean checkdb = false;
        try {
            String myPath = DB_LOCATION + DB_NAME;
            File dbfile = new File(myPath);
            checkdb = dbfile.exists();
        } catch (SQLiteException e) {
            System.out.println("Database doesn't exist");
        }
        return checkdb;    }
    public void createdatabase() throws IOException {
        long start = System.currentTimeMillis();
        if (isDatabaseExists()) {
            System.out.println(" Database exists.");
        } else {
            this.getReadableDatabase();
            try {
                copydatabase();
            } catch (IOException e) {
                e.printStackTrace();
                throw new Error("Error copying database");
            }
        }
        System.out.println(" createDB method finished: " + (System.currentTimeMillis() - start));
    }
    private void copydatabase() throws IOException {
        //Open your local db as the input stream
        System.out.println(" START COPYING");
        InputStream myinput = null;
        try {
            myinput = context.getAssets().open(DB_NAME);
        } catch (IOException e) {
            System.out.println(" get from assets failed ");
            e.printStackTrace();
        }
        System.out.println(" assets: " + myinput.available());
        // Path to the just created empty db
        String outfilename = DB_LOCATION + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myoutput = new FileOutputStream(outfilename);

        // transfer byte to inputfile to outputfile
        byte[] buffer = new byte[myinput.available()];
        System.out.println(" BUFFER: " + buffer.length);
        int length;
        while ((length = myinput.read(buffer)) > 0) {
            myoutput.write(buffer, 0, length);
        }
        //Close the streams
        myoutput.flush();
        myoutput.close();
        myinput.close();
        opendatabase();
        database.setVersion(DB_VERSION);
        close();
    }
    public boolean isUserExist(String email, String password){
        boolean isExist;
        String sqlCmd = "select * from students where email = ? and password = ?";
        opendatabase();
         isExist = database.rawQuery(sqlCmd, new String[]{email,password}).moveToFirst();
        close();
        return isExist;
    }
    public int getIdByEmail(String email){
        int id = -7733;
        String sqlCmd = "select id from students where email = ?";
        opendatabase();
        Cursor cursor = database.rawQuery(sqlCmd,new String[]{email});
        if(cursor.moveToFirst())
            id = cursor.getInt(0);
        close();
        return id;
    }
    public boolean isEmailExist(String email){
        boolean isExist;
        String sqlCmd = "select id from students where email = ?";
        opendatabase();
        Cursor cursor = database.rawQuery(sqlCmd,new String[]{""+email});
        close();
        return cursor.moveToFirst();
    }

    /**
     * Для проверки регистрации по зачетной книжке
     * @return
     */
    public boolean isRecordBookExist(String record_book){
        boolean isExist;
        String sqlCmd = "select * from students where record_book = ?";
        opendatabase();
        Cursor cursor = database.rawQuery(sqlCmd,new String[]{""+record_book});
        close();
        return cursor.moveToFirst();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
