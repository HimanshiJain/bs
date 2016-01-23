package com.example.himanshijain.booksquare;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/**
 * Created by himanshi jain on 04-11-2015.
 */
public class BooksAsyncTaskClass extends AsyncTask<String,Void,BookDetails[]> {

    BooksAsyncTaskInterface listener;
    @Override
    protected BookDetails[] doInBackground(String... strings) {

        StringBuffer output1;
        final HttpURLConnection URLConnect;
        try {
            URL url = new URL(strings[0]);
            URLConnect = (HttpURLConnection)url.openConnection();
            URLConnect.setRequestMethod("GET");
            URLConnect.connect();
            InputStream data = URLConnect.getInputStream();
            Scanner s = new Scanner(data);
            output1 = new StringBuffer();
            while(s.hasNext()){
                output1.append(s.nextLine());
            }
            Log.i("output1", output1.toString());
            s.close();
            URLConnect.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }catch (IOException e){
             return null;
        }
        return parseBooksJSON(output1.toString());
    }

    @Override
    protected void onPostExecute(BookDetails[] bookDetails) {
       if(listener!=null)
       {
           if(bookDetails!=null)
           listener.taskOnComplete(bookDetails);
       }
    }

    private BookDetails[] parseBooksJSON(String JSONString){
    try{

        JSONArray object=new JSONArray(JSONString);
        BookDetails[] output=new BookDetails[object.length()];
        for(int i=0;i<object.length(); i++) {
            JSONObject obj = object.getJSONObject(i);

            output[i] = new BookDetails(obj.getString("title"),"author1", obj.getInt("edition"),obj.getInt("id"));
        }
            return output;


    }catch(JSONException e){
        return  null;
        }
    }

}
