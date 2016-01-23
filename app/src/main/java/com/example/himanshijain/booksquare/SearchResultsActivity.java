package com.example.himanshijain.booksquare;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchResultsActivity extends AppCompatActivity implements BooksAsyncTaskInterface {

    BooksAsyncTaskClass task;
    SearchView searchView;
    //RecyclerView recyclerView;
    SuggestionAdapter adapter;
    //RecyclerView.LayoutManager layoutManager;
    ArrayList<String> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        //recyclerView=(RecyclerView)findViewById(R.id.suggestion_list);
        data=new ArrayList<>();
        data.add("Operating Systems");
        Intent intent=getIntent();
        String collegeQuery=intent.getStringExtra("college query");
        String text=intent.getStringExtra(SearchManager.QUERY);
        searchView=(SearchView)findViewById(R.id.searchView);
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

        final SearchView searchView=(SearchView)findViewById(R.id.searchView);
        final SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        SearchView.OnQueryTextListener onQueryTextListener= new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

//                task  = new BooksAsyncTaskClass();
//                task.listener = MainActivity.this;
//                task.execute(getUrlString("o"));
                return false;

            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        };


//        TextView tv=(TextView)findViewById(R.id.textReceived);
//        if(text!=null)
//        tv.setText(text);
//        else if(collegeQuery!=null)
//            tv.setText(collegeQuery);
//        handleIntent(intent);


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
           if(query!=null) {
//               showResults(query);
//               task  = new BooksAsyncTaskClass();
//               task.listener =this;
//               task.execute(getUrlString("o"));


           }
            else
               showResults(intent.getStringExtra("college query"));
        }
    }

    private void showResults(String query) {
        searchView.setQuery(query,false);
       Toast.makeText(this,query,Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_results, menu);
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

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void taskOnComplete(BookDetails[] b) {
        if(b!=null)
            Log.i("task done", b[0].title);
    }


    public String getUrlString(String startingAlpha){
        return "http://booksquare.in/books/search/autocomplete/?term="+startingAlpha;
    }
}
