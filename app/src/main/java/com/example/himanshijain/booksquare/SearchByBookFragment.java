package com.example.himanshijain.booksquare;


import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchByBookFragment extends android.support.v4.app.Fragment {


    public SearchByBookFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_search_by_book_fragment, container, false);
        final SearchView searchView=(SearchView)view.findViewById(R.id.searchByBook);
        final SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        SearchView.OnQueryTextListener onQueryTextListener= new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

//                task  = new BooksAsyncTaskClass();
//                task.listener = MainActivity.this;
//                task.execute(getUrlString("o"));
                Log.i("Search", "On submit");
                getActivity().onSearchRequested();
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
        return  view;
    }


}
