package com.example.wgjuh.magistrateprojectui;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

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

    public static SqlHelper getInstance(Context context) {
        if (instance != null)
            return instance;
        else instance = new SqlHelper(context);
        return instance;
    }


    private SqlHelper(Context context) {
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
        return checkdb;
    }

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

    public boolean isUserExist(String email, String password) {
        boolean isExist;
        String sqlCmd = "select * from students where email = ? and password = ?";
        opendatabase();
        isExist = database.rawQuery(sqlCmd, new String[]{email, password}).moveToFirst();
        close();
        return isExist;
    }

    public int getIdByEmail(String email) {
        int id = -7733;
        String sqlCmd = "select id from students where email = ?";
        opendatabase();
        Cursor cursor = database.rawQuery(sqlCmd, new String[]{email});
        if (cursor.moveToFirst())
            id = cursor.getInt(0);
        cursor.close();
        close();
        return id;
    }
    public int getGroupIdByUserId(int userId){
        int groupId = -7733;
        String sqlCmd = "select group_id from students where id = ?";
        opendatabase();
        Cursor cursor =  database.rawQuery(sqlCmd,new String[]{Integer.toString(userId)});
        if (cursor.moveToNext())
            groupId = cursor.getInt(cursor.getColumnIndex("group_id"));
        cursor.close();
        close();
        return groupId;
    }

    public boolean isEmailExist(String email) {
        boolean isExist;
        String sqlCmd = "select id from students where email = ?";
        opendatabase();
        isExist = database.rawQuery(sqlCmd, new String[]{"" + email}).moveToFirst();
        close();
        return isExist;
    }
    public boolean addNewUser(String fio, String email, String password, String recordBookNumber){
        boolean isOk = false;
       // String sqlCmd = "update students set email = ?, password =?, name =? where record_book = ?";
        opendatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email",email);
        contentValues.put("name",fio);
        contentValues.put("password",password);
        int updated = database.update("students",contentValues,"record_book =?",new String[]{recordBookNumber});
        if(updated == 1)
        {
            isOk = !isOk;
        }
        close();
        return isOk;
    }
    public ArrayList<String> getArticlesForGroupId(int groupId){
        Log.d(Constants.TAG,"Try to get articles for group id");
        ArrayList<String> articles = new ArrayList<>();
        String sqlCmd = "select name from manage_groups_articles left join articles on article_id = articles.id where group_id = ?";
        opendatabase();
        Cursor cursor = database.rawQuery(sqlCmd,new String[]{Integer.toString(groupId)});
        if(cursor.moveToFirst()){
            do {
                articles.add(cursor.getString(cursor.getColumnIndex("name")));
            }while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return articles;
    }
    public int getArticlesIdForName(String name){
        int articleId = -7733;
        String sqlCmd = "select id from articles where name = ?";
        opendatabase();
        Cursor cursor = database.rawQuery(sqlCmd, new String[]{name});
        if (cursor.moveToFirst())
            articleId = cursor.getInt(cursor.getColumnIndex("id"));
        cursor.close();
        close();
        return articleId;
    }

    public ArrayList<String> getThemes(int articleId){
        ArrayList<String> themes = new ArrayList<>();
        String sqlCmd = "select name from manage_themes_articles left join themes on theme_id = themes.id where article_id = ?";
        opendatabase();
        Cursor cursor = database.rawQuery(sqlCmd,new String[]{Integer.toString(articleId)});
        if(cursor.moveToFirst()){
            do {
                themes.add(cursor.getString(cursor.getColumnIndex("name")));
            }while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return themes;
    }


    public void getRecordBooks(){
        String sqlCmd = "select record_book from students ";
        opendatabase();
        Cursor cursor = database.rawQuery(sqlCmd, new String[]{});
        if (cursor.moveToFirst())
            do{
                Log.d(Constants.TAG,cursor.getString(0));
            }while (cursor.moveToNext());
        cursor.close();
        close();
    }
    /**
     * Для проверки регистрации по зачетной книжке
     *
     * @return
     */
    public boolean isRecordBookExist(String record_book) {
        boolean isExist;
        String sqlCmd = "select * from students where record_book = ?";
        opendatabase();
        isExist = database.rawQuery(sqlCmd, new String[]{"" + record_book}).moveToFirst();
        close();
        return isExist;
    }
    public boolean isRecordBookRegistr(String record_book){
        boolean isExist;
        String sqlCmd = "select * from students where record_book = ? and email IS NOT NULL";
        opendatabase();
        isExist = database.rawQuery(sqlCmd,new String[]{record_book}).moveToFirst();
        close();
        return isExist;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
