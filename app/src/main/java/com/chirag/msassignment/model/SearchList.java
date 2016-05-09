package com.chirag.msassignment.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chirag on 08/05/16.
 *
 * Search list model class which contains list of search images.
 */
public class SearchList {
    ArrayList<Search> searchList;

    public SearchList(ArrayList<Search> searchList) {
        this.searchList = searchList;
    }

    public ArrayList<Search> getSearchList() {
        return searchList;
    }

    public void setSearchList(ArrayList<Search> searchList) {
        this.searchList = searchList;
    }
}
