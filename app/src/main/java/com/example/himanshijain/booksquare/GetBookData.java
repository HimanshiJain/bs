package com.example.himanshijain.booksquare;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Himanshi Jain on 1/22/2016.
 */
public class GetBookData {
//     String TAG="VolleyRequest";
//
//    public void requestForBookData(){
//
//        StringRequest strReq = new StringRequest(Request.Method.POST,
//                Config.ALL_BOOK_DATA,new Response.Listener<String>() {
//
//            @Override
//            public void onResponse(String response) {
//                Log.i(TAG, response.toString());
//                Log.i("Response","received");
//
//                try {
//                    JSONObject responseObj = new JSONObject(response);
//                    Log.i("Json","Parsing Json Response");
//                    // Parsing json object response
//                    // response will be a json object
//
//
//                } catch (JSONException e) {
//                    Log.i(TAG, "Error: JSON EXception");
//                }
//
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                if (error instanceof TimeoutError) {
//                    Log.i(TAG, "Error: TimeoutError " + error.getMessage());
//                }else if( error instanceof NoConnectionError){
//                    Log.i(TAG, "Error: NoConnectionError" + error.getMessage());
//                }
//                else if (error instanceof AuthFailureError) {
//                    Log.i(TAG, "Error: AuthFailureError " + error.getMessage());
//                } else if (error instanceof ServerError) {
//                    Log.i(TAG, "Error: ServerError " + error.getMessage());
//                } else if (error instanceof NetworkError) {
//                    Log.i(TAG, "Error: NetworkError " + error.getMessage());
//                } else if (error instanceof ParseError) {
//                    Log.i(TAG, "Error: ParseError " + error.getMessage());
//                }
//
//            }
//        }){
//
//
//
//            /**
//             * Passing user parameters to our server
//             * @return
//             */
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("secret_key",Config.SECRET_KEY_ALL_BOOK);
//                Log.e(TAG, "Posting params: " + params.toString());
//
//                return params;
//            }
//
//
//        }
//                ;
//        //JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,Config.URL_REQUEST_SMS,null,)
//
//        int socketTimeout = 60000;
//        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//        strReq.setRetryPolicy(policy);
//        Log.i("Request", strReq.toString());
//        // Adding request to request queue
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(strReq);
//
//    }
}
