package com.example.himanshijain.booksquare;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SearchViewCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.NumberPicker;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    List<ImageView> dots;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    int currentPage;
    Timer swipeTimer;
    RequestQueue requestQueue;
    private Menu mToolbarMenu;
    ArrayList<BookDetails> bookarrayList;
    SharedPreferences sharedPreferences;
    BookDBHelper bookDBHelper;

//    BooksAsyncTaskClass task;
Button _btn1,_btn2,_btn3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences=getSharedPreferences("Prefs", MODE_PRIVATE);


       //request for all the data of the book
        if(!sharedPreferences.getBoolean("booksAdded",false)) {
            requestForBookData();
            Log.i("books","requested");
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putBoolean("booksAdded", true);
            editor.commit();

        }
        bookDBHelper=new BookDBHelper(this,MainActivity.this);
        //bookDBHelper.getBook(3001);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        final BannerAdapter bannerAdapter = new BannerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(bannerAdapter);
        _btn1 = (Button) findViewById(R.id.btn1);
        _btn2 = (Button) findViewById(R.id.btn2);
        _btn3 = (Button) findViewById(R.id.btn3);
        setButton(_btn1, 20, 20, -1);
        setButton(_btn2, 20, 20, -7829368);
        setButton(_btn3, 20, 20, -7829368);
        setTab();
        final SearchView searchView=(SearchView)findViewById(R.id.searchView);
        final SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        SearchView.OnQueryTextListener onQueryTextListener= new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

//                task  = new BooksAsyncTaskClass();
//                task.listener = MainActivity.this;
//                task.execute(getUrlString("o"));
                Log.i("Search","On submit");
                onSearchRequested();
                return true;

            }


            @Override
            public boolean onQueryTextChange(String s) {
                 //searchView.setSuggestionsAdapter();
                Log.i("Search","On text changed");
                //onSearchRequested();
                return false;
            }
        };
        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int i) {
                return false;
            }

            @Override
            public boolean onSuggestionClick(int i) {
                return false;
            }
        });

        TextView tv=(TextView)findViewById(R.id.textView);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), SearchActivityTabs.class);
                startActivity(intent);
            }
        });
        Button button=(Button)findViewById(R.id.byCollege);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);
                final LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.dialog, null);
                b.setView(dialogView);
                b.setTitle("Enter Details");
                final Spinner a = (Spinner) dialogView.findViewById(R.id.autocomplete_branch);
                String[] branches = getResources().getStringArray(R.array.branch_array);
                ArrayAdapter<String> branch_adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.item, branches);
                a.setAdapter(branch_adapter);
                final Spinner c = (Spinner) dialogView.findViewById(R.id.autocomplete_college);
                String[] colleges = getResources().getStringArray(R.array.college_array);
                ArrayAdapter<String> college_adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.item, colleges);
                c.setAdapter(college_adapter);
                final Spinner d = (Spinner) dialogView.findViewById(R.id.autocomplete_sem);
                String[] sems = getResources().getStringArray(R.array.sem_array);
                ArrayAdapter<String> sem_adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.item, sems);
                d.setAdapter(sem_adapter);
                b.setNeutralButton("GO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                               onSearchRequested();
                        //ComponentName componentName=(ComponentName)new ComponentName(d.getSelectedItem().toString(),"MainActivity");

                        Log.i("Search","On submit");
                        Intent intent = new Intent();
                        intent.setClass(getApplicationContext(), SearchResultsActivity.class);
                        intent.setAction("android.intent.action.SEARCH");
                        intent.putExtra("college query", c.getSelectedItem().toString() + " " +
                                a.getSelectedItem().toString() + " " + d.getSelectedItem().toString() + " ");
                        startActivity(intent);

                    }
                });

                b.create().show();

            }
        });


        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        currentPage = viewPager.getCurrentItem();
        swipeTimer = new Timer();


        swipeTimer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (currentPage == bannerAdapter.getCount()) {
                            currentPage = 0;
                        }
                        viewPager.setCurrentItem(currentPage++, true);
                    }
                });
            }
        }, 300, 5000);




    }



    private void setTab(){
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){

            @Override
            public void onPageScrollStateChanged(int position) {}
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {}
            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                btnAction(position);
            }

        });

    }
    private void btnAction(int action){
        switch(action){
            case 0: setButton(_btn1,20,20,-1); setButton(_btn2,20,20,-7829368 );setButton(_btn3,20,20,-7829368 );break;
            case 1: setButton(_btn2,20,20,-1); setButton(_btn1,20,20,-7829368 );setButton(_btn3,20,20,-7829368 ); break;
            case 2: setButton(_btn3,20,20,-1); setButton(_btn1,20,20,-7829368 );setButton(_btn2,20,20,-7829368 ); break;
        }
    }
    private void setButton(Button btn,int h, int w,int c){
        btn.setWidth(w);
        btn.setHeight(h);
        if(c==-1)
            btn.setBackgroundResource(R.drawable.button_selected);
        else
            btn.setBackgroundResource(R.drawable.button_unselected);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
//        switch (item.getItemId()) {
//            case R.id.action_cart:
//                Toast.makeText(this, "Show Cart", Toast.LENGTH_SHORT).show();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mToolbarMenu = menu;
        //createCartBadge(10);
        return super.onPrepareOptionsMenu(menu);
    }
    private void createCartBadge(int paramInt) {
//        if (Build.VERSION.SDK_INT <= 15) {
//            return;
//        }
        MenuItem cartItem = this.mToolbarMenu.findItem(R.id.action_cart);
        LayerDrawable localLayerDrawable = (LayerDrawable) cartItem.getIcon();
        Drawable cartBadgeDrawable = localLayerDrawable
                .findDrawableByLayerId(R.id.ic_badge);
        BadgeDrawable badgeDrawable;
        if ((cartBadgeDrawable != null)
                && ((cartBadgeDrawable instanceof BadgeDrawable))
                && (paramInt < 10)) {
            badgeDrawable = (BadgeDrawable) cartBadgeDrawable;
        } else {
            badgeDrawable = new BadgeDrawable(this);
        }
        badgeDrawable.setCount(paramInt);
        localLayerDrawable.mutate();
        localLayerDrawable.setDrawableByLayerId(R.id.ic_badge, badgeDrawable);
        cartItem.setIcon(localLayerDrawable);
    }



    String TAG="VolleyRequest";

    public void requestForBookData(){

        StringRequest strReq = new StringRequest(Request.Method.GET,
                Config.ALL_BOOK_DATA,new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i(TAG, response.toString());
                Log.i("Response","received");

                try {
                    JSONArray responseObj = new JSONArray(response);
                    Log.i("Json","Parsing Json Response");
                    // Parsing json object response
                    // response will be a json object
                    parseJSON(responseObj);

                } catch (JSONException e) {
                    Log.i(TAG, "Error: JSON EXception");
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError) {
                    Log.i(TAG, "Error: TimeoutError " + error.getMessage());
                }else if( error instanceof NoConnectionError){
                    Log.i(TAG, "Error: NoConnectionError" + error.getMessage());
                }
                else if (error instanceof AuthFailureError) {
                    Log.i(TAG, "Error: AuthFailureError " + error.getMessage());
                } else if (error instanceof ServerError) {
                    Log.i(TAG, "Error: ServerError " + error.getMessage());
                } else if (error instanceof NetworkError) {
                    Log.i(TAG, "Error: NetworkError " + error.getMessage());
                } else if (error instanceof ParseError) {
                    Log.i(TAG, "Error: ParseError " + error.getMessage());
                }

            }
        })
//        {
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
                ;
        //JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,Config.URL_REQUEST_SMS,null,)

        int socketTimeout = 60000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        strReq.setRetryPolicy(policy);
        Log.i("Request", strReq.toString());
        // Adding request to request queue
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(strReq);

    }


    private void parseJSON(JSONArray jsonArray){
        try{

            bookarrayList=new ArrayList<>(jsonArray.length());
            for(int i=0;i<jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);

                bookarrayList.add(new BookDetails(obj.getString("title"),obj.getString("authors"), obj.getInt("edition"),obj.getInt("id")));
            }

          Toast.makeText(getApplicationContext(),""+jsonArray.toString(),Toast.LENGTH_LONG).show();
            //bookDBHelper=new BookDBHelper(this);
            if(bookarrayList!=null){
                Log.i("books","added");
                addBooks(bookarrayList);
            }

        }catch(JSONException e){

        }
    }


    public void addBooks(ArrayList<BookDetails> bookDetails) {

        for(int i=0;i<bookDetails.size();++i) {
            ContentValues values = new ContentValues();
            values.put(bookDBHelper.COLUMN_NAME_ENTRY_ID, bookDetails.get(i).id);
            values.put(bookDBHelper.COLUMN_NAME_TITLE, bookDetails.get(i).title);
            values.put(bookDBHelper.COLUMN_NAME_EDITION, bookDetails.get(i).edition);
            values.put(bookDBHelper.COLUMN_NAME_AUTHORS, bookDetails.get(i).authors);
            Uri uri = getContentResolver().insert(BookProvider.BOOKS_URI, values);

        }
        Toast.makeText(getBaseContext(), "New records inserted", Toast.LENGTH_LONG)
                .show();
        Log.i("Records", "inserted");
    }

}
