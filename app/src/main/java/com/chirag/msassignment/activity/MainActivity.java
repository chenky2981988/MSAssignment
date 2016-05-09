package com.chirag.msassignment.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.chirag.msassignment.MSApplication;
import com.chirag.msassignment.R;
import com.chirag.msassignment.adapter.SearchAdapter;
import com.chirag.msassignment.network.CustomParser;
import com.chirag.msassignment.util.AppUtils;;
import org.json.JSONObject;

/**
 * Created by chirag on 08/05/16.
 *
 * This is main activity where user can search images on wiki.
 * This will display only those pages/image which are having image urls.
 */
public class MainActivity extends AppCompatActivity {

    private ListView mSearchListView;
    private EditText mSearchEdittext;
    private String TAG = MainActivity.class.getSimpleName();
    private Context mContext;
    private CustomParser parser;
    private SearchAdapter searchAdapter;
    private TextView emptyPHTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSearchListView = (ListView) findViewById(R.id.search_listview);
        mSearchEdittext = (EditText) findViewById(R.id.search_edittext);
        emptyPHTextView = (TextView) findViewById(R.id.emptyPlaceHolder);
        emptyPHTextView.setVisibility(View.VISIBLE);
        mSearchListView.setVisibility(View.GONE);
        mSearchEdittext.addTextChangedListener(new SearchWatcher());
        mContext = getApplicationContext();
        parser = new CustomParser();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!AppUtils.isOnline(mContext)){
            Toast.makeText(mContext,getString(R.string.internet_error_msg),Toast.LENGTH_SHORT).show();
        }
    }

    /*
         * This functions is used to perform search query on WIKI URL with customize image width according to device actual width.
         */
    private void performSearch(String searchQuery) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(AppUtils.SEARCH_URL);
        stringBuilder.append(searchQuery);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, String.format(AppUtils.SEARCH_URL, AppUtils.getDeviceWidth(this), searchQuery), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                if (response != null) {
                    emptyPHTextView.setVisibility(View.GONE);
                    mSearchListView.setVisibility(View.VISIBLE);
                    if (searchAdapter == null) {
                        if (parser.fromJson(response) != null && parser.fromJson(response).getSearchList() != null) {
                            searchAdapter = new SearchAdapter(mContext, parser.fromJson(response).getSearchList());
                            mSearchListView.setAdapter(searchAdapter);
                        }else{
                            emptyPHTextView.setVisibility(View.VISIBLE);
                            mSearchListView.setVisibility(View.GONE);
                        }
                    } else {
                        if (parser.fromJson(response) != null && parser.fromJson(response).getSearchList() != null) {
                            searchAdapter.setList(parser.fromJson(response).getSearchList());
                        }else{
                            emptyPHTextView.setVisibility(View.VISIBLE);
                            mSearchListView.setVisibility(View.GONE);
                        }
                    }
                }else{
                    emptyPHTextView.setVisibility(View.VISIBLE);
                    mSearchListView.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error : " + error.getMessage());
                emptyPHTextView.setVisibility(View.VISIBLE);
                mSearchListView.setVisibility(View.GONE);
            }
        });
        // Adding request to request queue
        MSApplication.getInstance().addToRequestQueue(jsonObjReq, AppUtils.WIKI_IMG_SEARCH);
    }

    /**
     * Responsible for handling changes in search edit text.
     */
    private class SearchWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence c, int i, int i2, int i3) {

        }

        @Override
        public void onTextChanged(CharSequence c, int i, int i2, int i3) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String mSearchQuery = mSearchEdittext.getText().toString();
            if (!TextUtils.isEmpty(mSearchQuery)) {
                MSApplication.getInstance().cancelPendingRequests(AppUtils.WIKI_IMG_SEARCH);
                if(AppUtils.isOnline(mContext)) {
                    performSearch(mSearchQuery);
                }else{
                    Toast.makeText(mContext,getString(R.string.internet_error_msg),Toast.LENGTH_SHORT).show();
                }
            }else{
                MSApplication.getInstance().cancelPendingRequests(AppUtils.WIKI_IMG_SEARCH);
                emptyPHTextView.setVisibility(View.VISIBLE);
                mSearchListView.setVisibility(View.GONE);
            }
        }
    }

}
