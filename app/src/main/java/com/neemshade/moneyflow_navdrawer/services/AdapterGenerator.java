package com.neemshade.moneyflow_navdrawer.services;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.SimpleAdapter;

import com.neemshade.moneyflow_navdrawer.data.Company;
import com.neemshade.moneyflow_navdrawer.data.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Balaji on 20-12-2016.
 */

public abstract class AdapterGenerator {
    protected boolean isPurchase = true;
    protected Company company;

    protected Context context;
    protected int listViewResource;
    protected String[] columnHeaders;
    protected int[] viewIds;

    public SimpleAdapter generateAdapter()
    {
        List<Map<String, String>> feedList= composeAdapterData();
        SimpleAdapter  simpleAdapter = new SimpleAdapter(context, feedList, listViewResource, columnHeaders, viewIds);
        return simpleAdapter;
    }

    /**
     * get the data list from child classes and create data that adapter class can use
     * @return
     */
    @NonNull
    public List<Map<String, String>> composeAdapterData() {
        List<Map<String, String>> feedList = new ArrayList<Map<String, String>>();

        List<List<String>> dataLOL = composeDataInListOfList();

        for (List<String> dataList : dataLOL) {
            Map<String, String> feedMap = new HashMap<String, String>();

            for (int i = 0; i < columnHeaders.length; i++) {
                feedMap.put(columnHeaders[i], dataList.get(i));
            }

            feedList.add(feedMap);
        }

        return feedList;
    }

    /**
     * generate list of rows from the company object
     * @return
     */
    protected abstract List<List<String>> composeDataInListOfList();


    /**
     * check if the transaction can be listed
     * @param transaction
     * @return
     */
    protected boolean isConsiderable(Transaction transaction) {
        if(transaction == null) return false;
        if(transaction.getLeftOver() == 0) return false;
        if(isPurchase && !transaction.isPurchase()) return false;
        if(!isPurchase() && transaction.isPurchase()) return false;

        return true;
    }


    public boolean isPurchase() {
        return isPurchase;
    }

    public void setPurchase(boolean purchase) {
        isPurchase = purchase;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getListViewResource() {
        return listViewResource;
    }

    public void setListViewResource(int listViewResource) {
        this.listViewResource = listViewResource;
    }

    public String[] getColumnHeaders() {
        return columnHeaders;
    }

    public void setColumnHeaders(String[] columnHeaders) {
        this.columnHeaders = columnHeaders;
    }

    public int[] getViewIds() {
        return viewIds;
    }

    public void setViewIds(int[] viewIds) {
        this.viewIds = viewIds;
    }


}
