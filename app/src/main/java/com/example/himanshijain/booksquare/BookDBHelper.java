package com.example.himanshijain.booksquare;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Himanshi Jain on 1/22/2016.
 */
public class BookDBHelper extends SQLiteOpenHelper{
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
Context context;
    public static final String DATABASE_NAME = "booksquare";
    public static final int DATABASE_VERSION = 1;
   public static final String TABLE_BOOKS = "books";
    public static final String COLUMN_NAME_ENTRY_ID = "_id";
    public static final String COLUMN_NAME_TITLE = "title";
    public static final String COLUMN_NAME_EDITION = "edition";
    public static final String COLUMN_NAME_AUTHORS = "authors";

    SharedPreferences sharedPreferences;
    Activity activity;
    public BookDBHelper(Context context,Activity activity) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
        this.activity=activity;
    }

    public BookDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sharedPreferences=context.getSharedPreferences("Prefs",context.MODE_PRIVATE);
        if(!sharedPreferences.getBoolean("booksAdded",false)) {
            Log.i("books", "db helper on create");
            String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_BOOKS + "("
                    + COLUMN_NAME_ENTRY_ID + " INTEGER PRIMARY KEY," + COLUMN_NAME_TITLE + " TEXT,"
                    + COLUMN_NAME_EDITION + " INTEGER," + COLUMN_NAME_AUTHORS + " TEXT"
                    + ")";
            sqLiteDatabase.execSQL(CREATE_CONTACTS_TABLE);
        }
    }

//    public void addBooks(ArrayList<BookDetails> bookDetails) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        for(int i=0;i<bookDetails.size();++i) {
//            ContentValues values = new ContentValues();
//            values.put(COLUMN_NAME_ENTRY_ID, bookDetails.get(i).id);
//            values.put(COLUMN_NAME_TITLE, bookDetails.get(i).title);
//            values.put(COLUMN_NAME_EDITION, bookDetails.get(i).edition);
//            values.put(COLUMN_NAME_AUTHORS, bookDetails.get(i).authors);
//            // Inserting Row
//            db.insert(TABLE_BOOKS, null, values);
//        }
//        db.close(); // Closing database connection
//    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS);
        onCreate(sqLiteDatabase);
    }
//    BookDetails getBook(int id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.query(TABLE_BOOKS, new String[] { COLUMN_NAME_ENTRY_ID,
//                        COLUMN_NAME_TITLE, COLUMN_NAME_EDITION,COLUMN_NAME_AUTHORS }, COLUMN_NAME_ENTRY_ID + "=?",
//                new String[] { String.valueOf(id) }, null, null, null, null);
//        if (cursor != null)
//            cursor.moveToFirst();
//
//        BookDetails contact = new BookDetails(cursor.getString(1),
//                cursor.getString(3), Integer.parseInt(cursor.getString(2)),Integer.parseInt(cursor.getString(0)));
//        Toast.makeText(context,""+cursor.getString(1)+Integer.parseInt(cursor.getString(2)),Toast.LENGTH_LONG).show();
//        // return contact
//        return contact;
//    }
}
