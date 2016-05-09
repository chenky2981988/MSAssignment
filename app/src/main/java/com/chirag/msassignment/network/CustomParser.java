package com.chirag.msassignment.network;

import android.support.v4.widget.SearchViewCompat;

import com.chirag.msassignment.model.Search;
import com.chirag.msassignment.model.SearchList;
import com.chirag.msassignment.model.Thumbnail;
import com.chirag.msassignment.util.AppUtils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by chirag on 08/05/16.
 *
 * This custom parser used to parse dynamix json response coming from server.
 * It will take only those objects which key is present in json.
 */
public class CustomParser {

    public SearchList fromJson(JSONObject response){

        try {
            JSONObject queryObj = response.getJSONObject(AppUtils.QUERY_KEY);
            if(queryObj != null) {
                if(queryObj.has(AppUtils.PAGES_KEY)){
                    JSONObject pagesObj = queryObj.getJSONObject(AppUtils.PAGES_KEY);
                    Iterator<String> keys = pagesObj.keys();
                    ArrayList<Search> searchArrayList = new ArrayList<>();
                    while (keys.hasNext()){
                        Search search = new Search();
                        JSONObject searchObj = pagesObj.getJSONObject(keys.next());

                        if(searchObj.has(AppUtils.PAGEID_KEY)){
                            search.setPageid(searchObj.getInt(AppUtils.PAGEID_KEY));
                        }
                        if(searchObj.has(AppUtils.NS_KEY)){
                            search.setNs(searchObj.getInt(AppUtils.NS_KEY));
                        }
                        if(searchObj.has(AppUtils.TITLE_KEY)){
                            search.setTitle(searchObj.getString(AppUtils.TITLE_KEY));
                        }
                        if(searchObj.has(AppUtils.INDEX_KEY)){
                            search.setIndex(searchObj.getInt(AppUtils.INDEX_KEY));
                        }

                        if(searchObj.has(AppUtils.THUMBNAIL_KEY)){
                            search.setThumbnail(new Gson().fromJson(searchObj.get(AppUtils.THUMBNAIL_KEY).toString(), Thumbnail.class));
                            searchArrayList.add(search);
                        }
                    }
                    return new SearchList(searchArrayList);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
