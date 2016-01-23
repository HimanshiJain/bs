package com.example.himanshijain.booksquare;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.CancellationSignal;
import android.util.Log;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by Himanshi Jain on 1/23/2016.
 */
public class BookProvider extends ContentProvider {


    static final String PROVIDER_NAME="com.example.himanshijain.booksquare.BookProvider";
    static final String URL = "content://" + PROVIDER_NAME + "/books";
    static final Uri BOOKS_URI = Uri.parse(URL);
    static final String id = "id";
    static final String name = "name";
    static final int uriCode = 1;
    static final UriMatcher uriMatcher;
    private static HashMap<String, String> values;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "books", uriCode);
        uriMatcher.addURI(PROVIDER_NAME, "books/*", uriCode);

    }

    SQLiteDatabase db;
    BookDBHelper dbHelper;
    @Override
    public boolean onCreate() {
        Context context = getContext();
         dbHelper = new BookDBHelper(context);
        db = dbHelper.getWritableDatabase();
        if (db != null) {
            return true;
        }
        return false;
    }



    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(dbHelper.TABLE_BOOKS);

        //uri=BOOKS_URI;
        Log.i("Uri", uri.toString());
        String lastSegment=uri.getLastPathSegment().toString();
        String last=uri.getEncodedFragment();
        uri=Uri.parse(URL+"/");
        Log.i("Uri", uri.toString());
        Log.i("LastSegment", lastSegment);
        values=new HashMap<String,String>();
        values.put(dbHelper.COLUMN_NAME_ENTRY_ID,dbHelper.COLUMN_NAME_ENTRY_ID);
        values.put(dbHelper.COLUMN_NAME_TITLE,dbHelper.COLUMN_NAME_TITLE
                +" AS "+SearchManager.SUGGEST_COLUMN_TEXT_1);
        values.put(dbHelper.COLUMN_NAME_AUTHORS,dbHelper.COLUMN_NAME_AUTHORS +" AS "+SearchManager.SUGGEST_COLUMN_TEXT_2);
        switch (uriMatcher.match(uri)) {
            case uriCode:
                //qb.setTables(dbHelper.TABLE_BOOKS);
                Log.i("Projection Map","setting");
                qb.setProjectionMap(values);
                Log.i("Projection Map", "set");
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        if (sortOrder == null || sortOrder == "") {
            sortOrder = dbHelper.COLUMN_NAME_TITLE;
        }
        //dbHelper.COLUMN_NAME_TITLE+" LIKE "+"'_"+lastSegment+"_'"
        Cursor c = qb.query(db,projection,selection,selectionArgs,null,null,sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case uriCode:
                return "vnd.android.cursor.dir/books";

            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        long rowID = db.insert(dbHelper.TABLE_BOOKS, "", contentValues);
            if (rowID > 0) {
                Uri _uri = ContentUris.withAppendedId(BOOKS_URI, rowID);
                getContext().getContentResolver().notifyChange(_uri, null);
                return _uri;
            }
        else return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case uriCode:
                count = db.delete(dbHelper.TABLE_BOOKS, s, strings);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        int count = 0;

        switch (uriMatcher.match(uri)) {
            case uriCode:
                count = db.update(dbHelper.TABLE_BOOKS, contentValues, s, strings);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
