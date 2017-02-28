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

    public  static SqlHelper getInstance(Context context) {
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

    public  void opendatabase() throws SQLException {
        //Open the database
        String mypath = DB_LOCATION + DB_NAME;
        database = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);

    }

    public  boolean isDatabaseExists() {
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

    public  void createdatabase() throws IOException {
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

    public boolean addNewUser(String fio, String email, String password, String recordBookNumber) {
        boolean isOk = false;
        // String sqlCmd = "update students set email = ?, password =?, name =? where record_book = ?";
        opendatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("name", fio);
        contentValues.put("password", password);
        int updated = database.update("students", contentValues, "record_book =?", new String[]{recordBookNumber});
        if (updated == 1) {
            isOk = !isOk;
        }
        close();
        return isOk;
    }

    /**
     * Getters
     */
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

    public int getGroupIdByUserId(int userId) {
        int groupId = -7733;
        String sqlCmd = "select group_id from students where id = ?";
        opendatabase();
        Cursor cursor = database.rawQuery(sqlCmd, new String[]{Integer.toString(userId)});
        if (cursor.moveToNext())
            groupId = cursor.getInt(cursor.getColumnIndex("group_id"));
        cursor.close();
        close();
        return groupId;
    }

    public String getUserName(int userId) {
        String sqlCmd = "select name from students where id =?";
        String name = "";
        opendatabase();
        Cursor cursor = database.rawQuery(sqlCmd, new String[]{Integer.toString(userId)});
        if (cursor.moveToFirst())
            name = cursor.getString(0);
        cursor.close();
        close();
        return name;
    }

    public String getUserEmail(int userId) {
        String sqlCmd = "select email from students where id =?";
        String name = "";
        opendatabase();
        Cursor cursor = database.rawQuery(sqlCmd, new String[]{Integer.toString(userId)});
        if (cursor.moveToFirst())
            name = cursor.getString(0);
        cursor.close();
        close();
        return name;
    }

    public int getTestResultForArticle(int articleId, int userId, int articlesCount) {
        int procent = 0;
        String sqlCmd = "select sum(manage_students_tests_result.result)*100/(?*100) " +
                "from manage_students_tests_result " +
                "left join tests on manage_students_tests_result.test_id = tests.id " +
                "left join manage_themes_articles on manage_themes_articles.theme_id = tests.theme_id " +
                "where manage_themes_articles.article_id = ? " +
                "and manage_students_tests_result.student_id = ?";
        opendatabase();
        Cursor cursor = database.rawQuery(sqlCmd, new String[]{Integer.toString(articlesCount), Integer.toString(articleId), Integer.toString(userId)});
        if (cursor.moveToFirst())
            procent = cursor.getInt(0);
        cursor.close();
        close();
        return procent;
    }

    public int getArticlesIdForName(String name) {
        int articleId = -7733;
        String sqlCmd = "select id from articles where name = ?";
        opendatabase();
        Cursor cursor = database.rawQuery(sqlCmd, new String[]{name});
        if (cursor.moveToFirst())
            articleId = cursor.getInt(cursor.getColumnIndex("id"));
        cursor.close();
        close();
        Log.d(Constants.TAG, "getArticlesIdForName : " + articleId);
        return articleId;
    }

    public void getRecordBooks() {
        String sqlCmd = "select record_book from students ";
        opendatabase();
        Cursor cursor = database.rawQuery(sqlCmd, new String[]{});
        if (cursor.moveToFirst())
            do {
                Log.d(Constants.TAG, cursor.getString(0));
            } while (cursor.moveToNext());
        cursor.close();
        close();
    }

    public ArrayList<String> getArticlesForGroupId(int groupId) {
        Log.d(Constants.TAG, "Try to get articles for group id");
        ArrayList<String> articles = new ArrayList<>();
        String sqlCmd = "select name from manage_groups_articles left join articles on article_id = articles.id where group_id = ?";
        opendatabase();
        Cursor cursor = database.rawQuery(sqlCmd, new String[]{Integer.toString(groupId)});
        if (cursor.moveToFirst()) {
            do {
                articles.add(cursor.getString(cursor.getColumnIndex("name")));
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return articles;
    }

    public ArrayList<ArticleThemeValue> getThemes(int articleId) {
        ArrayList<ArticleThemeValue> themes = new ArrayList<>();
        ArticleThemeValue articleThemeValue;
        String sqlCmd = "select id, name from manage_themes_articles left join themes on theme_id = themes.id where article_id = ?";
        opendatabase();
        Cursor cursor = database.rawQuery(sqlCmd, new String[]{Integer.toString(articleId)});
        if (cursor.moveToFirst()) {
            do {
                articleThemeValue = new ArticleThemeValue(cursor.getString(cursor.getColumnIndex("name")), cursor.getInt(cursor.getColumnIndex("id")));
                themes.add(articleThemeValue);
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return themes;
    }

    public ArrayList<ArticleThemeValue> getBooksForArticle(int articleId) {
        ArrayList<ArticleThemeValue> books = new ArrayList<>();
        ArticleThemeValue articleThemeValue;
        String sqlCmd = "select books.id, books.name from manage_articles_books " +
                "left join articles on article_id = articles.id " +
                "left join books on book_id = books.id  where article_id = ? ";
        opendatabase();
        Cursor cursor = database.rawQuery(sqlCmd, new String[]{Integer.toString(articleId)});
        if (cursor.moveToFirst()) {
            do {
                articleThemeValue = new ArticleThemeValue(cursor.getString(cursor.getColumnIndex("name")), cursor.getInt(cursor.getColumnIndex("id")));
                books.add(articleThemeValue);
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return books;
    }

    public ArrayList<ArticleThemeValue> getQuestionsForTrain(int themeId) {
        ArrayList<ArticleThemeValue> questions = new ArrayList<>();
        ArticleThemeValue articleThemeValue;
        String sqlCmd = "select id, name from questions where theme_id = ?";
        opendatabase();
        Cursor cursor = database.rawQuery(sqlCmd, new String[]{Integer.toString(themeId)});
        if (cursor.moveToFirst()) {
            do {
                articleThemeValue = new ArticleThemeValue(cursor.getString(cursor.getColumnIndex("name")), cursor.getInt(cursor.getColumnIndex("id")));
                questions.add(articleThemeValue);
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return questions;
    }

    public ArrayList<ArticleThemeValue> getArticlesForGroup(int groupId) {
        Log.d(Constants.TAG, "Try to get articles for group id");
        ArrayList<ArticleThemeValue> articles = new ArrayList<>();
        ArticleThemeValue articleThemeValue;
        String sqlCmd = "select id, name from manage_groups_articles left join articles on article_id = articles.id where group_id = ?";
        opendatabase();
        Cursor cursor = database.rawQuery(sqlCmd, new String[]{Integer.toString(groupId)});
        if (cursor.moveToFirst()) {
            do {
                articleThemeValue = new ArticleThemeValue(cursor.getString(cursor.getColumnIndex("name")), cursor.getInt(cursor.getColumnIndex("id")));
                articles.add(articleThemeValue);
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return articles;
    }

    public ArrayList<TestClass.Question> getQuestionsForTest(TestClass testClass) {
        ArrayList<TestClass.Question> questions = new ArrayList<>();
        TestClass.Question question;
        String sqlCmd = "select * from questions where test_id =?";
        opendatabase();
        Cursor cursor = database.rawQuery(sqlCmd, new String[]{Integer.toString(testClass.getTestId())});
        if (cursor.moveToFirst())
            do {
                question = testClass.newQuestion();
                question.setType(cursor.getInt(cursor.getColumnIndex("question_type_id")));
                question.setId(cursor.getInt(cursor.getColumnIndex("id")));
                question.setText(cursor.getString(cursor.getColumnIndex("name")));
                questions.add(question);
            } while (cursor.moveToNext());
        cursor.close();
        close();
        return questions;
    }

    public ArrayList<ArrayList<TestClass.Answer>> getAnswersForQuestions(ArrayList<TestClass.Question> questions, TestClass testClass) {
        ArrayList<TestClass.Answer> answersForQuestion;
        ArrayList<ArrayList<TestClass.Answer>> answers = new ArrayList<>();
        TestClass.Answer answer;
        String sqlCmd = "select answers.id, answers.name from answers where answers.theme_id = (select tests.theme_id from tests where tests.id = ?) and answers.question_id = ?";
        opendatabase();

        for (TestClass.Question question : questions) {
            Cursor cursor = database.rawQuery(sqlCmd, new String[]{Integer.toString(testClass.getTestId()),Integer.toString(question.getId())});
            if (cursor.moveToFirst()) {
                answersForQuestion = new ArrayList<>();
                do {
                    answer = question.newAnswer();
                    answer.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    answer.setText(cursor.getString(cursor.getColumnIndex("name")));
                    answersForQuestion.add(answer);
                } while (cursor.moveToNext());
            }
            else break;
            cursor.close();
            answers.add(answersForQuestion);
        }
        close();
        return answers;
    }

    public ArrayList<SingleUser> getClassmatesByGroupId(int groupId, int userId) {
        ArrayList<SingleUser> users = new ArrayList<>();
        String sqlCmd = "select students.id, students.name as 'student_name', groups.name as 'group_name' " +
                "from students " +
                "left join groups " +
                "on group_id = groups.id " +
                "where group_id = ? " +
                "and email notnull " +
                "and students.id != ?";
        SingleUser singleUser;
        opendatabase();
        Cursor cursor = database.rawQuery(sqlCmd, new String[]{Integer.toString(groupId), Integer.toString(userId)});
        if (cursor.moveToFirst()) {
            do {
                singleUser = new SingleUser();
                singleUser.setId(cursor.getInt(cursor.getColumnIndex("id")));
                singleUser.setName(cursor.getString(cursor.getColumnIndex("student_name")));
                singleUser.setGroupName(cursor.getString(cursor.getColumnIndex("group_name")));
                singleUser.setGroupId(groupId);
                users.add(singleUser);
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return users;
    }

   /* public ArrayList<SingleRecord> getUserRecordsByIdAndArticle(String id, int article) {
        ArrayList<SingleRecord> singleRecords = new ArrayList<>();
        SingleRecord singleRecord;
       *//* String sqlCmd = "select tests.name as 'name', manage_students_tests_result.result as 'result' " +
                "from manage_students_tests_result " +
                "left join tests on test_id = tests.id " +
                "where student_id =? and ";*//*

*//*        String sqlCmd0 = "select ifnull(manage_students_tests_result.student_id, -1) from manage_students_tests_result where article"
        String sqlCmd = "select " +
                "ifnull(manage_students_tests_result.student_id, -1) as 'id_result',tests.name, " +
                "ifnull(manage_students_tests_result.result, 0.0) as 'result' " +
                "from tests " +
                "left outer join manage_students_tests_result on tests.id = manage_students_tests_result.test_id " +
                "where tests.theme_id in (select manage_themes_articles.theme_id from manage_themes_articles where manage_themes_articles.article_id = ?) " +
                "and (id_result = ? or  id_result = -1)";    *//*
        String sqlCmd = "SELECT " +
                "IFNULL(manage_students_tests_result.student_id,-1) AS 'id_result',tests.name, " +
                "IFNULL(manage_students_tests_result.result, 0.0) AS 'result' " +
                "FROM tests " +
                "LEFT OUTER JOIN manage_students_tests_result ON tests.id = manage_students_tests_result.test_id " +
                "WHERE tests.theme_id " +
                "IN (SELECT manage_themes_articles.theme_id FROM manage_themes_articles WHERE manage_themes_articles.article_id = "+article+") " +
                "AND (id_result = "+id+" OR  id_result = -1) ";
        opendatabase();
        Cursor cursor = database.rawQuery(sqlCmd, null);
        if (cursor.moveToFirst())
            do {
                singleRecord = new SingleRecord();
                singleRecord.setName(cursor.getString(cursor.getColumnIndex("name")));
                singleRecord.setRecord(cursor.getFloat(cursor.getColumnIndex("result")));
                singleRecords.add(singleRecord);
            } while (cursor.moveToNext());
        cursor.close();
        close();
        return singleRecords;
    }*/
    public ArrayList<SingleRecord> getUserRecordsByIdAndArticle(String id, int article){
        ArrayList<SingleRecord> singleRecords = new ArrayList<>();
        SingleRecord singleRecord;
        String sqlCmd = "select * from tests " +
                "left join manage_themes_articles on tests.theme_id = manage_themes_articles.theme_id " +
                "where manage_themes_articles.article_id = ?";
        String sqlCmd2 = "select * from manage_students_tests_result where test_id = ? and student_id = ?";
        opendatabase();
            Cursor cursor = database.rawQuery(sqlCmd,new String[]{""+article});
            if (cursor.moveToFirst())
                do {
                    singleRecord = new SingleRecord();
                    singleRecord.setName(cursor.getString(cursor.getColumnIndex("name")));
                    singleRecord.setTestId(cursor.getInt(cursor.getColumnIndex("id")));
                    singleRecord.setRecord(0.0f);
                    singleRecords.add(singleRecord);
                }while (cursor.moveToNext());
            cursor.close();
        for (SingleRecord singleRecord1: singleRecords){
            cursor = database.rawQuery(sqlCmd2,new String[]{""+singleRecord1.getTestId(),id});
            if (cursor.moveToFirst())
                do {
                    singleRecord1.setRecord(cursor.getInt(cursor.getColumnIndex("result")));
                }while (cursor.moveToNext());
            cursor.close();
        }
        close();
        return singleRecords;
    }
    public ArrayList<SingleRecord> getUserRecordsByIdAndTheme(String id, int theme){
        ArrayList<SingleRecord> singleRecords = new ArrayList<>();
        SingleRecord singleRecord;
        String sqlCmd = "select * from tests " +
                "where theme_id = ?";
        String sqlCmd2 = "select * from manage_students_tests_result where test_id = ? and student_id = ?";
        opendatabase();
        Cursor cursor = database.rawQuery(sqlCmd,new String[]{""+theme});
        if (cursor.moveToFirst())
            do {
                singleRecord = new SingleRecord();
                singleRecord.setName(cursor.getString(cursor.getColumnIndex("name")));
                singleRecord.setTestId(cursor.getInt(cursor.getColumnIndex("id")));
                singleRecord.setRecord(0.0f);
                singleRecords.add(singleRecord);
            }while (cursor.moveToNext());
        cursor.close();
        for (SingleRecord singleRecord1: singleRecords){
            cursor = database.rawQuery(sqlCmd2,new String[]{""+singleRecord1.getTestId(),id});
            if (cursor.moveToFirst())
                do {
                    singleRecord1.setRecord(cursor.getInt(cursor.getColumnIndex("result")));
                }while (cursor.moveToNext());
            cursor.close();
        }
        close();
        return singleRecords;
    }
    /**
     * Данный метод получает вообще все тесты по теме.
     * @param theme
     * @return
     */
    public ArrayList<SingleRecord> getUserTestsWithRecordsByThemeAndUserId(int theme){
        ArrayList<SingleRecord> singleRecords = new ArrayList<>();
        SingleRecord singleRecord;
        String sqlCmd = "select tests.name, ifnull(manage_students_tests_result.result, 0.0) " +
                "from tests " +
                "left outer join manage_students_tests_result on tests.id = manage_students_tests_result.test_id " +
                "where tests.theme_id = ?";
        opendatabase();
        Cursor cursor = database.rawQuery(sqlCmd,new String[]{Integer.toString(theme)});
        if (cursor.moveToFirst())
            do {
                singleRecord = new SingleRecord();
               // singleRecord.setRecord();
            }while (cursor.moveToNext());
        close();
        return singleRecords;
    }

    /**
     * Checkers
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

    public boolean isUserExist(String email, String password) {
        boolean isExist;
        String sqlCmd = "select * from students where email = ? and password = ?";
        opendatabase();
        isExist = database.rawQuery(sqlCmd, new String[]{email, password}).moveToFirst();
        close();
        return isExist;
    }

    public boolean isEmailExist(String email) {
        boolean isExist;
        String sqlCmd = "select id from students where email = ?";
        opendatabase();
        isExist = database.rawQuery(sqlCmd, new String[]{"" + email}).moveToFirst();
        close();
        return isExist;
    }

    public boolean isRecordBookRegistr(String record_book) {
        boolean isExist;
        String sqlCmd = "select * from students where record_book = ? and email IS NOT NULL";
        opendatabase();
        isExist = database.rawQuery(sqlCmd, new String[]{record_book}).moveToFirst();
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
